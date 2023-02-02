package org.crosspointacademy.frc.lib

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import com.ctre.phoenix.sensors.CANCoder
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.SwerveModuleState
import org.crosspointacademy.frc.config.Swerve
import org.crosspointacademy.frc.lib.units.CustomUnitTypes
import org.crosspointacademy.frc.lib.units.toRadians
import org.crosspointacademy.frc.lib.units.toRadiansPerSecond

class SwerveModule(
    driveMotorId: Int,
    driveMotorInverted: Boolean,
    steerMotorId: Int,
    steerMotorInverted: Boolean,
    steerEncoderId: Int,
    private val steerEncoderOffset: Double,
    pidBuilder: PIDBuilder,
) {

    private val driveMotor = WPI_TalonFX(driveMotorId)
    private val steerMotor = WPI_TalonFX(steerMotorId)


    private val steerEncoder = CANCoder(steerEncoderId)
    private val steerPID = pidBuilder.build()

    init {
        driveMotor.inverted = driveMotorInverted
        steerMotor.inverted = steerMotorInverted

        driveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)

        steerPID.enableContinuousInput(-Math.PI, Math.PI)
    }


    val position get() = driveMotor.selectedSensorPosition.toRadians(CustomUnitTypes.TalonFX)
    val velocity get() = driveMotor.selectedSensorVelocity.toRadiansPerSecond(CustomUnitTypes.TalonFX)

    val steer get() = steerEncoder.absolutePosition.toRadians() + steerEncoderOffset

    var state: SwerveModuleState
        get() = SwerveModuleState(velocity, Rotation2d.fromRadians(steer))
        set(value) {
            val (speed, angle) = value

            if (Swerve.DEADBAND && speed < Swerve.DEADBAND_THRESHOLD) {
                stop()
                return
            }

            val optimized = SwerveModuleState.optimize(value, angle)

            // TODO Add feedforward and PIDControl for the drive motor
            driveMotor.set(optimized.speedMetersPerSecond / Swerve.MAX_VELOCITY_PER_SECOND)
            steerMotor.set(steerPID.calculate(steer, optimized.angle.radians))
        }

    fun reset() {
        driveMotor.selectedSensorPosition = 0.0
    }

    fun stop() {
        driveMotor.set(0.0)
        steerMotor.set(0.0)
    }

}
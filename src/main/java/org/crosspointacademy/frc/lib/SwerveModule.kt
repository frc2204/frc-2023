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

/**
 * A [Swerve X Module](https://wcproducts.com/products/swerve-x) from West Coast Products.
 *
 * This implementation specifically consists of 2 Falcon 500 motors, one for the drive and one for the steering.
 *
 */
class SwerveModule(
    driveMotorId: Int,
    driveMotorInverted: Boolean,
    steerMotorId: Int,
    steerMotorInverted: Boolean,
    steerEncoderId: Int,
    private val steerEncoderOffset: Double,
    pidBuilder: PIDBuilder,
) {

    /**
     * The drive motor which is a Falcon 500 motor, which controls the speed of the module.
     */
    private val driveMotor = WPI_TalonFX(driveMotorId)

    /**
     * The steer motor which is a Falcon 500 motor, which controls the angle of the module.
     */
    private val steerMotor = WPI_TalonFX(steerMotorId)

    /**
     * A CANCoder, which is used to get the angle of the module. It is connected to the steering pin with a 1:1 ratio
     */
    private val steerEncoder = CANCoder(steerEncoderId)

    /**
     * The PID controller for the steering motor
     */
    private val steerPID = pidBuilder.build()

    /**
     * If the motor is turned more than 180 degree, turn the opposite side(ex:-50 degree is 310 degree) instead of keep going.
     * Kip the turing efficient
     */
    init {
        driveMotor.inverted = driveMotorInverted
        steerMotor.inverted = steerMotorInverted

        driveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative)

        steerPID.enableContinuousInput(-Math.PI, Math.PI)
    }

    /**
     * The position of the module in radians
     */
    val position get() = driveMotor.selectedSensorPosition.toRadians(CustomUnitTypes.TalonFX)

    /**
     * The speed of the drive motor in radians per second
     */
    val velocity get() = driveMotor.selectedSensorVelocity.toRadiansPerSecond(CustomUnitTypes.TalonFX)

    /**
     * The angle of the steer encoder in radians
     */
    val steer get() = steerEncoder.absolutePosition.toRadians() + steerEncoderOffset

    var state: SwerveModuleState
        /**
         * Retrieves the speed and angle of the swerve module
         */
        get() = SwerveModuleState(velocity, Rotation2d.fromRadians(steer))
        /**
         * Sets the speed and angle of the swerve module
         */
        set(value) {
            val (speed, angle) = value

            if (Swerve.DEADBAND && speed < Swerve.DEADBAND_THRESHOLD) { // Add deadband to the drive motor
                stop()
                return
            }

            /**
             * Determines the desired position, speed, and orientation of the swerve drive module
             * It helps minimize the difference between the desired state and the actual state of the module.
             *
             * See [FRC 0 to Autonomous: #6 Swerve Drive Auto](https://youtu.be/0Xi9yb1IMyA?t=220) for visual
             */
            val optimized = SwerveModuleState.optimize(value, angle)

            // TODO Add feedforward and PIDControl for the drive motor
            driveMotor.set(optimized.speedMetersPerSecond / Swerve.MAX_VELOCITY_PER_SECOND)
            steerMotor.set(steerPID.calculate(steer, optimized.angle.radians))
        }

    /**
     * Stops the module.
     * Should only be used for deadband or emergency stop, where the requested value already goes through a PID
     * controller and is not a direct value, as it may cause the module to jerk, tip, or otherwise move in an
     * undesirable way.
     */
    fun stop() {
        driveMotor.set(0.0)
        steerMotor.set(0.0)
    }

}
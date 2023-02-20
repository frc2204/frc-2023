package org.crosspointacademy.lib.swerve

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.can.TalonFX
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import com.ctre.phoenix.sensors.CANCoder
import com.ctre.phoenix.sensors.WPI_CANCoder
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.SwerveModulePosition
import edu.wpi.first.math.kinematics.SwerveModuleState
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.crosspointacademy.frc.config.Swerve.DRIVE_FEED_FORWARD
import org.crosspointacademy.frc.config.Swerve.DRIVE_INVERTED
import org.crosspointacademy.frc.config.Swerve.DRIVE_NEUTRAL_MODE
import org.crosspointacademy.frc.config.Swerve.DRIVE_RATIO
import org.crosspointacademy.frc.config.Swerve.MAX_SPEED
import org.crosspointacademy.frc.config.Swerve.STEER_INVERTED
import org.crosspointacademy.frc.config.Swerve.STEER_NEUTRAL_MODE
import org.crosspointacademy.frc.config.Swerve.STEER_RATIO
import org.crosspointacademy.frc.config.Swerve.WHEEL_CIRCUMFERENCE
import org.crosspointacademy.lib.swerve.util.CTREModuleState.optimize
import org.crosspointacademy.lib.swerve.util.Conversions.degreesToFalcon
import org.crosspointacademy.lib.swerve.util.Conversions.falconToDegrees
import org.crosspointacademy.lib.swerve.util.Conversions.falconToMPS
import org.crosspointacademy.lib.swerve.util.Conversions.mpsToFalcon
import kotlin.math.abs

class SwerveModule(val configuration: SwerveModuleConfiguration) {


    private val absoluteEncoder = WPI_CANCoder(configuration.steerEncoderCANId)
    val driveMotor = WPI_TalonFX(configuration.driveMotorCANId)
    private val steerMotor = WPI_TalonFX(configuration.steerMotorCANId)

    private val driveFeedForward = DRIVE_FEED_FORWARD.build()

    private val steerEncoderOffset = configuration.steerEncoderOffset

    private var lastAngle: Rotation2d

    init {
        absoluteEncoder.apply {
            configFactoryDefault()
            configAllSettings(SwerveCTREConfigurations.CANCoder)
        }

        driveMotor.apply {
            configFactoryDefault()
            configAllSettings(SwerveCTREConfigurations.driveFalcon)
            inverted = DRIVE_INVERTED
            setNeutralMode(DRIVE_NEUTRAL_MODE)
            selectedSensorPosition = 0.0
        }

        steerMotor.apply {
            configFactoryDefault()
            configAllSettings(SwerveCTREConfigurations.steerFalcon)
            inverted = STEER_INVERTED
            setNeutralMode(STEER_NEUTRAL_MODE)
            resetToAbsolutePosition()
        }

        lastAngle = angle
    }

    val state get() = SwerveModuleState(
        falconToMPS(driveMotor.selectedSensorVelocity, WHEEL_CIRCUMFERENCE, DRIVE_RATIO),
        angle
    )

    private fun setSpeed(state: SwerveModuleState, openLoop: Boolean) {
        if (openLoop) {
            val percentOutput = state.speedMetersPerSecond / MAX_SPEED
            driveMotor.set(ControlMode.PercentOutput, percentOutput)
        } else {
            val velocity = mpsToFalcon(state.speedMetersPerSecond, WHEEL_CIRCUMFERENCE, DRIVE_RATIO)
            driveMotor.set(
                ControlMode.Velocity,
                velocity,
                DemandType.ArbitraryFeedForward,
                driveFeedForward.calculate(state.speedMetersPerSecond)
            )
        }
    }

    private fun setAngle(state: SwerveModuleState) {
        val angle = if (abs(state.speedMetersPerSecond) <= MAX_SPEED * 0.01) lastAngle else state.angle
        steerMotor.set(ControlMode.Position, degreesToFalcon(angle.degrees, STEER_RATIO))
        lastAngle = angle
    }

    fun setDesiredState(desired: SwerveModuleState, openLoop: Boolean) {
        val optimizedState = optimize(desired, angle)
        setSpeed(optimizedState, openLoop)
        setAngle(optimizedState)

        SmartDashboard.putNumber("Swerve Desired: ${configuration.name} Speed", desired.speedMetersPerSecond)
        SmartDashboard.putNumber("Swerve Desired: ${configuration.name} Angle", desired.angle.degrees)
        SmartDashboard.putNumber("Swerve Optimized Desired: ${configuration.name} Speed", optimizedState.speedMetersPerSecond)
        SmartDashboard.putNumber("Swerve Optimized Desired: ${configuration.name} Angle", optimizedState.angle.degrees)
    }

    val position get() = SwerveModulePosition(
        falconToMPS(driveMotor.selectedSensorPosition, WHEEL_CIRCUMFERENCE, DRIVE_RATIO),
        angle
    )

    private val angle get() = Rotation2d.fromDegrees(falconToDegrees(steerMotor.selectedSensorPosition, STEER_RATIO))
    val canCoder get() = Rotation2d.fromDegrees(absoluteEncoder.absolutePosition)

    fun resetToAbsolutePosition() {
        val position = degreesToFalcon(canCoder.degrees - steerEncoderOffset.degrees, STEER_RATIO)
        steerMotor.selectedSensorPosition = position
    }

}
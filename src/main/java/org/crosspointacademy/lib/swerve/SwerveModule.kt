package org.crosspointacademy.lib.swerve

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.DemandType
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import com.ctre.phoenix.sensors.WPI_CANCoder
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.SwerveModulePosition
import edu.wpi.first.math.kinematics.SwerveModuleState
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import org.crosspointacademy.frc.Robot
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
import org.crosspointacademy.lib.swerve.util.Conversions.falconToMeters
import org.crosspointacademy.lib.swerve.util.Conversions.metersToFalcon
import org.crosspointacademy.lib.swerve.util.Conversions.mpsToFalcon
import kotlin.math.abs
import kotlin.math.roundToInt

class SwerveModule(val config: SwerveModuleConfiguration) {

    private val absoluteEncoder = WPI_CANCoder(config.steerEncoderCANId)
    private val driveMotor = WPI_TalonFX(config.driveMotorCANId)
    private val steerMotor = WPI_TalonFX(config.steerMotorCANId)

    private val driveFeedForward = DRIVE_FEED_FORWARD.build()

    private val steerEncoderOffset = if (Robot.real) config.steerEncoderOffset else config.simulationEncoderOffset

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


    val state
        get() = SwerveModuleState(
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

        if (Robot.simulation) {
            driveMotor.selectedSensorPosition += metersToFalcon(
                state.speedMetersPerSecond * 0.02,
                WHEEL_CIRCUMFERENCE,
                DRIVE_RATIO
            ).roundToInt()
        }
    }

    private fun setAngle(state: SwerveModuleState) {
        val angle = if (abs(state.speedMetersPerSecond) <= MAX_SPEED * 0.01) lastAngle else state.angle
        val position = degreesToFalcon(angle.degrees, STEER_RATIO)
        steerMotor.set(ControlMode.Position, position)
        lastAngle = angle

        if (Robot.simulation) {
            steerMotor.selectedSensorPosition = position
        }
    }

    fun setDesiredState(desired: SwerveModuleState, openLoop: Boolean) {
        val optimizedState = optimize(desired, angle)
        setSpeed(optimizedState, openLoop)
        setAngle(optimizedState)

        SmartDashboard.putNumber("Swerve Desired: ${config.name} Speed", desired.speedMetersPerSecond)
        SmartDashboard.putNumber("Swerve Desired: ${config.name} Angle", desired.angle.degrees)
        SmartDashboard.putNumber(
            "Swerve Optimized Desired: ${config.name} Speed",
            optimizedState.speedMetersPerSecond
        )
        SmartDashboard.putNumber("Swerve Optimized Desired: ${config.name} Angle", optimizedState.angle.degrees)
    }

    val position
        get() = SwerveModulePosition(
            falconToMeters(driveMotor.selectedSensorPosition, WHEEL_CIRCUMFERENCE, DRIVE_RATIO),
            angle
        )

    private val angle: Rotation2d
        get() = Rotation2d.fromDegrees(
            falconToDegrees(steerMotor.selectedSensorPosition, STEER_RATIO)
        )

    val canCoder: Rotation2d get() = Rotation2d.fromDegrees(absoluteEncoder.absolutePosition)

    fun resetToAbsolutePosition() {
        val position = degreesToFalcon(canCoder.degrees - steerEncoderOffset.degrees, STEER_RATIO)
        steerMotor.selectedSensorPosition = position
    }

}
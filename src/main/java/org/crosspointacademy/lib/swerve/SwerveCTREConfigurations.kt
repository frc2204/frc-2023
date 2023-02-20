package org.crosspointacademy.lib.swerve

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration
import com.ctre.phoenix.sensors.AbsoluteSensorRange
import com.ctre.phoenix.sensors.CANCoderConfiguration
import com.ctre.phoenix.sensors.SensorInitializationStrategy
import com.ctre.phoenix.sensors.SensorTimeBase
import org.crosspointacademy.frc.config.Swerve
import org.crosspointacademy.frc.config.Swerve.DRIVE_CLOSED_LOOP_RAMP
import org.crosspointacademy.frc.config.Swerve.DRIVE_CURRENT_LIMIT
import org.crosspointacademy.frc.config.Swerve.DRIVE_CURRENT_LIMIT_ENABLED

import org.crosspointacademy.frc.config.Swerve.DRIVE_CURRENT_PEAK_DURATION
import org.crosspointacademy.frc.config.Swerve.DRIVE_CURRENT_PEAK_LIMIT
import org.crosspointacademy.frc.config.Swerve.DRIVE_OPEN_LOOP_RAMP
import org.crosspointacademy.frc.config.Swerve.DRIVE_PID
import kotlin.time.DurationUnit

object SwerveCTREConfigurations {

    val CANCoder = CANCoderConfiguration().apply {
        absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360
        sensorDirection = Swerve.INVERTED_ENCODERS
        initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition
        sensorTimeBase = SensorTimeBase.PerSecond
    }

    private val driveFalconCurrentLimits = SupplyCurrentLimitConfiguration(
        DRIVE_CURRENT_LIMIT_ENABLED,
        DRIVE_CURRENT_LIMIT,
        DRIVE_CURRENT_PEAK_LIMIT,
        DRIVE_CURRENT_PEAK_DURATION.toDouble(DurationUnit.SECONDS)
    )

    val driveFalcon = TalonFXConfiguration().apply {
        slot0.apply {
            kP = DRIVE_PID.proportional
            kI = DRIVE_PID.integral
            kD = DRIVE_PID.derivative
            kF = DRIVE_PID.filtered ?: 0.0
        }
        supplyCurrLimit = driveFalconCurrentLimits
        openloopRamp = DRIVE_OPEN_LOOP_RAMP
        closedloopRamp = DRIVE_CLOSED_LOOP_RAMP
    }

    private val steerFalconCurrentLimits = SupplyCurrentLimitConfiguration(
        Swerve.STEER_CURRENT_LIMIT_ENABLED,
        Swerve.STEER_CURRENT_LIMIT,
        Swerve.STEER_CURRENT_PEAK_LIMIT,
        Swerve.STEER_CURRENT_PEAK_DURATION.toDouble(DurationUnit.SECONDS)
    )

    val steerFalcon = TalonFXConfiguration().apply {
        slot0.apply {
            kP = Swerve.STEER_PID.proportional
            kI = Swerve.STEER_PID.integral
            kD = Swerve.STEER_PID.derivative
            kF = Swerve.STEER_PID.filtered ?: 0.0
        }
        supplyCurrLimit = steerFalconCurrentLimits
    }

}
package org.crosspointacademy.frc.config

import com.ctre.phoenix.motorcontrol.NeutralMode
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.kinematics.SwerveDriveKinematics
import edu.wpi.first.math.util.Units
import org.crosspointacademy.lib.swerve.util.MotorFeedForwardBuilder
import org.crosspointacademy.lib.swerve.util.PIDFBuilder
import kotlin.time.Duration.Companion.milliseconds

object Swerve {

    const val DRIVE_POWER = 0.3
    const val ROTATIONAL_POWER = 0.6

    private val WHEEL_RADIUS = Units.inchesToMeters(2.0)
    val WHEEL_CIRCUMFERENCE = 2 * Math.PI * WHEEL_RADIUS

    val MAX_SPEED = Units.feetToMeters(15.13)
    val MAX_OMEGA: Rotation2d = Rotation2d.fromRadians(6.0)// TODO requires testing

    // Auto configuration
    val AUTO_TRANSLATIONAL_PID = PIDFBuilder(2.0, 0.0, 0.0)
    val AUTO_ROTATIONAL_PID = PIDFBuilder(2.0, 0.0, 0.0)
    val AUTO_MAX_VELOCITY = Units.feetToMeters(4.0)
    val AUTO_MAX_ACCELERATION = Units.feetToMeters(3.0)

    const val INVERTED_GYRO = false
    const val INVERTED_ENCODERS = false

    // Drive Configuration
    const val DRIVE_RATIO = 7.36 / 1
    const val DRIVE_INVERTED = false
    val DRIVE_NEUTRAL_MODE = NeutralMode.Brake
    val DRIVE_PID = PIDFBuilder(0.08, 0.0, 0.0, 0.0)
    val DRIVE_FEED_FORWARD = MotorFeedForwardBuilder(0.0, 0.0, 0.0)
    const val DRIVE_CURRENT_LIMIT_ENABLED = true
    const val DRIVE_CURRENT_LIMIT = 35.0
    const val DRIVE_CURRENT_PEAK_LIMIT = 60.0
    val DRIVE_CURRENT_PEAK_DURATION = 100.milliseconds
    const val DRIVE_OPEN_LOOP_RAMP = 0.25
    const val DRIVE_CLOSED_LOOP_RAMP = 0.0

    // Steer Configuration
    const val STEER_RATIO = 15.43 / 1
    const val STEER_INVERTED = true
    val STEER_NEUTRAL_MODE = NeutralMode.Coast
    val STEER_PID = PIDFBuilder(0.15, 0.0, 0.0, 0.0)
    const val STEER_CURRENT_LIMIT_ENABLED = true
    const val STEER_CURRENT_LIMIT = 25.0
    const val STEER_CURRENT_PEAK_LIMIT = 40.0
    val STEER_CURRENT_PEAK_DURATION = 100.milliseconds


    // Chassis Configuration

    /**
     * The track width is the distance between the left and right wheels on the same side of the robot.
     */
    private val TRACK_WIDTH = Units.inchesToMeters(23.25)

    /**
     * The wheelbase is the distance between the front and back wheels on the same side of the robot.
     */
    private val WHEEL_BASE = Units.inchesToMeters(23.25)

    private val FRONT_LEFT_LOCATION = Translation2d(WHEEL_BASE / 2, TRACK_WIDTH / 2)
    private val FRONT_RIGHT_LOCATION = Translation2d(WHEEL_BASE / 2, -TRACK_WIDTH / 2)
    private val BACK_LEFT_LOCATION = Translation2d(-WHEEL_BASE / 2, TRACK_WIDTH / 2)
    private val BACK_RIGHT_LOCATION = Translation2d(-WHEEL_BASE / 2, -TRACK_WIDTH / 2)

    val KINEMATICS = SwerveDriveKinematics(
        FRONT_LEFT_LOCATION,
        FRONT_RIGHT_LOCATION,
        BACK_LEFT_LOCATION,
        BACK_RIGHT_LOCATION
    )

}
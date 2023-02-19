package org.crosspointacademy.frc.config

import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.kinematics.SwerveDriveKinematics
import edu.wpi.first.math.util.Units
import org.crosspointacademy.frc.lib.PIDBuilder

object Swerve {

    /**
     * The maximum velocity of the robot in meters per second.
     */
    val MAX_VELOCITY_PER_SECOND = 0.1
    /**
     * The maximum angular velocity of the robot in radians per second.
     */
    val MAX_OMEGA_PER_SECOND = 0.1

    /**
     * The front left module's drive motor CAN id.
     */
    const val FRONT_LEFT_DRIVE = 1
    /**
     * Whether the front left module's drive motor is inverted.
     */
    const val FRONT_LEFT_DRIVE_INVERTED = false
    /**
     * The front left module's steer motor CAN id.
     */
    const val FRONT_LEFT_STEER = 2
    /**
     * Whether the front left module's steer motor is inverted.
     */
    const val FRONT_LEFT_STEER_INVERTED = false
    /**
     * The front left module's steer encoder CAN id.
     */
    const val FRONT_LEFT_STEER_ENCODER = 3
    /**
     * The front left module's steer encoder offset in radians.
     */
    const val FRONT_LEFT_STEER_OFFSET = 0.0

    /**
     * The front right module's drive motor CAN id.
     */
    const val FRONT_RIGHT_DRIVE = 4
    /**
     * Whether the front right module's drive motor is inverted.
     */
    const val FRONT_RIGHT_DRIVE_INVERTED = false
    /**
     * The front right module's steer motor CAN id.
     */
    const val FRONT_RIGHT_STEER = 5
    /**
     * Whether the front right module's steer motor is inverted.
     */
    const val FRONT_RIGHT_STEER_INVERTED = false
    /**
     * The front right module's steer encoder CAN id.
     */
    const val FRONT_RIGHT_STEER_ENCODER = 6
    /**
     * The front right module's steer encoder offset in radians.
     */
    const val FRONT_RIGHT_STEER_OFFSET = 0.0

    /**
     * The back left module's drive motor CAN id.
     */
    const val BACK_LEFT_DRIVE = 7
    /**
     * Whether the back left module's drive motor is inverted.
     */
    const val BACK_LEFT_DRIVE_INVERTED = false
    /**
     * The back left module's steer motor CAN id.
     */
    const val BACK_LEFT_STEER = 8
    /**
     * Whether the back left module's steer motor is inverted.
     */
    const val BACK_LEFT_STEER_INVERTED = false
    /**
     * The back left module's steer encoder CAN id.
     */
    const val BACK_LEFT_STEER_ENCODER = 9
    /**
     * The back left module's steer encoder offset in radians.
     */
    const val BACK_LEFT_STEER_OFFSET = 0.0

    /**
     * The back right module's drive motor CAN id.
     */
    const val BACK_RIGHT_DRIVE = 10
    /**
     * Whether the back right module's drive motor is inverted.
     */
    const val BACK_RIGHT_DRIVE_INVERTED = false
    /**
     * The back right module's steer motor CAN id.
     */
    const val BACK_RIGHT_STEER = 11
    /**
     * Whether the back right module's steer motor is inverted.
     */
    const val BACK_RIGHT_STEER_INVERTED = false
    /**
     * The back right module's steer encoder CAN id.
     */
    const val BACK_RIGHT_STEER_ENCODER = 12
    /**
     * The back right module's steer encoder offset in radians.
     */
    const val BACK_RIGHT_STEER_OFFSET = 0.0

    val GLOBAL_STEER_PID = PIDBuilder(0.1, 0.0, 0.0)

    /**
     * Rate limiter for translational velocity in meters per second.
     */
    const val TRANSLATIONAL_SLEW_RATE = 5.0
    /**
     * Rate limiter for rotational velocity in radians per second.
     */
    const val ROTATIONAL_SLEW_RATE = 5.0

    /**
     * Whether to use the deadband.
     */
    const val DEADBAND = true
    /**
     * The deadband threshold.
     */
    const val DEADBAND_THRESHOLD = 0.01

    /**
     * The track width is the distance between the left and right wheels on the same side of the robot.
     */
    private val TRACK_WIDTH = Units.inchesToMeters(23.25)

    /**
     * The wheelbase is the distance between the front and back wheels on the same side of the robot.
     */
    private val WHEEL_BASE = Units.feetToMeters(23.25)

    /**
     * The front left module's location on the robot.
     */
    private val FRONT_LEFT_LOCATION = Translation2d(WHEEL_BASE / 2, TRACK_WIDTH / 2)

    /**
     * The front right module's location on the robot.
     */
    private val FRONT_RIGHT_LOCATION = Translation2d(WHEEL_BASE / 2, -TRACK_WIDTH / 2)

    /**
     * The back left module's location on the robot.
     */
    private val BACK_LEFT_LOCATION = Translation2d(-WHEEL_BASE / 2, TRACK_WIDTH / 2)

    /**
     * The back right module's location on the robot.
     */
    private val BACK_RIGHT_LOCATION = Translation2d(-WHEEL_BASE / 2, -TRACK_WIDTH / 2)

    /**
     * The robot's kinematics.
     */
    val KINEMATICS = SwerveDriveKinematics(
        FRONT_LEFT_LOCATION,
        FRONT_RIGHT_LOCATION,
        BACK_LEFT_LOCATION,
        BACK_RIGHT_LOCATION
    )

}
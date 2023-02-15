package org.crosspointacademy.frc.config

import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.kinematics.SwerveDriveKinematics
import edu.wpi.first.math.util.Units
import org.crosspointacademy.frc.lib.PIDBuilder

object Swerve {

    val MAX_VELOCITY_PER_SECOND = Units.feetToMeters(4.0)
    val MAX_OMEGA_PER_SECOND = Units.degreesToRadians(360.0)

    const val FRONT_LEFT_DRIVE = 1
    const val FRONT_LEFT_DRIVE_INVERTED = false
    const val FRONT_LEFT_STEER = 2
    const val FRONT_LEFT_STEER_INVERTED = false
    const val FRONT_LEFT_STEER_ENCODER = 3
    const val FRONT_LEFT_STEER_OFFSET = 0.0
    val FRONT_LEFT_STEER_PID = PIDBuilder(0.0, 0.0, 0.0)

    const val FRONT_RIGHT_DRIVE = 4
    const val FRONT_RIGHT_DRIVE_INVERTED = false
    const val FRONT_RIGHT_STEER = 5
    const val FRONT_RIGHT_STEER_INVERTED = false
    const val FRONT_RIGHT_STEER_ENCODER = 6
    const val FRONT_RIGHT_STEER_OFFSET = 0.0
    val FRONT_RIGHT_STEER_PID = PIDBuilder(0.0, 0.0, 0.0)

    const val BACK_LEFT_DRIVE = 7
    const val BACK_LEFT_DRIVE_INVERTED = false
    const val BACK_LEFT_STEER = 8
    const val BACK_LEFT_STEER_INVERTED = false
    const val BACK_LEFT_STEER_ENCODER = 9
    const val BACK_LEFT_STEER_OFFSET = 0.0
    val BACK_LEFT_STEER_PID = PIDBuilder(0.0, 0.0, 0.0)

    const val BACK_RIGHT_DRIVE = 10
    const val BACK_RIGHT_DRIVE_INVERTED = false
    const val BACK_RIGHT_STEER = 11
    const val BACK_RIGHT_STEER_INVERTED = false
    const val BACK_RIGHT_STEER_ENCODER = 12
    const val BACK_RIGHT_STEER_OFFSET = 0.0
    val BACK_RIGHT_STEER_PID = PIDBuilder(0.0, 0.0, 0.0)

    const val TRANSLATIONAL_SLEW_RATE = 0.0
    const val ROTATIONAL_SLEW_RATE = 0.0

    const val DEADBAND = true
    const val DEADBAND_THRESHOLD = 0.01

    /**
     * The track width is the distance between the left and right wheels on the same side of the robot.
     */
    private val TRACK_WIDTH = Units.inchesToMeters(23.25)

    /**
     * The wheelbase is the distance between the front and back wheels on the same side of the robot.
     */
    private val WHEEL_BASE = Units.feetToMeters(23.25)

    private val FRONT_LEFT_LOCATION = Translation2d( WHEEL_BASE / 2, TRACK_WIDTH / 2)
    private val FRONT_RIGHT_LOCATION = Translation2d( WHEEL_BASE / 2, - TRACK_WIDTH / 2)
    private val BACK_LEFT_LOCATION = Translation2d( - WHEEL_BASE / 2,  TRACK_WIDTH / 2)
    private val BACK_RIGHT_LOCATION = Translation2d( - WHEEL_BASE / 2, - TRACK_WIDTH / 2)

    val KINEMATICS = SwerveDriveKinematics(
        FRONT_LEFT_LOCATION,
        FRONT_RIGHT_LOCATION,
        BACK_LEFT_LOCATION,
        BACK_RIGHT_LOCATION
    )

}
package org.crosspointacademy.frc.subsystems

import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.SwerveModuleState
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj2.command.SubsystemBase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.crosspointacademy.frc.config.Swerve.BACK_LEFT_DRIVE
import org.crosspointacademy.frc.config.Swerve.BACK_LEFT_DRIVE_INVERTED
import org.crosspointacademy.frc.config.Swerve.BACK_LEFT_STEER
import org.crosspointacademy.frc.config.Swerve.BACK_LEFT_STEER_ENCODER
import org.crosspointacademy.frc.config.Swerve.BACK_LEFT_STEER_INVERTED
import org.crosspointacademy.frc.config.Swerve.BACK_LEFT_STEER_OFFSET
import org.crosspointacademy.frc.config.Swerve.BACK_LEFT_STEER_PID
import org.crosspointacademy.frc.config.Swerve.BACK_RIGHT_DRIVE
import org.crosspointacademy.frc.config.Swerve.BACK_RIGHT_DRIVE_INVERTED
import org.crosspointacademy.frc.config.Swerve.BACK_RIGHT_STEER
import org.crosspointacademy.frc.config.Swerve.BACK_RIGHT_STEER_ENCODER
import org.crosspointacademy.frc.config.Swerve.BACK_RIGHT_STEER_INVERTED
import org.crosspointacademy.frc.config.Swerve.BACK_RIGHT_STEER_OFFSET
import org.crosspointacademy.frc.config.Swerve.BACK_RIGHT_STEER_PID
import org.crosspointacademy.frc.config.Swerve.FRONT_LEFT_DRIVE
import org.crosspointacademy.frc.config.Swerve.FRONT_LEFT_DRIVE_INVERTED
import org.crosspointacademy.frc.config.Swerve.FRONT_LEFT_STEER
import org.crosspointacademy.frc.config.Swerve.FRONT_LEFT_STEER_ENCODER
import org.crosspointacademy.frc.config.Swerve.FRONT_LEFT_STEER_INVERTED
import org.crosspointacademy.frc.config.Swerve.FRONT_LEFT_STEER_OFFSET
import org.crosspointacademy.frc.config.Swerve.FRONT_LEFT_STEER_PID
import org.crosspointacademy.frc.config.Swerve.FRONT_RIGHT_DRIVE
import org.crosspointacademy.frc.config.Swerve.FRONT_RIGHT_DRIVE_INVERTED
import org.crosspointacademy.frc.config.Swerve.FRONT_RIGHT_STEER
import org.crosspointacademy.frc.config.Swerve.FRONT_RIGHT_STEER_ENCODER
import org.crosspointacademy.frc.config.Swerve.FRONT_RIGHT_STEER_INVERTED
import org.crosspointacademy.frc.config.Swerve.FRONT_RIGHT_STEER_OFFSET
import org.crosspointacademy.frc.config.Swerve.FRONT_RIGHT_STEER_PID
import org.crosspointacademy.frc.lib.SwerveModule
import kotlin.math.IEEErem

@OptIn(DelicateCoroutinesApi::class)
object SwerveSubsystem : SubsystemBase() {

    /**
     * The front left module
     */
    private val frontLeft = SwerveModule(
        FRONT_LEFT_DRIVE,
        FRONT_LEFT_DRIVE_INVERTED,
        FRONT_LEFT_STEER,
        FRONT_LEFT_STEER_INVERTED,
        FRONT_LEFT_STEER_ENCODER,
        FRONT_LEFT_STEER_OFFSET,
        FRONT_LEFT_STEER_PID,
    )

    /**
     * The front right module
     */
    private val frontRight = SwerveModule(
        FRONT_RIGHT_DRIVE,
        FRONT_RIGHT_DRIVE_INVERTED,
        FRONT_RIGHT_STEER,
        FRONT_RIGHT_STEER_INVERTED,
        FRONT_RIGHT_STEER_ENCODER,
        FRONT_RIGHT_STEER_OFFSET,
        FRONT_RIGHT_STEER_PID,
    )

    /**
     * The back left module
     */
    private val backLeft = SwerveModule(
        BACK_LEFT_DRIVE,
        BACK_LEFT_DRIVE_INVERTED,
        BACK_LEFT_STEER,
        BACK_LEFT_STEER_INVERTED,
        BACK_LEFT_STEER_ENCODER,
        BACK_LEFT_STEER_OFFSET,
        BACK_LEFT_STEER_PID,
    )

    /**
     * The back right module
     */
    private val backRight = SwerveModule(
        BACK_RIGHT_DRIVE,
        BACK_RIGHT_DRIVE_INVERTED,
        BACK_RIGHT_STEER,
        BACK_RIGHT_STEER_INVERTED,
        BACK_RIGHT_STEER_ENCODER,
        BACK_RIGHT_STEER_OFFSET,
        BACK_RIGHT_STEER_PID,
    )

    /**
     *
     */
    private val navX = AHRS(SPI.Port.kMXP)

    init {
        GlobalScope.launch {
            delay(1000)
            navX.reset()
        }
    }

    val heading get() = navX.angle.IEEErem(360.0)
    val rotation2d: Rotation2d get() = navX.rotation2d

    fun setModuleStates(states: Array<SwerveModuleState>) {
        frontLeft.state = states[0]
        frontRight.state = states[1]
        backLeft.state = states[2]
        backRight.state = states[3]
    }

    fun stop() {
        frontLeft.stop()
        frontRight.stop()
        backLeft.stop()
        backRight.stop()
    }

    /**
     * Resets the navX
     */
    fun resetHeading() = runOnce {
        navX.reset()
    }


}
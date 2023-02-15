package org.crosspointacademy.frc.commands

import edu.wpi.first.math.filter.SlewRateLimiter
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.config.Swerve
import org.crosspointacademy.frc.subsystems.SwerveSubsystem

/**
 * A command that drives the robot using the joystick.
 */
class SwerveTeleop(
    /**
     * The x component of the joystick.
     */
    private val x: () -> Double,

    /**
     * The y component of the joystick.
     */
    private val y: () -> Double,

    /**
     * The theta component of the joystick.
     */
    private val theta: () -> Double,

    /**
     * Whether the robot should be in field-oriented mode. (i.e. the robot's heading is the same as the field's heading)
     */
    private val fieldOriented: () -> Boolean,
) : CommandBase() {

    /**
     * A slew rate limiter for the x component of the joystick. This provides a smoother acceleration on the x-axis.
     */
    private val xSlewRateLimiter = SlewRateLimiter(Swerve.TRANSLATIONAL_SLEW_RATE)

    /**
     * A slew rate limiter for the y component of the joystick. This provides a smoother acceleration on the y-axis.
     */
    private val ySlewRateLimiter = SlewRateLimiter(Swerve.TRANSLATIONAL_SLEW_RATE)

    /**
     * A slew rate limiter for the theta component of the joystick. This provides a smoother rotational experience.
     */
    private val thetaSlewRateLimiter = SlewRateLimiter(Swerve.ROTATIONAL_SLEW_RATE)

    init {
        addRequirements(SwerveSubsystem)
    }

    /**
     * Executes the command.
     */
    override fun execute() {
        val x = x()
        val y = y()
        val theta = theta()

        val xSlewRateLimited = xSlewRateLimiter.calculate(x) * Swerve.MAX_VELOCITY_PER_SECOND
        val ySlewRateLimited = ySlewRateLimiter.calculate(y) * Swerve.MAX_VELOCITY_PER_SECOND
        val thetaSlewRateLimited = thetaSlewRateLimiter.calculate(theta) * Swerve.MAX_OMEGA_PER_SECOND

        val chassisSpeeds = if (fieldOriented()) {
            ChassisSpeeds.fromFieldRelativeSpeeds(
                xSlewRateLimited,
                ySlewRateLimited,
                thetaSlewRateLimited,
                SwerveSubsystem.rotation2d
            )
        } else {
            ChassisSpeeds(xSlewRateLimited, ySlewRateLimited, thetaSlewRateLimited)
        }

        val moduleStates = Swerve.KINEMATICS.toSwerveModuleStates(chassisSpeeds)

        SwerveSubsystem.setModuleStates(moduleStates)
    }

    /**
     * Stops the robot.
     */
    override fun end(interrupted: Boolean) {
        SwerveSubsystem.stop()
    }

    /**
     * Returns whether the command is finished. This command never finishes and is constantly running.
     */
    override fun isFinished() = false

}
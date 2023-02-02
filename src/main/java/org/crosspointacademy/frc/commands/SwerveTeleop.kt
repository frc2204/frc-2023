package org.crosspointacademy.frc.commands

import edu.wpi.first.math.filter.SlewRateLimiter
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.config.Swerve
import org.crosspointacademy.frc.subsystems.SwerveSubsystem

class SwerveTeleop(
    private val x: () -> Double,
    private val y: () -> Double,
    private val theta: () -> Double,
    private val fieldOriented: () -> Boolean,
) : CommandBase() {

    private val xSlewRateLimiter = SlewRateLimiter(Swerve.TRANSLATIONAL_SLEW_RATE)
    private val ySlewRateLimiter = SlewRateLimiter(Swerve.TRANSLATIONAL_SLEW_RATE)
    private val thetaSlewRateLimiter = SlewRateLimiter(Swerve.ROTATIONAL_SLEW_RATE)

    init {
        addRequirements(SwerveSubsystem)
    }

    override fun execute() {
        val x = x()
        val y = y()
        val theta = theta()

        val xSlewRateLimited = xSlewRateLimiter.calculate(x) * Swerve.MAX_VELOCITY_PER_SECOND
        val ySlewRateLimited = ySlewRateLimiter.calculate(y) * Swerve.MAX_VELOCITY_PER_SECOND
        val thetaSlewRateLimited = thetaSlewRateLimiter.calculate(theta) * Swerve.MAX_OMEGA_PER_SECOND

        val chassisSpeeds = if (fieldOriented()) {
            ChassisSpeeds.fromFieldRelativeSpeeds(xSlewRateLimited, ySlewRateLimited, thetaSlewRateLimited, SwerveSubsystem.rotation2d)
        } else {
            ChassisSpeeds(xSlewRateLimited, ySlewRateLimited, thetaSlewRateLimited)
        }

        val moduleStates = Swerve.KINEMATICS.toSwerveModuleStates(chassisSpeeds)

        SwerveSubsystem.setModuleStates(moduleStates)
    }

    override fun end(interrupted: Boolean) {
        SwerveSubsystem.stop()
    }

    override fun isFinished() = false

}
package org.crosspointacademy.frc.commands

import edu.wpi.first.math.MathUtil
import edu.wpi.first.math.filter.SlewRateLimiter
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.config.Controls.DRIVE_STICK_SLEW_RATE
import org.crosspointacademy.frc.config.Controls.ROTATION_STICK_SLEW_RATE
import org.crosspointacademy.frc.config.Controls.STICK_DEADBAND
import org.crosspointacademy.frc.config.Swerve.MAX_OMEGA
import org.crosspointacademy.frc.config.Swerve.MAX_SPEED
import org.crosspointacademy.frc.subsystems.SwerveSubsystem

class SwerveTeleop(
    private val translation: () -> Double,
    private val strafe: () -> Double,
    private val rotation: () -> Double,
    private val robotCentric: () -> Boolean,
) : CommandBase() {

    init {
        addRequirements(SwerveSubsystem)
    }

    private val translationalSlewRateLimiter = SlewRateLimiter(DRIVE_STICK_SLEW_RATE)
    private val strafeSlewRateLimiter = SlewRateLimiter(DRIVE_STICK_SLEW_RATE)
    private val rotationalSlewRateLimiter = SlewRateLimiter(ROTATION_STICK_SLEW_RATE)

    override fun execute() {

        val translationSlew = translationalSlewRateLimiter.calculate(translation())
        val strafeSlew = strafeSlewRateLimiter.calculate(strafe())
        val rotationSlew = rotationalSlewRateLimiter.calculate(rotation())

        val translationValue = MathUtil.applyDeadband(translationSlew, STICK_DEADBAND)
        val strafeValue = MathUtil.applyDeadband(strafeSlew, STICK_DEADBAND)
        val rotationValue = MathUtil.applyDeadband(rotationSlew, STICK_DEADBAND)

        SwerveSubsystem.drive(
            Translation2d(translationValue, strafeValue).times(MAX_SPEED),
            rotationValue * MAX_OMEGA.radians,
            !robotCentric(),
            true
        )
    }

}
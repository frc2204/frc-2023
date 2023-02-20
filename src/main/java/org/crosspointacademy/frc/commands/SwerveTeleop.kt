package org.crosspointacademy.frc.commands

import edu.wpi.first.math.MathUtil
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.config.Controls.STICK_DEADBAND
import org.crosspointacademy.frc.config.Swerve.MAX_OMEGA
import org.crosspointacademy.frc.config.Swerve.MAX_SPEED
import org.crosspointacademy.frc.subsystems.SwerveSubsystem

class SwerveTeleop(
    private val translation: () -> Double,
    private val strafe: () -> Double,
    private val rotation: () -> Double,
    private val fieldOriented: () -> Boolean,
) : CommandBase() {

    init {
        addRequirements(SwerveSubsystem)
    }

    override fun execute() {
        val translationValue = MathUtil.applyDeadband(translation(), STICK_DEADBAND)
        val strafeValue = MathUtil.applyDeadband(strafe(), STICK_DEADBAND)
        val rotationValue = MathUtil.applyDeadband(rotation(), STICK_DEADBAND)

        SwerveSubsystem.drive(
            Translation2d(translationValue, strafeValue).times(MAX_SPEED),
            rotationValue * MAX_OMEGA.radians,
            fieldOriented(),
            true
        )
    }

}
package org.crosspointacademy.frc.commands.claw

import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.subsystems.ClawSubsystem

class CloseClaw : CommandBase() {

    init {
        addRequirements(ClawSubsystem)
    }

    override fun initialize() {
        ClawSubsystem.close()
    }

    override fun isFinished(): Boolean {
        return true
    }

}
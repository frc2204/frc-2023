package org.crosspointacademy.frc.commands.claw

import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.subsystems.ClawSubsystem

class OpenClaw : CommandBase() {

    init {
        addRequirements(ClawSubsystem)
    }

    override fun initialize() {
        ClawSubsystem.open()
    }

    override fun isFinished(): Boolean {
        return true
    }

}
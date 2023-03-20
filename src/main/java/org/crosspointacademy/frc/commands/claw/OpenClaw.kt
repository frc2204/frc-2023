package org.crosspointacademy.frc.commands.claw

import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.subsystems.ClawSubsystem
import org.crosspointacademy.lib.Logger.cmd

class OpenClaw : CommandBase() {

    init {
        addRequirements(ClawSubsystem)
    }

    override fun initialize() {
        cmd("Opening claw")
        ClawSubsystem.open()
    }

    override fun isFinished(): Boolean {
        return true
    }

}
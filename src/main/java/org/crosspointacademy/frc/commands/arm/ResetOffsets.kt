package org.crosspointacademy.frc.commands.arm

import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.subsystems.ArmSubsystem

class ResetOffsets : CommandBase() {

    init {
        addRequirements(ArmSubsystem)
    }

    override fun initialize() {
        ArmSubsystem.firstJointOffset = 0.0
        ArmSubsystem.secondJointOffset = 0.0
    }

    override fun isFinished(): Boolean {
        return true
    }

}
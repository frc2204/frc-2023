package org.crosspointacademy.frc.commands.arm.offset

import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.config.Arm.OFFSET_AMOUNT
import org.crosspointacademy.frc.subsystems.ArmSubsystem

class SubtractOffsetJointTwo : CommandBase() {

    init {
        addRequirements(ArmSubsystem)
    }

    override fun initialize() {
        ArmSubsystem.secondJointOffset -= OFFSET_AMOUNT
    }

    override fun isFinished() : Boolean {
        return true
    }

}
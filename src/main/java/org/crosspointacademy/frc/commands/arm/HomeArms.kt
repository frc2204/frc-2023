package org.crosspointacademy.frc.commands.arm

import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.subsystems.ArmSubsystem

class HomeArms : CommandBase() {

    private val secondJointHomeCommand = ArmSubsystem.secondJoint.home()

    override fun initialize() {
        addRequirements(ArmSubsystem)
        ArmSubsystem.firstJoint.resetEncoder()
        secondJointHomeCommand.schedule()
    }

    override fun isFinished(): Boolean {
        return secondJointHomeCommand.isFinished
    }
}
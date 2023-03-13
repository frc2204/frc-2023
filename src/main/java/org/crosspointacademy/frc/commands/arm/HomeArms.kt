package org.crosspointacademy.frc.commands.arm

import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.subsystems.ArmSubsystem

object HomeArms : CommandBase() {

    private val firstJointHomeCommand = ArmSubsystem.firstJoint.home()
    private val secondJointHomeCommand = ArmSubsystem.secondJoint.home()

    override fun initialize() {
        addRequirements(ArmSubsystem)
        firstJointHomeCommand.schedule()
        secondJointHomeCommand.schedule()
    }

    override fun isFinished(): Boolean {
        return firstJointHomeCommand.isFinished && secondJointHomeCommand.isFinished
    }

}
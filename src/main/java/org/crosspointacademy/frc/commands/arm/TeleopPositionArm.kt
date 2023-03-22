package org.crosspointacademy.frc.commands.arm

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.subsystems.ArmSubsystem

class TeleopPositionArm : CommandBase() {

    init {
        addRequirements(ArmSubsystem)
    }

    override fun execute() {
        val positions = ArmSubsystem.selectedPosition
        SmartDashboard.putString("Current position", positions?.name ?: "None")
        positions?.let {
            ArmSubsystem.firstJoint.position = it.jointOnePosition + ArmSubsystem.firstJointOffset
            ArmSubsystem.secondJoint.position = it.jointTwoPosition + ArmSubsystem.secondJointOffset
        }
    }

    override fun isFinished(): Boolean {
        return false
    }

}
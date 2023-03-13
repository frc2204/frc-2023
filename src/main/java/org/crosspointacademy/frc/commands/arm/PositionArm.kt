package org.crosspointacademy.frc.commands.arm

import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.config.Arm
import org.crosspointacademy.frc.subsystems.ArmSubsystem

class PositionArm(private val positions: Arm.Positions) : CommandBase() {

    override fun initialize() {
        addRequirements(ArmSubsystem)
        println("PositionArm to ${positions.name}...")
    }

    override fun execute() {
        ArmSubsystem.firstJoint.position = positions.jointOnePosition
        ArmSubsystem.secondJoint.position = positions.jointTwoPosition
    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun cancel() {
        println("PositionArm to ${positions.name} cancelled!")
    }

}
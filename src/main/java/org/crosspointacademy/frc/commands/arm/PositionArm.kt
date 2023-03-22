package org.crosspointacademy.frc.commands.arm

import edu.wpi.first.wpilibj2.command.CommandBase
import org.crosspointacademy.frc.config.Arm
import org.crosspointacademy.frc.subsystems.ArmSubsystem

class PositionArm(
    private val positions: Arm.Positions,
    private val errJ1: Double,
    private val errJ2: Double
): CommandBase() {

    init {
        addRequirements(ArmSubsystem)
    }

    override fun initialize() {
        ArmSubsystem.firstJoint.position = positions.jointOnePosition + ArmSubsystem.firstJointOffset
        ArmSubsystem.secondJoint.position = positions.jointTwoPosition + ArmSubsystem.secondJointOffset
    }

    override fun isFinished(): Boolean {
        return ArmSubsystem.firstJoint.position in (-errJ1..errJ1) && ArmSubsystem.secondJoint.position in (-errJ2..errJ2)
    }

}
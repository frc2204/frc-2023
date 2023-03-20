package org.crosspointacademy.frc.subsystems

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.crosspointacademy.frc.config.Arm
import org.crosspointacademy.frc.config.Arm.FIRST_JOINT_CONFIG
import org.crosspointacademy.frc.config.Arm.FIRST_JOINT_SPARK_ID
import org.crosspointacademy.frc.config.Arm.SECOND_JOINT_CONFIG
import org.crosspointacademy.frc.config.Arm.SECOND_JOINT_SPARK_ID
import org.crosspointacademy.lib.control.homing.HomingNEOSparkMax

object ArmSubsystem : SubsystemBase() {

    val firstJoint = HomingNEOSparkMax(FIRST_JOINT_SPARK_ID, FIRST_JOINT_CONFIG)
    val secondJoint = HomingNEOSparkMax(SECOND_JOINT_SPARK_ID, SECOND_JOINT_CONFIG)

    var selectedPosition: Arm.Positions? = null

    fun setTargetPosition(position: Arm.Positions?): CommandBase = runOnce {
        selectedPosition = position
    }
    
    override fun periodic() {
        SmartDashboard.putBoolean("First Joint Homed", firstJoint.atHome)
        SmartDashboard.putBoolean("Second Joint Homed", secondJoint.atHome)
        SmartDashboard.putNumber("First Joint Position", firstJoint.position)
        SmartDashboard.putNumber("Second Joint Position", secondJoint.position)
    }

}
package org.crosspointacademy.frc.subsystems

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.crosspointacademy.frc.config.Arm
import org.crosspointacademy.frc.config.Arm.FIRST_JOINT_CONFIG
import org.crosspointacademy.frc.config.Arm.FIRST_JOINT_SPARK_ID
import org.crosspointacademy.frc.config.Arm.SECOND_JOINT_CONFIG
import org.crosspointacademy.frc.config.Arm.SECOND_JOINT_SPARK_ID
import org.crosspointacademy.lib.Logger.cmd
import org.crosspointacademy.lib.control.homing.HomingNEOSparkMax

object ArmSubsystem : SubsystemBase() {

    val firstJoint = HomingNEOSparkMax(FIRST_JOINT_SPARK_ID, FIRST_JOINT_CONFIG)
    val secondJoint = HomingNEOSparkMax(SECOND_JOINT_SPARK_ID, SECOND_JOINT_CONFIG)

    var firstJointOffset = 0.0
    var secondJointOffset = 0.0

    var selectedPosition: Arm.Positions? = null
        set(value) {
            field = value
            cmd("Selected position set to $value")
        }

    fun setTargetPosition(position: Arm.Positions?): CommandBase = runOnce {
        selectedPosition = position
    }

    fun nextTargetPosition(): CommandBase = runOnce {
        selectedPosition = when(selectedPosition) {
            Arm.Positions.FLOOR_TIPPED -> Arm.Positions.FLOOR
            Arm.Positions.FLOOR -> Arm.Positions.HOME
            Arm.Positions.HOME -> Arm.Positions.FIRST_NODE
            Arm.Positions.FIRST_NODE -> Arm.Positions.SECOND_NODE
            Arm.Positions.SECOND_NODE -> Arm.Positions.THIRD_NODE
            Arm.Positions.THIRD_NODE -> Arm.Positions.THIRD_NODE
            else -> Arm.Positions.HOME
        }
    }

    fun previousTargetPosition(): CommandBase = runOnce {
        selectedPosition = when (selectedPosition) {
            Arm.Positions.FLOOR_TIPPED -> Arm.Positions.FLOOR_TIPPED
            Arm.Positions.FLOOR -> Arm.Positions.FLOOR_TIPPED
            Arm.Positions.HOME -> Arm.Positions.FLOOR
            Arm.Positions.FIRST_NODE -> Arm.Positions.HOME
            Arm.Positions.SECOND_NODE -> Arm.Positions.FIRST_NODE
            Arm.Positions.THIRD_NODE -> Arm.Positions.SECOND_NODE
            else -> Arm.Positions.HOME
        }
    }

    override fun periodic() {
        SmartDashboard.putBoolean("First Joint Homed", firstJoint.atHome)
        SmartDashboard.putBoolean("Second Joint Homed", secondJoint.atHome)
        SmartDashboard.putNumber("First Joint Position", firstJoint.position)
        SmartDashboard.putNumber("Second Joint Position", secondJoint.position)
    }

}
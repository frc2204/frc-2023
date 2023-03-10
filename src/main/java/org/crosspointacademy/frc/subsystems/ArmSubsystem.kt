package org.crosspointacademy.frc.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.crosspointacademy.frc.config.Arm.FIRST_JOINT_PID
import org.crosspointacademy.frc.config.Arm.FIRST_JOINT_SPARK_ID
import org.crosspointacademy.frc.config.Arm.SECOND_JOINT_PID
import org.crosspointacademy.frc.config.Arm.SECOND_JOINT_SPARK_ID

object ArmSubsystem : SubsystemBase() {

    private val firstJointSpark = CANSparkMax(FIRST_JOINT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val secondJointSpark = CANSparkMax(SECOND_JOINT_SPARK_ID, CANSparkMaxLowLevel.MotorType.kBrushless)

    init {
        firstJointSpark.apply {
            restoreFactoryDefaults()

            pidController.apply {
                p = FIRST_JOINT_PID.proportional
                i = FIRST_JOINT_PID.integral
                d = FIRST_JOINT_PID.derivative
            }

            idleMode = CANSparkMax.IdleMode.kBrake
        }

        secondJointSpark.apply {
            restoreFactoryDefaults()

            pidController.apply {
                p = SECOND_JOINT_PID.proportional
                i = SECOND_JOINT_PID.integral
                d = SECOND_JOINT_PID.derivative
            }

            idleMode = CANSparkMax.IdleMode.kBrake
        }
    }

}
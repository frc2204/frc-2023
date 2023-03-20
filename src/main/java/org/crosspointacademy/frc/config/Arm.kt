package org.crosspointacademy.frc.config

import com.revrobotics.CANSparkMax
import org.crosspointacademy.lib.PIDFBuilder
import org.crosspointacademy.lib.control.homing.HomingConfiguration

object Arm {

    const val FIRST_JOINT_SPARK_ID = 13
    const val SECOND_JOINT_SPARK_ID = 14

    val FIRST_JOINT_CONFIG = HomingConfiguration(
        "First Joint",
        0,
        0.2,
        15.0,
        PIDFBuilder(0.15, 0.0, 0.0),
        -0.3 to 0.3,
        CANSparkMax.IdleMode.kBrake,
    )

    val SECOND_JOINT_CONFIG = HomingConfiguration(
        "Second Joint",
        1,
        0.2,
        15.0,
        PIDFBuilder(0.1, 0.0, 0.0),
        -0.6 to 0.6,
        CANSparkMax.IdleMode.kBrake,
    )


    enum class Positions(
        val jointOnePosition: Double,
        val jointTwoPosition: Double,
    ) {

        HOME(0.0, 0.0),
        FLOOR(0.0, 0.0),
        FIRST_NODE(0.0, -10.0),
        SECOND_NODE(19.0, 10.0),
        THIRD_NODE(28.0, -1.0),

    }

}
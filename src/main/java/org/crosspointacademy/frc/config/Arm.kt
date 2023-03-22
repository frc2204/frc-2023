package org.crosspointacademy.frc.config

import com.revrobotics.CANSparkMax
import org.crosspointacademy.lib.PIDFBuilder
import org.crosspointacademy.lib.control.homing.HomingConfiguration

object Arm {

    const val FIRST_JOINT_SPARK_ID = 13
    const val SECOND_JOINT_SPARK_ID = 14
    const val OFFSET_AMOUNT = 0.1

    val FIRST_JOINT_CONFIG = HomingConfiguration(
        "First Joint",
        1,
        0.2,
        20.0,
        PIDFBuilder(0.15, 0.0, 0.0),
        -0.3 to 0.3,
        CANSparkMax.IdleMode.kBrake,
    )

    val SECOND_JOINT_CONFIG = HomingConfiguration(
        "Second Joint",
        0,
        -0.1,
        20.0,
        PIDFBuilder(0.1, 0.0, 0.0),
        -0.6 to 0.6,
        CANSparkMax.IdleMode.kBrake,
    )

    enum class Positions(
        val jointOnePosition: Double,
        val jointTwoPosition: Double,
    ) {

        FLOOR_TIPPED(21.0, 50.0),
        FLOOR(-2.0, 37.0),
        HOME(0.0, 0.0),
        FIRST_NODE(0.0, 34.0),
        SECOND_NODE(19.0, 9.4),
        THIRD_NODE(29.0, -3.0),

    }

}
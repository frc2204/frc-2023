package org.crosspointacademy.frc.config

import org.crosspointacademy.lib.PIDFBuilder

object Arm {

    const val FIRST_JOINT_SPARK_ID = 13
    const val SECOND_JOINT_SPARK_ID = 14

    val FIRST_JOINT_PID = PIDFBuilder(0.0, 0.0, 0.0, 0.0)
    val SECOND_JOINT_PID = PIDFBuilder(0.0, 0.0, 0.0, 0.0)
}
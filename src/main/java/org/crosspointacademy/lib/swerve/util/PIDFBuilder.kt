package org.crosspointacademy.lib.swerve.util

import edu.wpi.first.math.controller.PIDController

data class PIDFBuilder(
    val proportional: Double,
    val integral: Double,
    val derivative: Double,
    val filtered: Double? = null,
) {

    fun build(): PIDController {
        return PIDController(proportional, integral, derivative)
    }

}

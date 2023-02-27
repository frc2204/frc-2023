package org.crosspointacademy.lib.swerve.util

import com.pathplanner.lib.auto.PIDConstants

data class PIDFBuilder(
    val proportional: Double,
    val integral: Double,
    val derivative: Double,
    val filtered: Double? = null,
) {

    fun toPIDConstants() = PIDConstants(proportional, integral, derivative)

}

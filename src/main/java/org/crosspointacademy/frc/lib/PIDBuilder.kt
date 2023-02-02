package org.crosspointacademy.frc.lib

import edu.wpi.first.math.controller.PIDController

data class PIDBuilder(
    private val proportional: Double,
    private val integral: Double,
    private val derivative: Double
) {

    fun build() = PIDController(proportional, integral, derivative)

}
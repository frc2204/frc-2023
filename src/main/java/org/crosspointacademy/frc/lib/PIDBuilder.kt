package org.crosspointacademy.frc.lib

import edu.wpi.first.math.controller.PIDController

/**
 * A builder for PID controllers. This is used to make PID controllers more readable and configurable.
 */
data class PIDBuilder(
    private val proportional: Double,
    private val integral: Double,
    private val derivative: Double
) {

    /**
     * Builds a PID controller with the given parameters.
     */
    fun build() = PIDController(proportional, integral, derivative)

}
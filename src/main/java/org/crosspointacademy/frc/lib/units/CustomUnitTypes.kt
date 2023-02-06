package org.crosspointacademy.frc.lib.units

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

enum class CustomUnitTypes(val resolution: Int, val velocityPeriod: Duration) {

    /**
     * The encoder on the TalonFX has a resolution of 2048 ticks per revolution.
     * The velocity period is 100 milliseconds.
     */
    TalonFX(2048, 100.milliseconds),

}
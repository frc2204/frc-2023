package org.crosspointacademy.frc.lib.units

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

enum class CustomUnitTypes(val resolution: Int, val velocityPeriod: Duration) {

    CANCoder(4096, 1.seconds),
    TalonFX(2048, 100.milliseconds),

}
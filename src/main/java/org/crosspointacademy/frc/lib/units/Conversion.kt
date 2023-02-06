package org.crosspointacademy.frc.lib.units

import kotlin.time.DurationUnit

/**
 * Converts degrees to radians
 */
fun Double.toRadians() = this * Math.PI / 180

/**
 * Converts a custom unit type to radians
 */
fun Double.toRadians(type: CustomUnitTypes) = this * 2 * Math.PI / type.resolution

fun Double.toRadiansPerSecond(type: CustomUnitTypes) = this.toRadians(type) / type.velocityPeriod.toDouble(DurationUnit.SECONDS)
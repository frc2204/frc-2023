package org.crosspointacademy.lib.control.homing

import com.revrobotics.CANSparkMax.IdleMode
import org.crosspointacademy.lib.PIDFBuilder

data class HomingConfiguration(
    val name: String,
    val homingSwitchId: Int,
    val homingPower: Double,
    val slewRate: Double,
    val pidf: PIDFBuilder,
    val minMaxPower: Pair<Double, Double>,
    val idleMode: IdleMode
)
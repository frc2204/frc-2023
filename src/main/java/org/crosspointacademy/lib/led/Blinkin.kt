package org.crosspointacademy.lib.led

import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.motorcontrol.Spark

class Blinkin(channel: Int) : Spark(channel) {

    fun set(pattern: BlinkinPattern) {
        val currentAlliance = DriverStation.getAlliance()
        var currentAlliancePattern = BlinkinPattern.RED
        var oppositeAlliancePattern = BlinkinPattern.BLUE
        if (currentAlliance == DriverStation.Alliance.Blue) {
            currentAlliancePattern = BlinkinPattern.BLUE
            oppositeAlliancePattern = BlinkinPattern.RED
        }

        val desiredPattern = when (pattern) {
            BlinkinPattern.ALLIANCE -> currentAlliancePattern
            BlinkinPattern.OPPOSITE_ALLIANCE -> oppositeAlliancePattern
            else -> pattern
        }

        super.set(desiredPattern.pwmPower)

    }

}
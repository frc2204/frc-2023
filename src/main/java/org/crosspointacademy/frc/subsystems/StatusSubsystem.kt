package org.crosspointacademy.frc.subsystems

import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.crosspointacademy.frc.config.Status
import org.crosspointacademy.lib.led.Blinkin

object StatusSubsystem : SubsystemBase() {

    private val ledController = Blinkin(9)

    fun setStatus(status: Status) {
        ledController.set(status.pattern)
    }

}
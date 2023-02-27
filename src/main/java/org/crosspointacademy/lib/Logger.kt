package org.crosspointacademy.lib

import edu.wpi.first.wpilibj.Timer

object Logger {

    private fun logger(message: String) = println("[${Timer.getFPGATimestamp()}] [LOGGER] $message")

    fun input(message: String) = logger("[INPUT] : $message")

}
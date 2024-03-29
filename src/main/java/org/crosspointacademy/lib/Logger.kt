package org.crosspointacademy.lib

import edu.wpi.first.wpilibj.DriverStation

object Logger {

    fun startup(msg: String) {
        println("[STARTUP] $msg")
    }

    fun debug(msg: String) {
        println("[DEBUG] $msg")
    }

    fun cmd(msg: String) {
        println("[COMMAND] $msg")
    }

    fun warn(msg: String, trace: Boolean = false) {
        DriverStation.reportWarning(msg, trace)
    }

    fun err(msg: String, trace: Boolean) {
        DriverStation.reportError(msg, trace)
    }

}
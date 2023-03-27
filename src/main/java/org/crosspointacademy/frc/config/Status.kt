package org.crosspointacademy.frc.config

import org.crosspointacademy.lib.led.BlinkinPattern

enum class Status(val pattern: BlinkinPattern) {

    DISABLED(BlinkinPattern.ALLIANCE),

    CLAW_OPEN(BlinkinPattern.STROBE_RED),
    CLAW_CLOSE(BlinkinPattern.GREEN),

    DRIVE(BlinkinPattern.BPM_RAINBOW),




}
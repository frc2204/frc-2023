package org.crosspointacademy.frc.subsystems

import edu.wpi.first.wpilibj.PneumaticHub
import edu.wpi.first.wpilibj2.command.SubsystemBase

object ClawSubsystem : SubsystemBase() {

    private val pneumaticHub = PneumaticHub(15)

}
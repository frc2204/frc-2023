package org.crosspointacademy.frc.subsystems

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.PneumaticHub
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.crosspointacademy.frc.config.Claw.FORWARD_CHANNEL
import org.crosspointacademy.frc.config.Claw.PNEUMATIC_HUB_ID
import org.crosspointacademy.frc.config.Claw.REVERSE_CHANNEL
import org.crosspointacademy.frc.config.Status

object ClawSubsystem : SubsystemBase() {

    val pcm = PneumaticHub(PNEUMATIC_HUB_ID)
    val solenoid = pcm.makeDoubleSolenoid(FORWARD_CHANNEL, REVERSE_CHANNEL)

    init {
        pcm.enableCompressorDigital()
    }

    fun open() {
        solenoid.set(DoubleSolenoid.Value.kReverse)
        StatusSubsystem.setStatus(Status.CLAW_OPEN)
    }

    fun close() {
        solenoid.set(DoubleSolenoid.Value.kForward)
        StatusSubsystem.setStatus(Status.CLAW_CLOSE)
    }

}
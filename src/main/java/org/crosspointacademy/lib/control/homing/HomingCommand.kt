package org.crosspointacademy.lib.control.homing

import edu.wpi.first.wpilibj2.command.CommandBase

class HomingCommand(private val motor: HomingNEOSparkMax, private val config: HomingConfiguration) : CommandBase() {

    private var isFinished = false

    override fun initialize() {
        println("Homing ${config.name}...")
        motor.sparkMax.set(config.homingPower)
    }

    override fun execute() {
        motor.apply {
            homingState = HomingState.HOMING
            if (atHome) {
                resetEncoder()
                homingState = HomingState.HOMED
                isFinished = true
                println("${config.name} successfully homed!")
            }
        }
    }

    override fun isFinished(): Boolean {
        return isFinished
    }

    override fun end(interrupted: Boolean) {
        motor.stop()
    }

}
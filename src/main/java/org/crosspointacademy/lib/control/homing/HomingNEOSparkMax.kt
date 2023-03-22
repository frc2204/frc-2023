package org.crosspointacademy.lib.control.homing

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.math.filter.SlewRateLimiter
import edu.wpi.first.wpilibj.DigitalInput

/**
 * A wrapper around a SparkMax that allows for homing for precise positional control via PID.
 */
class HomingNEOSparkMax(deviceId: Int, private val homingConfiguration: HomingConfiguration) {

    val sparkMax = CANSparkMax(deviceId, CANSparkMaxLowLevel.MotorType.kBrushless)

    init {
        sparkMax.apply {
            pidController.apply {
                p = homingConfiguration.pidf.proportional
                i = homingConfiguration.pidf.integral
                d = homingConfiguration.pidf.derivative

                setOutputRange(homingConfiguration.minMaxPower.first, homingConfiguration.minMaxPower.second)
            }

            idleMode = homingConfiguration.idleMode
        }
    }

    private val homeSwitch = DigitalInput(homingConfiguration.homingSwitchId)
    var homingState = HomingState.NOT_HOMED
    val atHome get() = homeSwitch.get()

    private val slewRate = SlewRateLimiter(homingConfiguration.slewRate)

    var position
        get() = sparkMax.encoder.position
        set(value) {
            sparkMax.pidController.setReference(slewRate.calculate(value), CANSparkMax.ControlType.kPosition)
        }

    fun resetEncoder() {
        sparkMax.encoder.position = -2.0
    }

    fun home(): HomingCommand {
        return HomingCommand(this, homingConfiguration)
    }

    fun stop() {
        sparkMax.stopMotor()
    }

}
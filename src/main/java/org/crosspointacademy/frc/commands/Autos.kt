package org.crosspointacademy.frc.commands

import com.pathplanner.lib.auto.SwerveAutoBuilder
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import org.crosspointacademy.frc.commands.Autos.AutoEvents.pickupItem
import org.crosspointacademy.frc.commands.Autos.AutoEvents.placeNode
import org.crosspointacademy.frc.commands.claw.CloseClaw
import org.crosspointacademy.frc.commands.claw.OpenClaw
import org.crosspointacademy.frc.config.Arm
import org.crosspointacademy.frc.config.Swerve.AUTO_ROTATIONAL_PID
import org.crosspointacademy.frc.config.Swerve.AUTO_TRANSLATIONAL_PID
import org.crosspointacademy.frc.config.Swerve.KINEMATICS
import org.crosspointacademy.frc.subsystems.ArmSubsystem
import org.crosspointacademy.frc.subsystems.SwerveSubsystem

object Autos {


    val autoModeChooser = SendableChooser<AutoMode>().apply {
        AutoMode.values().forEach { addOption(it.optionName, it) }
        setDefaultOption(AutoMode.DEFAULT.optionName, AutoMode.DEFAULT)
    }

    init {
        SmartDashboard.putData("Auto Mode", autoModeChooser)
    }

    enum class AutoMode(val optionName: String, val pathName: String) {
        DEFAULT("Default", "Default Path"),
        BALANCE_PATH("Balance Path", "Balance Path"),
        RIGHT_PATH("Right Path", "Right Path"),
    }

    object AutoEvents {
        fun placeNode(positionCommand: Command) = SequentialCommandGroup(
            CloseClaw(),
            positionCommand,
            OpenClaw()
        )


        val pickupItem = SequentialCommandGroup(
            OpenClaw(),
            ArmSubsystem.setTargetPosition(Arm.Positions.FLOOR),
            CloseClaw(),
            ArmSubsystem.setTargetPosition(Arm.Positions.HOME),
        )
    }

    private val EVENT_MAP = mapOf(
        "PLACE_THIRD_NODE" to placeNode(ArmSubsystem.setTargetPosition(Arm.Positions.THIRD_NODE)),
        "PICKUP" to pickupItem
    )

    val autoBuilder = SwerveAutoBuilder(
        { SwerveSubsystem.pose },
        { SwerveSubsystem.resetOdometry(it) },
        KINEMATICS,
        AUTO_TRANSLATIONAL_PID.toPIDConstants(),
        AUTO_ROTATIONAL_PID.toPIDConstants(),
        { SwerveSubsystem.moduleStates = it },
        EVENT_MAP,
        true,
        SwerveSubsystem,
    )

}
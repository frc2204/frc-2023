package org.crosspointacademy.frc.commands

import com.pathplanner.lib.auto.SwerveAutoBuilder
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.WaitCommand
import org.crosspointacademy.frc.commands.Autos.AutoEvents.PLACE_THIRD_NODE
import org.crosspointacademy.frc.commands.arm.PositionArm
import org.crosspointacademy.frc.commands.claw.CloseClaw
import org.crosspointacademy.frc.commands.claw.OpenClaw
import org.crosspointacademy.frc.config.Arm
import org.crosspointacademy.frc.config.Swerve.AUTO_ROTATIONAL_PID
import org.crosspointacademy.frc.config.Swerve.AUTO_TRANSLATIONAL_PID
import org.crosspointacademy.frc.config.Swerve.KINEMATICS
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
    }

    object AutoEvents {

        val PLACE_THIRD_NODE = SequentialCommandGroup(
            CloseClaw(),
            PositionArm(Arm.Positions.THIRD_NODE, 4.6, 4.6).withTimeout(4.0),
            WaitCommand(2.0),
            OpenClaw(),
            WaitCommand(1.0),
            PositionArm(Arm.Positions.HOME, 1.4, 1.4),
            CloseClaw(),
        )

    }

    private val EVENT_MAP = mapOf<String, Command>(
        "PLACE_THIRD_NODE" to PLACE_THIRD_NODE,
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
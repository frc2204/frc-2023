package org.crosspointacademy.frc.commands

import com.pathplanner.lib.auto.SwerveAutoBuilder
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import org.crosspointacademy.frc.config.Swerve.AUTO_ROTATIONAL_PID
import org.crosspointacademy.frc.config.Swerve.AUTO_TRANSLATIONAL_PID
import org.crosspointacademy.frc.config.Swerve.KINEMATICS
import org.crosspointacademy.frc.subsystems.SwerveSubsystem

object Autos {


    private val autoModeChooser = SendableChooser<AutoMode>().apply {
        AutoMode.values().forEach { addOption(it.optionName, it) }
        setDefaultOption(AutoMode.DEFAULT.optionName, AutoMode.DEFAULT)
    }

    init {
        SmartDashboard.putData("Auto Mode", autoModeChooser)
    }

    enum class AutoMode(val optionName: String, val pathName: String) {
        DEFAULT("Default", "Default Path"),
    }

    private val EVENT_MAP = emptyMap<String, Command>()

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
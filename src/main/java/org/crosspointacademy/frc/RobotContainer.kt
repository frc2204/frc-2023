package org.crosspointacademy.frc

import com.pathplanner.lib.PathConstraints
import com.pathplanner.lib.PathPlanner
import com.pathplanner.lib.server.PathPlannerServer
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.Trigger
import org.crosspointacademy.frc.commands.Autos
import org.crosspointacademy.frc.commands.SwerveTeleop
import org.crosspointacademy.frc.config.Swerve.AUTO_MAX_ACCELERATION
import org.crosspointacademy.frc.config.Swerve.AUTO_MAX_VELOCITY
import org.crosspointacademy.frc.config.Swerve.DRIVE_POWER
import org.crosspointacademy.frc.config.Swerve.ROTATIONAL_POWER
import org.crosspointacademy.frc.subsystems.SwerveSubsystem

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 *
 * In Kotlin, it is recommended that all your Subsystems are Kotlin objects. As such, there
 * can only ever be a single instance. This eliminates the need to create reference variables
 * to the various subsystems in this container to pass into to commands. The commands can just
 * directly reference the (single instance of the) object.
 */
object RobotContainer {

    private val xboxController = XboxController(0)

    init {

        configureBindings()
        Autos // Reference the Autos object so that it is initialized, placing the chooser on the dashboard

        SwerveSubsystem.defaultCommand = SwerveTeleop(
            { xboxController.leftY * DRIVE_POWER },
            { xboxController.leftX * DRIVE_POWER },
            { xboxController.rightX * ROTATIONAL_POWER },
            { xboxController.bButtonPressed }
        )

        PathPlannerServer.startServer(5811)
    }

    /** Use this method to define your `trigger->command` mappings. */
    private fun configureBindings() {
        Trigger { xboxController.aButtonPressed }.onTrue(SwerveSubsystem.zeroGyro())
    }

    fun getAutonomousCommand(): Command? {
        return Autos.autoBuilder.fullAuto(
            PathPlanner.loadPathGroup(
                Autos.autoModeChooser.selected.pathName,
                PathConstraints(AUTO_MAX_VELOCITY, AUTO_MAX_ACCELERATION)
            )
        )
    }

}
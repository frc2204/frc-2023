package org.crosspointacademy.frc

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.Trigger
import org.crosspointacademy.frc.commands.SwerveTeleop
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

        SwerveSubsystem.defaultCommand = SwerveTeleop(
            { xboxController.leftY },
            { xboxController.leftX },
            { xboxController.rightX },
            { xboxController.bButtonPressed }
        )
    }

    /** Use this method to define your `trigger->command` mappings. */
    private fun configureBindings() {
        Trigger { xboxController.aButtonPressed }.onTrue(SwerveSubsystem.zeroGyro())
    }

    fun getAutonomousCommand(): Command? {
        // TODO: Implement properly
        return null
    }

}
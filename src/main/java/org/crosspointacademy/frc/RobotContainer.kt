package org.crosspointacademy.frc

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.button.Trigger
import org.crosspointacademy.frc.commands.Autos
import org.crosspointacademy.frc.commands.SwerveTeleop
import org.crosspointacademy.frc.commands.arm.HomeArms
import org.crosspointacademy.frc.commands.arm.PositionArm
import org.crosspointacademy.frc.commands.claw.CloseClaw
import org.crosspointacademy.frc.commands.claw.OpenClaw
import org.crosspointacademy.frc.config.Arm
import org.crosspointacademy.frc.config.Swerve.DRIVE_POWER
import org.crosspointacademy.frc.config.Swerve.ROTATIONAL_POWER
import org.crosspointacademy.frc.subsystems.ArmSubsystem
import org.crosspointacademy.frc.subsystems.ArmSubsystem.setTargetPosition
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

    private val primaryJoystick = Joystick(0)
    private val secondaryJoystick = Joystick(1)
    private val xboxController = XboxController(2)

    init {

        configureBindings()
        Autos // Reference the Autos object so that it is initialized, placing the chooser on the dashboard

        SwerveSubsystem.defaultCommand = SwerveTeleop(
            { primaryJoystick.y * DRIVE_POWER },
            { primaryJoystick.x * DRIVE_POWER },
            { secondaryJoystick.x * ROTATIONAL_POWER },
            { xboxController.bButtonPressed }
        )

        ArmSubsystem.defaultCommand = PositionArm()
    }

    /** Use this method to define your `trigger->command` mappings. */
    private fun configureBindings() {
        Trigger { secondaryJoystick.trigger }.onTrue(SwerveSubsystem.zeroGyro())

        // Arm Control
        Trigger { xboxController.backButtonPressed }.onTrue(setTargetPosition(null))
        Trigger { xboxController.xButtonPressed }.onTrue(setTargetPosition(Arm.Positions.HOME))
        Trigger { xboxController.yButtonPressed }.onTrue(setTargetPosition(Arm.Positions.FIRST_NODE))
        Trigger { xboxController.bButtonPressed }.onTrue(setTargetPosition(Arm.Positions.SECOND_NODE))
        Trigger { xboxController.aButtonPressed }.onTrue(setTargetPosition(Arm.Positions.THIRD_NODE))

        // Claw Control
        Trigger { xboxController.leftBumper }.onTrue(CloseClaw())
        Trigger { xboxController.rightBumper }.onTrue(OpenClaw())
    }

    fun getAutonomousCommand(): Command {
        return SequentialCommandGroup(
            HomeArms(),
//            Autos.autoBuilder.fullAuto(
//                PathPlanner.loadPathGroup(
//                    Autos.autoModeChooser.selected.pathName,
//                    PathConstraints(AUTO_MAX_VELOCITY, AUTO_MAX_ACCELERATION)
//                )
//            )
        )
    }

}
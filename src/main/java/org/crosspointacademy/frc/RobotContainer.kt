package org.crosspointacademy.frc

import com.pathplanner.lib.server.PathPlannerServer
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.button.Trigger
import org.crosspointacademy.frc.commands.Autos
import org.crosspointacademy.frc.commands.Autos.AutoEvents.PLACE_THIRD_NODE
import org.crosspointacademy.frc.commands.SwerveTeleop
import org.crosspointacademy.frc.commands.arm.TeleopPositionArm
import org.crosspointacademy.frc.commands.arm.offset.AddOffsetJointOne
import org.crosspointacademy.frc.commands.arm.offset.AddOffsetJointTwo
import org.crosspointacademy.frc.commands.arm.offset.SubtractOffsetJointOne
import org.crosspointacademy.frc.commands.arm.offset.SubtractOffsetJointTwo
import org.crosspointacademy.frc.commands.claw.CloseClaw
import org.crosspointacademy.frc.commands.claw.OpenClaw
import org.crosspointacademy.frc.config.Arm
import org.crosspointacademy.frc.config.Swerve.DRIVE_BABY_POWER
import org.crosspointacademy.frc.config.Swerve.DRIVE_POWER
import org.crosspointacademy.frc.config.Swerve.ROTATIONAL_POWER
import org.crosspointacademy.frc.subsystems.ArmSubsystem
import org.crosspointacademy.frc.subsystems.ArmSubsystem.nextTargetPosition
import org.crosspointacademy.frc.subsystems.ArmSubsystem.previousTargetPosition
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
            { primaryJoystick.y * DRIVE_POWER  + xboxController.getRawAxis(1) * DRIVE_BABY_POWER},
            { primaryJoystick.x * DRIVE_POWER  + xboxController.getRawAxis(0) * DRIVE_BABY_POWER},
            { secondaryJoystick.x * ROTATIONAL_POWER },
            { false }
        )

        ArmSubsystem.defaultCommand = TeleopPositionArm()

        PathPlannerServer.startServer(5811)
    }

    /** Use this method to define your `trigger->command` mappings. */
    private fun configureBindings() {
        Trigger { secondaryJoystick.trigger }.onTrue(SwerveSubsystem.zeroGyro())

        // Arm Control
//        Trigger { xboxController.startButtonPressed }.onTrue(
//            SequentialCommandGroup(
//                ResetOffsets(),
//                ArmSubsystem.secondJoint.home(),
//                setTargetPosition(Arm.Positions.HOME),
//            )
//        )
//        Trigger { xboxController.backButtonPressed }.onTrue(setTargetPosition(null))
        Trigger { xboxController.yButtonPressed }.onTrue(nextTargetPosition())
        Trigger { xboxController.bButtonPressed }.onTrue(previousTargetPosition())
        Trigger { xboxController.xButtonPressed }.onTrue(setTargetPosition(Arm.Positions.FLOOR))
        Trigger { xboxController.aButtonPressed }.onTrue(setTargetPosition(Arm.Positions.HOME))

        Trigger { xboxController.pov == 0 }.onTrue(AddOffsetJointOne())
        Trigger { xboxController.pov == 90 }.onTrue(AddOffsetJointTwo())
        Trigger { xboxController.pov == 180 }.onTrue(SubtractOffsetJointOne())
        Trigger { xboxController.pov == 270 }.onTrue(SubtractOffsetJointTwo())

        // Claw Control
        Trigger { xboxController.leftBumper }.onTrue(OpenClaw())
        Trigger { xboxController.rightBumper }.onTrue(CloseClaw())
    }

    fun getAutonomousCommand(): Command {
        return SequentialCommandGroup(
            PLACE_THIRD_NODE
//            Autos.autoBuilder.fullAuto(
//                PathPlanner.loadPathGroup(
//                    Autos.autoModeChooser.selected.pathName,
//                    PathConstraints(AUTO_MAX_VELOCITY, AUTO_MAX_ACCELERATION)
//                )
//            )
        )
    }

}
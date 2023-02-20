package org.crosspointacademy.frc.subsystems

import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.geometry.Twist2d
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.math.kinematics.SwerveDriveKinematics
import edu.wpi.first.math.kinematics.SwerveDriveOdometry
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.smartdashboard.Field2d
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.crosspointacademy.frc.Robot
import org.crosspointacademy.frc.config.Swerve.INVERTED_GYRO
import org.crosspointacademy.frc.config.Swerve.KINEMATICS
import org.crosspointacademy.frc.config.Swerve.MAX_SPEED
import org.crosspointacademy.lib.swerve.SwerveModule
import org.crosspointacademy.lib.swerve.SwerveModuleConfiguration

object SwerveSubsystem : SubsystemBase() {

    private val navX = AHRS(SPI.Port.kMXP)
    private val modules = arrayOf(
        SwerveModule(SwerveModuleConfiguration.FRONT_LEFT),
        SwerveModule(SwerveModuleConfiguration.FRONT_RIGHT),
        SwerveModule(SwerveModuleConfiguration.BACK_LEFT),
        SwerveModule(SwerveModuleConfiguration.BACK_RIGHT)
    )
    private val field = Field2d()

    private val navXModulo360 get() = navX.angle % 360
    val yaw get() = Rotation2d.fromDegrees(if (INVERTED_GYRO) 360 - navXModulo360 else navXModulo360)

    private var actualOdometry: SwerveDriveOdometry

    val pose: Pose2d get() = actualOdometry.poseMeters

    var moduleStates
        get() = modules.map { it.state }.toTypedArray()
        set(value) {
            SwerveDriveKinematics.desaturateWheelSpeeds(value, MAX_SPEED)
            modules.forEachIndexed { index, module -> module.setDesiredState(value[index], false)}
        }
    private val modulePositions get() = modules.map { it.position }.toTypedArray()

    init {

        // Avoids a bug with inverted motors
        Timer.delay(1.0)
        resetModulesToAbsolute()

        actualOdometry = SwerveDriveOdometry(KINEMATICS, yaw, modulePositions)
        SmartDashboard.putData("Field", field)
    }

    fun drive(translation: Translation2d, rotation: Double, fieldOriented: Boolean, openLoop: Boolean) {

        val moduleStates = KINEMATICS.toSwerveModuleStates(
            if (fieldOriented) ChassisSpeeds.fromFieldRelativeSpeeds(translation.x, translation.y, rotation, yaw)
            else ChassisSpeeds(translation.x, translation.y, rotation)
        )

        SwerveDriveKinematics.desaturateWheelSpeeds(moduleStates, MAX_SPEED)
        modules.forEachIndexed { index, module -> module.setDesiredState(moduleStates[index], openLoop) }
    }

    fun zeroGyro(): CommandBase = runOnce {
        navX.zeroYaw()
    }

    fun resetOdometry() {
        actualOdometry.resetPosition(yaw, modulePositions, pose)
    }

    private fun resetModulesToAbsolute() {
        modules.forEach { it.resetToAbsolutePosition() }
    }

    override fun periodic() {
        actualOdometry.update(yaw, modulePositions)
        field.robotPose = pose

        modules.forEach {
            SmartDashboard.putNumber("${it.configuration.name} CANCoder", it.canCoder.degrees)
            SmartDashboard.putNumber("${it.configuration.name} Integrated", it.position.angle.degrees)
            SmartDashboard.putNumber("${it.configuration.name} Velocity", it.state.speedMetersPerSecond)
        }
    }

}
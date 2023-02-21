package org.crosspointacademy.frc.subsystems

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.math.kinematics.SwerveDriveKinematics
import edu.wpi.first.math.kinematics.SwerveDriveOdometry
import edu.wpi.first.wpilibj.ADIS16470_IMU
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.smartdashboard.Field2d
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.crosspointacademy.frc.config.Swerve.INVERTED_GYRO
import org.crosspointacademy.frc.config.Swerve.KINEMATICS
import org.crosspointacademy.frc.config.Swerve.MAX_SPEED
import org.crosspointacademy.frc.config.SwerveModuleConfigurations
import org.crosspointacademy.lib.swerve.SwerveModule

object SwerveSubsystem : SubsystemBase() {

    private val adis16470Imu = ADIS16470_IMU()
    private val modules = arrayOf(
        SwerveModule(SwerveModuleConfigurations.FRONT_LEFT),
        SwerveModule(SwerveModuleConfigurations.FRONT_RIGHT),
        SwerveModule(SwerveModuleConfigurations.BACK_LEFT),
        SwerveModule(SwerveModuleConfigurations.BACK_RIGHT)
    )
    private val field = Field2d()

    private val gyroAngle get() = adis16470Imu.angle % 360
    val yaw: Rotation2d get() = Rotation2d.fromDegrees(if (INVERTED_GYRO) 360 - gyroAngle else gyroAngle)

    private var actualOdometry: SwerveDriveOdometry

    val pose: Pose2d get() = actualOdometry.poseMeters

    var moduleStates
        get() = modules.map { it.state }.toTypedArray()
        set(value) {
            SwerveDriveKinematics.desaturateWheelSpeeds(value, MAX_SPEED)
            modules.forEachIndexed { index, module -> module.setDesiredState(value[index], false) }
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
        adis16470Imu.reset()
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

        SmartDashboard.putBoolean("Gyro Connected", adis16470Imu.isConnected)
        SmartDashboard.putNumber("Gyro", gyroAngle)

        modules.forEach {
            SmartDashboard.putNumber("${it.configuration.name} CANCoder", it.canCoder.degrees)
            SmartDashboard.putNumber("${it.configuration.name} Integrated", it.position.angle.degrees)
            SmartDashboard.putNumber("${it.configuration.name} Velocity", it.state.speedMetersPerSecond)
        }
    }

}
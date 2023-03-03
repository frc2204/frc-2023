package org.crosspointacademy.frc.subsystems

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.math.kinematics.SwerveDriveKinematics
import edu.wpi.first.wpilibj.ADIS16470_IMU
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.simulation.ADIS16470_IMUSim
import edu.wpi.first.wpilibj.smartdashboard.Field2d
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.crosspointacademy.frc.Robot
import org.crosspointacademy.frc.config.Limelight.FIXED_LIMELIGHT_NAME
import org.crosspointacademy.frc.config.Swerve.INVERTED_GYRO
import org.crosspointacademy.frc.config.Swerve.KINEMATICS
import org.crosspointacademy.frc.config.Swerve.MAX_SPEED
import org.crosspointacademy.frc.config.SwerveModuleConfigurations
import org.crosspointacademy.lib.limelight.LimelightHelpers
import org.crosspointacademy.lib.swerve.SwerveModule

object SwerveSubsystem : SubsystemBase() {

    private val adis16470Imu = ADIS16470_IMU()
    private val adis16470ImuSim = ADIS16470_IMUSim(adis16470Imu)
    private val modules = arrayOf(
        SwerveModule(SwerveModuleConfigurations.FRONT_LEFT),
        SwerveModule(SwerveModuleConfigurations.FRONT_RIGHT),
        SwerveModule(SwerveModuleConfigurations.BACK_LEFT),
        SwerveModule(SwerveModuleConfigurations.BACK_RIGHT)
    )

    private val field = Field2d()

    private val gyroAngle get() = adis16470Imu.angle % 360
    private val yaw: Rotation2d get() = Rotation2d.fromDegrees(if (INVERTED_GYRO) 360 - gyroAngle else gyroAngle)

    private var poseEstimator: SwerveDrivePoseEstimator

    val pose: Pose2d get() = poseEstimator.estimatedPosition

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

        poseEstimator = SwerveDrivePoseEstimator(KINEMATICS, yaw, modulePositions, Pose2d(0.0, 0.0, Rotation2d()))
        SmartDashboard.putData("Field", field)
    }

    fun drive(translation: Translation2d, rotation: Double, fieldOriented: Boolean, openLoop: Boolean) {

        val chassisSpeeds = if (fieldOriented) {
            ChassisSpeeds.fromFieldRelativeSpeeds(translation.x, translation.y, rotation, yaw)
        } else ChassisSpeeds(translation.x, translation.y, rotation)

        val moduleStates = KINEMATICS.toSwerveModuleStates(chassisSpeeds)

        SwerveDriveKinematics.desaturateWheelSpeeds(moduleStates, MAX_SPEED)
        modules.forEachIndexed { index, module -> module.setDesiredState(moduleStates[index], openLoop) }

        if (Robot.simulation) {
            adis16470ImuSim.setGyroAngleZ(gyroAngle + chassisSpeeds.omegaRadiansPerSecond * 0.02 * 180 / Math.PI)
        }
    }
    
    fun zeroGyro(): CommandBase = runOnce {
        adis16470Imu.reset()
    }

    fun resetOdometry(pose: Pose2d) {
        poseEstimator.resetPosition(yaw, modulePositions, pose)
    }

    private fun resetModulesToAbsolute() {
        modules.forEach { it.resetToAbsolutePosition() }
    }

    override fun periodic() {
        poseEstimator.update(yaw, modulePositions)
        if (LimelightHelpers.getTV(FIXED_LIMELIGHT_NAME)) poseEstimator.addVisionMeasurement(
            LimelightHelpers.getBotPose2d(FIXED_LIMELIGHT_NAME),
            LimelightHelpers.getVisionTimestamp(FIXED_LIMELIGHT_NAME),
        )

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
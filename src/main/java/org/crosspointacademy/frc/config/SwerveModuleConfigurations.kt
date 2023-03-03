package org.crosspointacademy.frc.config

import edu.wpi.first.math.geometry.Rotation2d
import org.crosspointacademy.frc.Robot

enum class SwerveModuleConfigurations(
    val driveMotorCANId: Int,
    val steerMotorCANId: Int,
    val steerEncoderCANId: Int,
    val steerEncoderOffset: Rotation2d,
) {

    FRONT_LEFT(1, 2, 3, Rotation2d.fromDegrees(if (Robot.real) 307.8 else 0.0)),
    FRONT_RIGHT(4, 5, 6, Rotation2d.fromDegrees(if (Robot.real) 350.59 else 0.0)),
    BACK_LEFT(7, 8, 9, Rotation2d.fromDegrees(if (Robot.real) 25.04 else 0.0)),
    BACK_RIGHT(10, 11, 12, Rotation2d.fromDegrees(if (Robot.real) 323.34 else 0.0)),

}
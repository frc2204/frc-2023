package org.crosspointacademy.lib.swerve

import edu.wpi.first.math.geometry.Rotation2d

enum class SwerveModuleConfiguration(
    val driveMotorCANId: Int,
    val steerMotorCANId: Int,
    val steerEncoderCANId: Int,
    val steerEncoderOffset: Rotation2d,
) {

    FRONT_LEFT(1, 2, 3, Rotation2d.fromDegrees(0.0)),
    FRONT_RIGHT(4, 5, 6, Rotation2d.fromDegrees(0.0)),
    BACK_LEFT(7, 8, 9, Rotation2d.fromDegrees(0.0)),
    BACK_RIGHT(10, 11, 12, Rotation2d.fromDegrees(0.0)),

}
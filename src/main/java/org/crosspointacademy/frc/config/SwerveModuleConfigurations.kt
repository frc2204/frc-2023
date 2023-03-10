package org.crosspointacademy.frc.config

import edu.wpi.first.math.geometry.Rotation2d
import org.crosspointacademy.lib.degrees
import org.crosspointacademy.lib.swerve.SwerveModuleConfiguration

enum class SwerveModuleConfigurations(
    override val driveMotorCANId: Int,
    override val steerMotorCANId: Int,
    override val steerEncoderCANId: Int,
    override val steerEncoderOffset: Rotation2d,
    override val simulationEncoderOffset: Rotation2d
) : SwerveModuleConfiguration {

    FRONT_LEFT(1, 2, 3, 307.8.degrees ,0.0.degrees),
    FRONT_RIGHT(4, 5, 6, 350.59.degrees, 0.0.degrees),
    BACK_LEFT(7, 8, 9, 25.04.degrees, 0.0.degrees),
    BACK_RIGHT(10, 11, 12, 323.34.degrees, 0.0.degrees),

}
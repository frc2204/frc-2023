package org.crosspointacademy.frc.lib

import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.SwerveModuleState


/**
 * Returns the speed of the module in meters per second.
 */
operator fun SwerveModuleState.component1() = speedMetersPerSecond

/**
 * Returns the angle of the module as [Rotation2d]
 */
operator fun SwerveModuleState.component2(): Rotation2d = angle
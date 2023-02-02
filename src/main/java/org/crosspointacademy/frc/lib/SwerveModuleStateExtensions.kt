package org.crosspointacademy.frc.lib

import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.SwerveModuleState

operator fun SwerveModuleState.component1() = speedMetersPerSecond
operator fun SwerveModuleState.component2(): Rotation2d = angle
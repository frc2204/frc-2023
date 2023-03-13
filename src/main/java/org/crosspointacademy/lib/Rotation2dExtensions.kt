package org.crosspointacademy.lib

import edu.wpi.first.math.geometry.Rotation2d

val Double.degrees: Rotation2d get() = Rotation2d.fromDegrees(this)

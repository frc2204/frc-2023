package org.crosspointacademy.lib

import edu.wpi.first.math.geometry.Rotation2d

val Double.degrees get() = Rotation2d.fromDegrees(this)

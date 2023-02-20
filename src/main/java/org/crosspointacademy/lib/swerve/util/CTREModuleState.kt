package org.crosspointacademy.lib.swerve.util

import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.SwerveModuleState
import kotlin.math.abs


object CTREModuleState {

    /**
     * Minimize the change in heading the desired swerve module state would require by potentially
     * reversing the direction the wheel spins. Customized from WPILib's version to include placing
     * in appropriate scope for CTRE onboard control.
     *
     * @param desiredState The desired state.
     * @param currentAngle The current module angle.
     */
    fun optimize(desiredState: SwerveModuleState, currentAngle: Rotation2d): SwerveModuleState {
        var targetAngle = placeInAppropriate0To360Scope(currentAngle.degrees, desiredState.angle.degrees)
        var targetSpeed = desiredState.speedMetersPerSecond
        val delta = targetAngle - currentAngle.degrees
        if (abs(delta) > 90) {
            targetSpeed = -targetSpeed
            targetAngle =
                if (delta > 90) 180.let { targetAngle -= it; targetAngle } else 180.let { targetAngle += it; targetAngle }
        }
        return SwerveModuleState(targetSpeed, Rotation2d.fromDegrees(targetAngle))
    }

    /**
     * @param scopeReference Current Angle
     * @param newAngle Target Angle
     * @return Closest angle within scope
     */
    private fun placeInAppropriate0To360Scope(scopeReference: Double, newAngle: Double): Double {
        var returnedAngle = newAngle
        val lowerBound: Double
        val upperBound: Double
        val lowerOffset = scopeReference % 360
        if (lowerOffset >= 0) {
            lowerBound = scopeReference - lowerOffset
            upperBound = scopeReference + (360 - lowerOffset)
        } else {
            upperBound = scopeReference - lowerOffset
            lowerBound = scopeReference - (360 + lowerOffset)
        }
        while (returnedAngle < lowerBound) {
            returnedAngle += 360.0
        }
        while (returnedAngle > upperBound) {
            returnedAngle -= 360.0
        }
        if (returnedAngle - scopeReference > 180) {
            returnedAngle -= 360.0
        } else if (returnedAngle - scopeReference < -180) {
            returnedAngle += 360.0
        }
        return returnedAngle
    }



}

package org.crosspointacademy.lib.swerve.util

object Conversions {
    /**
     * @param positionCounts CANCoder Position Counts
     * @param gearRatio Gear Ratio between CANCoder and Mechanism
     * @return Degrees of Rotation of Mechanism
     */
    fun canCoderToDegrees(positionCounts: Double, gearRatio: Double): Double {
        return positionCounts * (360.0 / (gearRatio * 4096.0))
    }

    /**
     * @param degrees Degrees of rotation of Mechanism
     * @param gearRatio Gear Ratio between CANCoder and Mechanism
     * @return CANCoder Position Counts
     */
    fun degreesToCANcoder(degrees: Double, gearRatio: Double): Double {
        return degrees / (360.0 / (gearRatio * 4096.0))
    }

    /**
     * @param counts Falcon Position Counts
     * @param gearRatio Gear Ratio between Falcon and Mechanism
     * @return Degrees of Rotation of Mechanism
     */
    fun falconToDegrees(positionCounts: Double, gearRatio: Double): Double {
        return positionCounts * (360.0 / (gearRatio * 2048.0))
    }

    /**
     * @param degrees Degrees of rotation of Mechanism
     * @param gearRatio Gear Ratio between Falcon and Mechanism
     * @return Falcon Position Counts
     */
    fun degreesToFalcon(degrees: Double, gearRatio: Double): Double {
        return degrees / (360.0 / (gearRatio * 2048.0))
    }

    /**
     * @param velocityCounts Falcon Velocity Counts
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return RPM of Mechanism
     */
    fun falconToRPM(velocityCounts: Double, gearRatio: Double): Double {
        val motorRPM = velocityCounts * (600.0 / 2048.0)
        return motorRPM / gearRatio
    }

    /**
     * @param rpm RPM of mechanism
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return RPM of Mechanism
     */
    fun rpmToFalcon(rpm: Double, gearRatio: Double): Double {
        val motorRPM = rpm * gearRatio
        return motorRPM * (2048.0 / 600.0)
    }

    /**
     * @param velocityCounts Falcon Velocity Counts
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon MPS)
     * @return Falcon Velocity Counts
     */
    fun falconToMPS(
        velocityCounts: Double,
        circumference: Double,
        gearRatio: Double
    ): Double {
        val wheelRPM = falconToRPM(velocityCounts, gearRatio)
        return wheelRPM * circumference / 60
    }

    /**
     * @param velocity Velocity MPS
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon MPS)
     * @return Falcon Velocity Counts
     */
    fun mpsToFalcon(velocity: Double, circumference: Double, gearRatio: Double): Double {
        val wheelRPM = velocity * 60 / circumference
        return rpmToFalcon(wheelRPM, gearRatio)
    }

    /**
     * @param positionCounts Falcon Position Counts
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Falcon and Wheel
     * @return Meters
     */
    fun falconToMeters(positionCounts: Double, circumference: Double, gearRatio: Double): Double {
        return positionCounts * (circumference / (gearRatio * 2048.0))
    }

    /**
     * @param meters Meters
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Falcon and Wheel
     * @return Falcon Position Counts
     */
    fun metersToFalcon(meters: Double, circumference: Double, gearRatio: Double): Double {
        return meters / (circumference / (gearRatio * 2048.0))
    }
}
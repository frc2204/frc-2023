package org.crosspointacademy.lib.limelight.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Detector(

    /**
     * Human-readable class name string
     */
    @SerialName("class") val detectedClass: String,

    /**
     * ClassID integer
     */
    @SerialName("classID") val detectedClassId: Int,

    /**
     * Confidence of the prediction (0-1)
     */
    @SerialName("conf") val confidence: Double,

    /**
     * Individual corner points as an array of {x,y} in pixels. Center-zero, positive right and down. Must be enabled.
     */
    @SerialName("pts") val points: List<Pair<Double, Double>>,

    /**
     * The size of the target as a percentage of the image (0-1)
     */
    @SerialName("ta") val area: Double,

    /**
     * X-coordinate of the center of the target in degrees. Positive-right, center-zero
     */
    @SerialName("tx") val x: Double,

    /**
     * X-coordinate of the center of the target in pixels. Positive-right, center-zero
     */
    @SerialName("txp") val xPixel: Double,

    /**
     * Y-coordinate of the center of the target in degrees. Positive-down, center-zero
     */
    @SerialName("ty") val y: Double,

    /**
     * Y-coordinate of the center of the target in pixels. Positive-down, center-zero
     */
    @SerialName("typ") val yPixel: Double,
)

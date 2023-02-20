package org.crosspointacademy.lib.limelight.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Classifier(
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
)
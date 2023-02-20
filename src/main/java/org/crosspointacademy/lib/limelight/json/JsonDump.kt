package org.crosspointacademy.lib.limelight.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This class is used to deserialize the JSON dump from the Limelight.
 */
@Serializable
data class JsonDump(
    /**
     * The actual results of the JSON dump. This is the only field that is actually used.
     */
    @SerialName("Results") val results: Results
)

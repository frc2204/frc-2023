package org.crosspointacademy.frc.lib.limelight.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class JsonDump(
    @SerialName("Results") val results: Results
)

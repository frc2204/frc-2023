package org.crosspointacademy.frc.lib.limelight.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.crosspointacademy.frc.lib.serialization.DoubleMillisecondSeserializer
import org.crosspointacademy.frc.lib.serialization.ValidityIndicatorSerializer
import kotlin.time.Duration

@Serializable
data class Results(
    @SerialName("Classifier") val classifiers: List<Classifier>,
    @SerialName("Detector") val detectors: List<Detector>,
    @SerialName("Fiducial") val fiducials: List<Fiducial>,
    @SerialName("Retro") val retros: List<Retro>,

    @SerialName("pID")
    /**
     * Current pipeline index
     */
    val pipelineId: Int,

    @Serializable(with = DoubleMillisecondSeserializer::class)
    @SerialName("tl")
    /**
     * Targeting latency
     */
    val latency: Duration,

    @Serializable(with = DoubleMillisecondSeserializer::class)
    @SerialName("ts")
    /**
     * Time since boot
     */
    val sinceBoot: Duration,

    @Serializable(with = ValidityIndicatorSerializer::class)
    @SerialName("v")
    /**
     * Whether the limelight has any valid targets
     */
    val valid: Boolean,
)
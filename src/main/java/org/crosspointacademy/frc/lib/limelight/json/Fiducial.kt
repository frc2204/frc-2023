package org.crosspointacademy.frc.lib.limelight.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Fiducial(
    /**
     * Fiducial tag ID
     */
    @SerialName("fID") val id: Int,

    /**
     * Fiducial Family (16H5C, 25H9C, 36H11C, etc)
     */
    @SerialName("fam") val family: String,

    /**
     * Return individual corner points. Must be enabled.
     */
    @SerialName("pts") val points: List<Pair<Double, Double>>,

    @Deprecated("Not implemented in Limelight")
    val skew: List<String> = listOf(),

    /**
     * Camera Pose in target space as computed by this fiducial (x,y,z,rx,ry,rz)
     */
    @SerialName("t6c_ts") val cameraPoseTargetSpace: List<Double>,

    /**
     * Robot Pose in field space as computed by this fiducial (x,y,z,rx,ry,rz)
     */
    @SerialName("t6r_fs") val robotPoseFieldSpace: List<Double>,

    /**
     * Robot Pose in target space as computed by this fiducial (x,y,z,rx,ry,rz)
     */
    @SerialName("t6r_ts") val robotPoseTargetSpace: List<Double>,

    /**
     * Target Pose in camera space as computed by this fiducial (x,y,z,rx,ry,rz)
     */
    @SerialName("t6t_cs") val targetPoseCameraSpace: List<Double>,

    /**
     * Target Pose in robot space as computed by this fiducial (x,y,z,rx,ry,rz)
     */
    @SerialName("t6t_rs") val targetPoseRobotSpace: List<Double>,

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

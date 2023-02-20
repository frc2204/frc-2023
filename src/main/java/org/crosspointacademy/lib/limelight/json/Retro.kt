package org.crosspointacademy.lib.limelight.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Retro(
    /**
     * Individual corner points as an array of {x,y} in pixels. Center-zero, positive right and down. Must be enabled.
     */
    @SerialName("pts") val points: List<Pair<Double, Double>>,

    /**
     * Camera Pose in target space as computed by SolvePnP (x,y,z,rx,ry,rz)
     */
    @SerialName("t6c_ts") val cameraPoseTargetSpace: List<Double>,

    /**
     * Robot Pose in field space as computed by SolvePnP (x,y,z,rx,ry,rz)
     */
    @SerialName("t6r_fs") val robotPoseFieldSpace: List<Double>,

    /**
     * Robot Pose in target space as computed by SolvePnP (x,y,z,rx,ry,rz)
     */
    @SerialName("t6r_ts") val robotPoseTargetSpace: List<Double>,

    /**
     * Target Pose in camera space as computed by SolvePnP (x,y,z,rx,ry,rz)
     */
    @SerialName("t6t_cs") val targetPoseCameraSpace: List<Double>,

    /**
     * Target Pose in robot space as computed by SolvePnP (x,y,z,rx,ry,rz)
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

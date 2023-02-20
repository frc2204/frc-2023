package org.crosspointacademy.lib.limelight

import edu.wpi.first.networktables.NetworkTable
import edu.wpi.first.networktables.NetworkTableInstance
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.crosspointacademy.lib.limelight.json.JsonDump

class Limelight(private val hostname: String) {

    private val table: NetworkTable get() = NetworkTableInstance.getDefault().getTable(hostname)

    private lateinit var jsonDump: JsonDump

    internal fun refresh(json: String) {
        jsonDump = Json.decodeFromString(json)
    }

    fun refresh() {
        refresh(table.getEntry("json").getString("{}"))
    }

    val classifiers get() = jsonDump.results.classifiers
    val detectors get() = jsonDump.results.detectors
    val fiducials get() = jsonDump.results.fiducials
    val retros get() = jsonDump.results.retros

    /**
     * Current pipeline index
     */
    val pipelineId get() = jsonDump.results.pipelineId

    /**
     * Targeting latency
     */
    val latency get() = jsonDump.results.latency

    /**
     * Timestamp in milliseconds from boot
     */
    val sinceBoot get() = jsonDump.results.sinceBoot

    /**
     * Validity indicator
     * Indicates whether the limelight has any valid targets
     */
    val valid get() = jsonDump.results.valid

    /**
     * Side length of the longest side of the fitted bounding box (pixels)
     */
    val long get() = table.getEntry("tlong").getDouble(-1.0)

    /**
     * Side length of the shortest side of the fitted bounding box (pixels)
     */
    val short get() = table.getEntry("tshort").getDouble(-1.0)

    /**
     * Horizontal side-length of the rough bounding box (0 - 320 pixels)
     */

    val horizontal get() = table.getEntry("thor").getDouble(-1.0)

    /**
     * Vertical side-length of the rough bounding box (0 - 320 pixels)
     */
    val vertical get() = table.getEntry("tvert").getDouble(-1.0)

}
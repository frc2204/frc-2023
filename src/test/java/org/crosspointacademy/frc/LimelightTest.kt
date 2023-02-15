package org.crosspointacademy.frc

import org.crosspointacademy.frc.lib.limelight.Limelight
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import kotlin.time.Duration.Companion.milliseconds

class LimelightTest {

    private val limelight = Limelight("Limelight-Test")

    private fun getJSON(fileName: String): String {
        return Paths.get("src", "test", "resources", "limelightTest", "$fileName.json").toFile().readText()
    }

    @Test
    fun `Empty Pipeline Test`() {
        limelight.refresh(getJSON("EmptyPipeline"))

        // Check that all the lists are empty
        assertEquals(0, limelight.classifiers.size, "Classifier Size")
        assertEquals(0, limelight.detectors.size, "Detector Size")
        assertEquals(0, limelight.fiducials.size, "Fiducial Size")
        assertEquals(0, limelight.retros.size, "Retro Size")

        // Check that the other values are correct
        assertEquals(0, limelight.pipelineId, "Pipeline ID")
        assertEquals(1.milliseconds, limelight.latency, "Latency")
        assertEquals(2.milliseconds, limelight.sinceBoot, "Since Boot")
        assertEquals(false, limelight.valid, "Valid")

    }

    @Test
    fun `Classifier Pipeline Test`() {
        limelight.refresh(getJSON("ClassifierPipeline"))

        val classifier = limelight.classifiers[0]

        // Check classifier results
        assertEquals(1, limelight.classifiers.size, "Classifier Size")

        assertEquals("digital clock", classifier.detectedClass, "Class Name")
        assertEquals(531, classifier.detectedClassId, "Class ID")

    }

    @Test
    fun `Detector Pipeline Test`() {
        limelight.refresh(getJSON("DetectorPipeline"))

        val detector = limelight.detectors[0]

        // Check detector results
        assertEquals(1, limelight.detectors.size, "Detector Size")

        assertEquals("person", detector.detectedClass, "Class Name")
        assertEquals(0, detector.detectedClassId, "Class ID")
        assertEquals(0.8, detector.confidence, "Class Confidence")
        assertEquals(emptyList<Pair<Double, Double>>(), detector.points, "Points")
        assertEquals(0.25, detector.area, "Area")
        assertEquals(-2.4, detector.x, "X")
        assertEquals(147.5, detector.xPixel, "X Pixel")
        assertEquals(-10.0, detector.y, "Y")
        assertEquals(165.5, detector.yPixel, "Y Pixel")

    }

    @Test
    fun `Fiducial Pipeline Test`() {

        limelight.refresh(getJSON("FiducialPipeline"))

        val fiducial = limelight.fiducials[0]

        assertEquals(1, limelight.fiducials.size, "Fiducial Size")

        // Check fiducial results
        assertEquals(2, fiducial.id, "Id")
        assertEquals("16H5C", fiducial.family, "Family")
        assertEquals(emptyList<Pair<Double, Double>>(), fiducial.points, "Points")
        assertEquals(listOf(0.33, -0.06, -2.5, -4.68, -5.17, 4.53), fiducial.cameraPoseTargetSpace, "t6c_ts")
        assertEquals(listOf(4.74, -1.59, 0.52, 4.52, 4.26, 5.52), fiducial.robotPoseFieldSpace, "t6r_fs")
        assertEquals(listOf(0.33, -0.06, -2.5, -4.68, -5.17, 4.53), fiducial.robotPoseTargetSpace, "t6r_ts")
        assertEquals(listOf(-0.1, -0.12, 2.52, 4.28, 5.51, -4.11), fiducial.targetPoseCameraSpace, "t6t_cs")
        assertEquals(listOf(-0.1, -0.12, 2.52, 4.28, 5.51, -4.11), fiducial.targetPoseRobotSpace, "t6t_rs")
        assertEquals(0.01, fiducial.area, "Area")
        assertEquals(-2.05, fiducial.x, "X")
        assertEquals(149.49, fiducial.xPixel, "X Pixel")
        assertEquals(2.73, fiducial.y, "Y")
        assertEquals(107.15, fiducial.yPixel, "Y Pixel")

    }

    @Test
    fun `Retro Pipeline Test`() {
        limelight.refresh(getJSON("RetroPipeline"))

        val retro = limelight.retros[0]

        assertEquals(1, limelight.retros.size, "Retro Size")

        // Check retro results
        assertEquals(emptyList<Pair<Double, Double>>(), retro.points, "Points")


    }

}
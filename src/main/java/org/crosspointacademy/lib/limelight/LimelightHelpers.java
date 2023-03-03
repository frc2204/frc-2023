package org.crosspointacademy.lib.limelight;

/*
LimelightHelpers v1.2.1 (March 1, 2023).
https://github.com/LimelightVision/limelightlib-wpijava/blob/main/LimelightHelpers.java
 */

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wpi.first.wpilibj.Timer;

public class LimelightHelpers {

    public static class LimelightTargetRetro {

        @JsonProperty("t6cts")
        private double[] cameraPoseTargetSpace;

        @JsonProperty("t6rfs")
        private double[] robotPoseFieldSpace;

        @JsonProperty("t6rts")
        private  double[] robotPoseTargetSpace;

        @JsonProperty("t6tcs")
        private double[] targetPoseCameraSpace;

        @JsonProperty("t6trs")
        private double[] targetPoseRobotSpace;

        public Pose3d getCameraPoseTargetSpace()
        {
            return toPose3D(cameraPoseTargetSpace);
        }
        public Pose3d getRobotPoseFieldSpace()
        {
            return toPose3D(robotPoseFieldSpace);
        }
        public Pose3d getRobotPoseTargetSpace()
        {
            return toPose3D(robotPoseTargetSpace);
        }
        public Pose3d getTargetPoseCameraSpace()
        {
            return toPose3D(targetPoseCameraSpace);
        }
        public Pose3d getTargetPoseRobotSpace()
        {
            return toPose3D(targetPoseRobotSpace);
        }

        public Pose2d getCameraPoseTargetSpace2D()
        {
            return toPose2D(cameraPoseTargetSpace);
        }
        public Pose2d getRobotPoseFieldSpace2D()
        {
            return toPose2D(robotPoseFieldSpace);
        }
        public Pose2d getRobotPoseTargetSpace2D()
        {
            return toPose2D(robotPoseTargetSpace);
        }
        public Pose2d getTargetPoseCameraSpace2D()
        {
            return toPose2D(targetPoseCameraSpace);
        }
        public Pose2d getTargetPoseRobotSpace2D()
        {
            return toPose2D(targetPoseRobotSpace);
        }

        @JsonProperty("ta")
        public double ta;

        @JsonProperty("tx")
        public double tx;

        @JsonProperty("txp")
        public double txpixels;

        @JsonProperty("ty")
        public double ty;

        @JsonProperty("typ")
        public double typixels;

        @JsonProperty("ts")
        public double ts;

        public LimelightTargetRetro() {
            cameraPoseTargetSpace = new double[6];
            robotPoseFieldSpace = new double[6];
            robotPoseTargetSpace = new double[6];
            targetPoseCameraSpace = new double[6];
            targetPoseRobotSpace = new double[6];
        }

    }

    public static class LimelightTargetFiducial {

        @JsonProperty("fID")
        public double fiducialID;

        @JsonProperty("fam")
        public String fiducialFamily;

        @JsonProperty("t6cts")
        private double[] cameraPoseTargetSpace;

        @JsonProperty("t6rfs")
        private double[] robotPoseFieldSpace;

        @JsonProperty("t6rts")
        private double[] robotPoseTargetSpace;

        @JsonProperty("t6tcs")
        private double[] targetPoseCameraSpace;

        @JsonProperty("t6trs")
        private double[] targetPoseRobotSpace;

        public Pose3d getCameraPoseTargetSpace()
        {
            return toPose3D(cameraPoseTargetSpace);
        }
        public Pose3d getRobotPoseFieldSpace()
        {
            return toPose3D(robotPoseFieldSpace);
        }
        public Pose3d getRobotPoseTargetSpace()
        {
            return toPose3D(robotPoseTargetSpace);
        }
        public Pose3d getTargetPoseCameraSpace()
        {
            return toPose3D(targetPoseCameraSpace);
        }
        public Pose3d getTargetPoseRobotSpace()
        {
            return toPose3D(targetPoseRobotSpace);
        }

        public Pose2d getCameraPoseTargetSpace2D()
        {
            return toPose2D(cameraPoseTargetSpace);
        }
        public Pose2d getRobotPoseFieldSpace2D()
        {
            return toPose2D(robotPoseFieldSpace);
        }
        public Pose2d getRobotPoseTargetSpace2D()
        {
            return toPose2D(robotPoseTargetSpace);
        }
        public Pose2d getTargetPoseCameraSpace2D()
        {
            return toPose2D(targetPoseCameraSpace);
        }
        public Pose2d getTargetPoseRobotSpace2D()
        {
            return toPose2D(targetPoseRobotSpace);
        }

        @JsonProperty("ta")
        public double ta;

        @JsonProperty("tx")
        public double tx;

        @JsonProperty("txp")
        public double txpixels;

        @JsonProperty("ty")
        public double ty;

        @JsonProperty("typ")
        public double typixels;

        @JsonProperty("ts")
        public double ts;

        public LimelightTargetFiducial() {
            cameraPoseTargetSpace = new double[6];
            robotPoseFieldSpace = new double[6];
            robotPoseTargetSpace = new double[6];
            targetPoseCameraSpace = new double[6];
            targetPoseRobotSpace = new double[6];
        }
    }

    public static class LimelightTargetBarcode {

    }

    public static class LimelightTargetClassifier {

        @JsonProperty("class")
        public String className;

        @JsonProperty("classID")
        public double classID;

        @JsonProperty("conf")
        public double confidence;

        @JsonProperty("zone")
        public double zone;

        @JsonProperty("tx")
        public double tx;

        @JsonProperty("txp")
        public double txpixels;

        @JsonProperty("ty")
        public double ty;

        @JsonProperty("typ")
        public double typixels;

        public  LimelightTargetClassifier() {
        }
    }

    public static class LimelightTargetDetector {

        @JsonProperty("class")
        public String className;

        @JsonProperty("classID")
        public double classID;

        @JsonProperty("conf")
        public double confidence;

        @JsonProperty("ta")
        public double ta;

        @JsonProperty("tx")
        public double tx;

        @JsonProperty("txp")
        public double txpixels;

        @JsonProperty("ty")
        public double ty;

        @JsonProperty("typ")
        public double typixels;

        public LimelightTargetDetector() {
        }
    }

    public static class Results {

        @JsonProperty("pID")
        public double pipelineID;

        @JsonProperty("tl")
        public double latencypipeline;

        @JsonProperty("cl")
        public double latencycapture;

        public double latencyjsonParse;

        @JsonProperty("ts")
        public double timestampLIMELIGHTpublish;

        @JsonProperty("tsrio")
        public double timestampRIOFPGAcapture;

        @JsonProperty("v")
        @JsonFormat(shape = Shape.NUMBER)
        public boolean valid;

        @JsonProperty("botpose")
        public double[] botpose;

        @JsonProperty("botposewpired")
        public double[] botposewpired;

        @JsonProperty("botposewpiblue")
        public double[] botposewpiblue;

        @JsonProperty("t6crs")
        public double[] cameraposerobotspace;

        public Pose3d getBotPose3d() {
            return toPose3D(botpose);
        }

        public Pose3d getBotPose3dwpiRed() {
            return toPose3D(botposewpired);
        }

        public Pose3d getBotPose3dwpiBlue() {
            return toPose3D(botposewpiblue);
        }

        public Pose2d getBotPose2d() {
            return toPose2D(botpose);
        }

        public Pose2d getBotPose2dwpiRed() {
            return toPose2D(botposewpired);
        }

        public Pose2d getBotPose2dwpiBlue() {
            return toPose2D(botposewpiblue);
        }

        @JsonProperty("Retro")
        public LimelightTargetRetro[] targetsRetro;

        @JsonProperty("Fiducial")
        public LimelightTargetFiducial[] targetsFiducials;

        @JsonProperty("Classifier")
        public LimelightTargetClassifier[] targetsClassifier;

        @JsonProperty("Detector")
        public LimelightTargetDetector[] targetsDetector;

        @JsonProperty("Barcode")
        public LimelightTargetBarcode[] targetsBarcode;

        public Results() {
            botpose = new double[6];
            botposewpired = new double[6];
            botposewpiblue = new double[6];
            cameraposerobotspace = new double[6];
            targetsRetro = new LimelightTargetRetro[0];
            targetsFiducials = new LimelightTargetFiducial[0];
            targetsClassifier = new LimelightTargetClassifier[0];
            targetsDetector = new LimelightTargetDetector[0];
            targetsBarcode = new LimelightTargetBarcode[0];
        }
    }

    public static class LimelightResults {
        @JsonProperty("Results")
        public Results targetingResults;

        public LimelightResults() {
            targetingResults = new Results();
        }
    }

    private static ObjectMapper mapper;

    /**
     * Print JSON Parse time to the console in milliseconds
     */
    static boolean profileJSON = false;

    static String sanitizeName(String name) {
        if (name == "" || name == null) {
            return "limelight";
        }
        return name;
    }

    private static Pose3d toPose3D(double[] inData){
        if(inData.length < 6)
        {
            System.err.println("Bad LL 3D Pose Data!");
            return new Pose3d();
        }
        return new Pose3d(
                new Translation3d(inData[0], inData[1], inData[2]),
                new Rotation3d(Units.degreesToRadians(inData[3]), Units.degreesToRadians(inData[4]),
                        Units.degreesToRadians(inData[5])));
    }

    private static Pose2d toPose2D(double[] inData){
        if(inData.length < 6)
        {
            System.err.println("Bad LL 2D Pose Data!");
            return new Pose2d();
        }
        Translation2d tran2d = new Translation2d(inData[0], inData[1]);
        Rotation2d r2d = new Rotation2d(Units.degreesToRadians(inData[5]));
        return new Pose2d(tran2d, r2d);
    }

    public static NetworkTable getLimelightNTTable(String tableName) {
        return NetworkTableInstance.getDefault().getTable(sanitizeName(tableName));
    }

    public static NetworkTableEntry getLimelightNTTableEntry(String tableName, String entryName) {
        return getLimelightNTTable(tableName).getEntry(entryName);
    }

    public static double getLimelightNTDouble(String tableName, String entryName) {
        return getLimelightNTTableEntry(tableName, entryName).getDouble(0.0);
    }

    public static void setLimelightNTDouble(String tableName, String entryName, double val) {
        getLimelightNTTableEntry(tableName, entryName).setDouble(val);
    }

    public static void setLimelightNTDoubleArray(String tableName, String entryName, double[] val) {
        getLimelightNTTableEntry(tableName, entryName).setDoubleArray(val);
    }

    public static double[] getLimelightNTDoubleArray(String tableName, String entryName) {
        return getLimelightNTTableEntry(tableName, entryName).getDoubleArray(new double[0]);
    }

    public static String getLimelightNTString(String tableName, String entryName) {
        return getLimelightNTTableEntry(tableName, entryName).getString("");
    }

    public static URL getLimelightURLString(String tableName, String request) {
        String urlString = "http://" + sanitizeName(tableName) + ".local:5807/" + request;
        URL url;
        try {
            url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            System.err.println("bad LL URL");
        }
        return null;
    }
    /////
    /////

    public static double getTX(String limelightName) {
        return getLimelightNTDouble(limelightName, "tx");
    }

    public static double getTY(String limelightName) {
        return getLimelightNTDouble(limelightName, "ty");
    }

    public static double getTA(String limelightName) {
        return getLimelightNTDouble(limelightName, "ta");
    }

    public static double getLatencyPipeline(String limelightName) {
        return getLimelightNTDouble(limelightName, "tl");
    }

    public static double getLatencyCapture(String limelightName) {
        return getLimelightNTDouble(limelightName, "cl");
    }

    public static double getCurrentPipelineIndex(String limelightName) {
        return getLimelightNTDouble(limelightName, "getpipe");
    }

    public static String getJSONDump(String limelightName) {
        return getLimelightNTString(limelightName, "json");
    }

    /**
     * Switch to getBotPose
     *
     * @param limelightName
     * @return
     */
    @Deprecated
    public static double[] getBotpose(String limelightName) {
        return getLimelightNTDoubleArray(limelightName, "botpose");
    }

    /**
     * Switch to getBotPosewpiRed
     *
     * @param limelightName
     * @return
     */
    @Deprecated
    public static double[] getBotposewpiRed(String limelightName) {
        return getLimelightNTDoubleArray(limelightName, "botposewpired");
    }

    /**
     * Switch to getBotPosewpiBlue
     *
     * @param limelightName
     * @return
     */
    @Deprecated
    public static double[] getBotposewpiBlue(String limelightName) {
        return getLimelightNTDoubleArray(limelightName, "botposewpiblue");
    }

    public static double[] getBotPose(String limelightName) {
        return getLimelightNTDoubleArray(limelightName, "botpose");
    }

    public static double[] getBotPosewpiRed(String limelightName) {
        return getLimelightNTDoubleArray(limelightName, "botposewpired");
    }

    public static double[] getBotPosewpiBlue(String limelightName) {
        return getLimelightNTDoubleArray(limelightName, "botposewpiblue");
    }

    public static double[] getBotPoseTargetSpace(String limelightName) {
        return getLimelightNTDoubleArray(limelightName, "botposetargetspace");
    }

    public static double[] getCameraPoseTargetSpace(String limelightName) {
        return getLimelightNTDoubleArray(limelightName, "cameraposetargetspace");
    }

    public static double[] getTargetPoseCameraSpace(String limelightName) {
        return getLimelightNTDoubleArray(limelightName, "targetposecameraspace");
    }

    public static double[] getTargetPoseRobotSpace(String limelightName) {
        return getLimelightNTDoubleArray(limelightName, "targetposerobotspace");
    }

    public static double[] getTargetColor(String limelightName) {
        return getLimelightNTDoubleArray(limelightName, "tc");
    }

    public static double getFiducialID(String limelightName) {
        return getLimelightNTDouble(limelightName, "tid");
    }

    public static double getNeuralClassID(String limelightName) {
        return getLimelightNTDouble(limelightName, "tclass");
    }

    /////
    /////

    public static Pose3d getBotPose3d(String limelightName) {
        double[] poseArray = getLimelightNTDoubleArray(limelightName, "botpose");
        return toPose3D(poseArray);
    }

    public static Pose3d getBotPose3dwpiRed(String limelightName) {
        double[] poseArray = getLimelightNTDoubleArray(limelightName, "botposewpired");
        return toPose3D(poseArray);
    }

    public static Pose3d getBotPose3dwpiBlue(String limelightName) {
        double[] poseArray = getLimelightNTDoubleArray(limelightName, "botposewpiblue");
        return toPose3D(poseArray);
    }

    public static Pose3d getBotPose3dTargetSpace(String limelightName) {
        double[] poseArray = getLimelightNTDoubleArray(limelightName, "botposetargetspace");
        return toPose3D(poseArray);
    }

    public static Pose3d getCameraPose3dTargetSpace(String limelightName) {
        double[] poseArray = getLimelightNTDoubleArray(limelightName, "cameraposetargetspace");
        return toPose3D(poseArray);
    }

    public static Pose3d getTargetPose3dCameraSpace(String limelightName) {
        double[] poseArray = getLimelightNTDoubleArray(limelightName, "targetposecameraspace");
        return toPose3D(poseArray);
    }

    public static Pose3d getTargetPose3dRobotSpace(String limelightName) {
        double[] poseArray = getLimelightNTDoubleArray(limelightName, "targetposerobotspace");
        return toPose3D(poseArray);
    }

    public static Pose3d getCameraPose3dRobotSpace(String limelightName) {
        double[] poseArray = getLimelightNTDoubleArray(limelightName, "cameraposerobotspace");
        return toPose3D(poseArray);
    }

    /**
     * Gets the Pose2d for easy use with Odometry vision pose estimator
     * (addVisionMeasurement)
     *
     * @param limelightName
     * @return
     */
    public static Pose2d getBotPose2dwpiBlue(String limelightName) {

        double[] result = getBotPosewpiBlue(limelightName);
        return toPose2D(result);
    }

    /**
     * Gets the Pose2d for easy use with Odometry vision pose estimator
     * (addVisionMeasurement)
     *
     * @param limelightName
     * @return
     */
    public static Pose2d getBotPose2dwpiRed(String limelightName) {

        double[] result = getBotPosewpiRed(limelightName);
        return toPose2D(result);

    }

    /**
     * Gets the Pose2d for easy use with Odometry vision pose estimator
     * (addVisionMeasurement)
     *
     * @param limelightName
     * @return
     */
    public static Pose2d getBotPose2d(String limelightName) {

        double[] result = getBotPose(limelightName);
        return toPose2D(result);

    }

    public static boolean getTV(String limelightName) {
        return 1.0 == getLimelightNTDouble(limelightName, "tv");
    }

    /////
    /////

    public static void setPipelineIndex(String limelightName, int pipelineIndex) {
        setLimelightNTDouble(limelightName, "pipeline", pipelineIndex);
    }

    /**
     * The LEDs will be controlled by Limelight pipeline settings, and not by robot
     * code.
     */
    public static void setLEDModePipelineControl(String limelightName) {
        setLimelightNTDouble(limelightName, "ledMode", 0);
    }

    public static void setLEDModeForceOff(String limelightName) {
        setLimelightNTDouble(limelightName, "ledMode", 1);
    }

    public static void setLEDModeForceBlink(String limelightName) {
        setLimelightNTDouble(limelightName, "ledMode", 2);
    }

    public static void setLEDModeForceOn(String limelightName) {
        setLimelightNTDouble(limelightName, "ledMode", 3);
    }

    public static void setStreamModeStandard(String limelightName) {
        setLimelightNTDouble(limelightName, "stream", 0);
    }

    public static void setStreamModePiPMain(String limelightName) {
        setLimelightNTDouble(limelightName, "stream", 1);
    }

    public static void setStreamModePiPSecondary(String limelightName) {
        setLimelightNTDouble(limelightName, "stream", 2);
    }

    public static void setCameraModeProcessor(String limelightName) {
        setLimelightNTDouble(limelightName, "camMode", 0);
    }
    public static void setCameraModeDriver(String limelightName) {
        setLimelightNTDouble(limelightName, "camMode", 1);
    }


    /**
     * Sets the crop window. The crop window in the UI must be completely open for
     * dynamic cropping to work.
     */
    public static void setCropWindow(String limelightName, double cropXMin, double cropXMax, double cropYMin, double cropYMax) {
        double[] entries = new double[4];
        entries[0] = cropXMin;
        entries[1] = cropXMax;
        entries[2] = cropYMin;
        entries[3] = cropYMax;
        setLimelightNTDoubleArray(limelightName, "crop", entries);
    }

    public static void setCameraPoseRobotSpace(String limelightName, double forward, double side, double up, double roll, double pitch, double yaw) {
        double[] entries = new double[6];
        entries[0] = forward;
        entries[1] = side;
        entries[2] = up;
        entries[3] = roll;
        entries[4] = pitch;
        entries[5] = yaw;
        setLimelightNTDoubleArray(limelightName, "cameraposerobotspaceset", entries);
    }

    /////
    /////

    public static void setPythonScriptData(String limelightName, double[] outgoingPythonData) {
        setLimelightNTDoubleArray(limelightName, "llrobot", outgoingPythonData);
    }

    public static double[] getPythonScriptData(String limelightName) {
        return getLimelightNTDoubleArray(limelightName, "llpython");
    }

    /////
    /////

    /**
     * Asynchronously take snapshot.
     */
    public static CompletableFuture<Boolean> takeSnapshot(String tableName, String snapshotName) {
        return CompletableFuture.supplyAsync(() -> SYNCHTAKESNAPSHOT(tableName, snapshotName));
    }

    private static boolean SYNCHTAKESNAPSHOT(String tableName, String snapshotName) {
        URL url = getLimelightURLString(tableName, "capturesnapshot");
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (snapshotName != null && snapshotName != "") {
                connection.setRequestProperty("snapname", snapshotName);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return true;
            } else {
                System.err.println("Bad LL Request");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Parses Limelight's JSON results dump into a LimelightResults Object
     */
    public static LimelightResults getLatestResults(String limelightName) {
        long start = System.nanoTime();
        LimelightHelpers.LimelightResults results = new LimelightHelpers.LimelightResults();
        if (mapper == null) {
            mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        try {
            results = mapper.readValue(getJSONDump(limelightName), LimelightResults.class);
        } catch (JsonProcessingException e) {
            System.err.println("lljson error: " + e.getMessage());
        }

        long end = System.nanoTime();
        double millis = (end - start) * .000001;
        results.targetingResults.latencyjsonParse = millis;
        if (profileJSON) {
            System.out.printf("lljson: %.2f\r\n", millis);
        }

        return results;
    }

    public static double getTotalLatency(String limelightName) {
        return getLatencyCapture(limelightName) / 1000 + getLatencyPipeline(limelightName) / 1000;
    }

    public static double getVisionTimestamp(String limeLightName) {
        double timestamp = Timer.getFPGATimestamp() - getTotalLatency(limeLightName);
        return Math.max(timestamp, 0); // Prevent negative timestamps
    }

}

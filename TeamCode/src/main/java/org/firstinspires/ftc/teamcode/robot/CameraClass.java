package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class CameraClass {
// https://github.com/IFC-Robotics/Center-Stage_2023-2024/blob/main/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/robot/CameraClass.java
// external/samples/RobotAutoDriveToAprilTagOmni.java
    LinearOpMode opMode;
    Telemetry telemetry;
    public WebcamName webcam;
    public final String name;
    public VisionPortal visionPortal;
    public AprilTagProcessor aprilTagProcessor;
    private AprilTagDetection detectedTag;
    public int desiredTagId = -1;
    private boolean aprilTagFound = false;
    public CameraClass(String name) {
        this.name = name;
    }
    public void init(LinearOpMode opModeParam) {
        opMode = opModeParam;
        telemetry = opMode.telemetry;
        webcam = opMode.hardwareMap.get(WebcamName.class, this.name);
        visionPortal = new VisionPortal.Builder()
                .setCamera(webcam)
                .addProcessors()
                .build();
        aprilTagProcessor = new AprilTagProcessor.Builder().build();
        aprilTagProcessor.setDecimation(2);
    }
    public AprilTagDetection detectAprilTags() {
        List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addLine("Found apriltag with ID " + detection.id);
                if ((desiredTagId < 0) || (detection.id == desiredTagId)) {
                    aprilTagFound = true;
                    return detection;
                }
                aprilTagFound = false;
            }
        }
        return null;
    }

}
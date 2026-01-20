package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Autonomous(name="Go to Apriltag")
public class GotoApriltag extends LinearOpMode {
    AprilTagDetection apriltag=null;
    @Override
    public void runOpMode() {
        Robot.init(this,false,true);
        setManualExposure(6, 250);
        waitForStart();
        while(opModeIsActive()){
            AprilTagDetection detectedAprilTag = Robot.aprilTagWebcam.detectAprilTags();
            if(Objects.nonNull(detectedAprilTag)) {
                if (Arrays.asList(new Integer[]{21, 22, 23}).contains(detectedAprilTag.id)) {
                    apriltag = detectedAprilTag;
                    Robot.aprilTagWebcam.desiredTagId = detectedAprilTag.id;
                    telemetry.addData("Found", "ID %d (%s)", apriltag.id, apriltag.metadata.name);
                    telemetry.addData("Range",  "%5.1f inches", apriltag.ftcPose.range);
                    telemetry.addData("Bearing","%3.0f degrees", apriltag.ftcPose.bearing);
                    telemetry.addData("Yaw","%3.0f degrees", apriltag.ftcPose.yaw);
                }
            }
            if(apriltag!=null) {
                double DESIRED_DISTANCE = 10;
                double rangeError = (apriltag.ftcPose.range - DESIRED_DISTANCE);
                double headingError = apriltag.ftcPose.bearing;
                double yawError = apriltag.ftcPose.yaw;
                double xError = apriltag.ftcPose.x;
                double SPEED_GAIN = 0.02;
                double STRAFE_GAIN = 0.015;
                double TURN_GAIN = 0.01;
                double MAX_AUTO_SPEED = 0.5;
                double MAX_AUTO_STRAFE = 0.5;
                double MAX_AUTO_TURN  = 0.3;
                double drive = Range.clip(rangeError * SPEED_GAIN, -MAX_AUTO_SPEED, MAX_AUTO_SPEED);
                double strafe = Range.clip(-xError * STRAFE_GAIN, -MAX_AUTO_STRAFE, MAX_AUTO_STRAFE);
                double turn = Range.clip(headingError * TURN_GAIN, -MAX_AUTO_TURN, MAX_AUTO_TURN);
                Robot.drivetrain.teleOp(drive,strafe,turn,false);
            }
        }
    }
    private void  setManualExposure(int exposureMS, int gain) {
        // Wait for the camera to be open, then use the controls

        if (Robot.aprilTagWebcam.visionPortal == null) {
            return;
        }Robot.aprilTagWebcam.visionPortal.getCameraState();
        // Make sure camera is streaming before we try to set the exposure controls
        if (Robot.aprilTagWebcam.visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            telemetry.addData("Camera", "Waiting");
            telemetry.update();
            while (!isStopRequested() && (Robot.aprilTagWebcam.visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                sleep(20);
            }
            telemetry.addData("Camera", "Ready");
            telemetry.update();
        }

        // Set camera controls unless we are stopping.
        if (!isStopRequested())
        {
            ExposureControl exposureControl = Robot.aprilTagWebcam.visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
                sleep(50);
            }
            exposureControl.setExposure(exposureMS, TimeUnit.MILLISECONDS);
            sleep(20);
            GainControl gainControl = Robot.aprilTagWebcam.visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            sleep(20);
        }
    }
}

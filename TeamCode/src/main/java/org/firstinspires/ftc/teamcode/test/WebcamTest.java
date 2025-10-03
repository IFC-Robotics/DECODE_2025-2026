package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.CameraClass;

@TeleOp(name="Webcam Test")
public class WebcamTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        telemetry.addLine("Initializing OpMode...");
        telemetry.update();

        CameraClass aprilTagWebcam = new CameraClass("webcam");
        aprilTagWebcam.init(this);
        waitForStart();
        telemetry.addLine("Starting OpMode...");
        telemetry.update();
        while(opModeIsActive()){
            aprilTagWebcam.detectAprilTags();
        }
    }
}

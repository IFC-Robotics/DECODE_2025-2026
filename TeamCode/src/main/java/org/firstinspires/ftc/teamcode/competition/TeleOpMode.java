package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name="TeleOp")
public class TeleOpMode extends LinearOpMode {
    @Override
    public void runOpMode() {
        telemetry.addLine("Initializing OpMode...");
        telemetry.update();

        Robot.init(this,false);
        waitForStart();
        telemetry.addLine("Starting OpMode...");
        while(opModeIsActive()){
            Robot.drivetrain.teleOp(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x,gamepad1.x||gamepad1.y);
            Robot.motorIntake.motorCurrentSpeed = (gamepad1.right_bumper?1:0);
            Robot.motorLaunchL.motorCurrentSpeed = (gamepad1.x?0.7:0);
            Robot.motorLaunchR.motorCurrentSpeed = (gamepad1.x?0.7:0);
            Robot.aprilTagWebcam.detectAprilTags();
        }
    }
}

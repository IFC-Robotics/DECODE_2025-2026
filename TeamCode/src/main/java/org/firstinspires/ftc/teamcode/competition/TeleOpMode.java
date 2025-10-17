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
            Robot.drivetrain.teleOp(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x,gamepad1.left_bumper);
            Robot.motorIntake.teleOpBool(gamepad1.b);
            Robot.motorLaunchL.teleOpBool(gamepad1.right_bumper);
            Robot.motorLaunchR.teleOpBool(gamepad1.right_bumper);
            Robot.motorConveyor.teleOpBool(gamepad1.y);
            //Robot.aprilTagWebcam.detectAprilTags();
        }
    }
}

package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.Robot;
@Autonomous(name="LaunchAutonDebug", group="test")
public class LaunchAutonDebugging extends LinearOpMode {
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(this, false,false);
        waitForStart();

        Robot.drivetrain.drive(10, 0.5);
        Robot.drivetrain.turn(150, 0.5);
        Robot.drivetrain.strafe(8, 0.5);
        Robot.drivetrain.drive(24, 0.5, true);
        Robot.motorIntake.runToPosition(1000, true);
        sleep(1000);
        Robot.motorConveyor.runToPosition(1000, true, 1);
        sleep(500);
        Robot.drivetrain.drive(-12, 0.5);
        Robot.drivetrain.strafe(-8, 0.5);
        Robot.drivetrain.turn(-150, -0.5);
        Robot.drivetrain.drive(-10, 0.5);

        Robot.motorLaunchR.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.motorLaunchR.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Robot.motorLaunchR.motor.setTargetPosition(3000);
        Robot.motorLaunchR.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.motorLaunchR.motor.setPower(0.375);

        Robot.motorLaunchL.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.motorLaunchL.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Robot.motorLaunchL.motor.setTargetPosition(3000);
        Robot.motorLaunchL.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.motorLaunchL.motor.setPower(0.375);

        sleep(2000);
        Robot.servoLauncher.runToPosition("down");
        sleep(1000);

//        while (opModeIsActive()) {
//            if (timer.seconds() < 15) {
//                Robot.motorLaunchR.setConstVelocity(400);
//            } else {
//                Robot.motorLaunchR.stopMotor();
//            }
//            telemetry.addData("Launch Motor Position", Robot.motorLaunchR.motor.getCurrentPosition());
//            telemetry.addData("Timer: ", timer.seconds());
//            telemetry.update();
//        }
//
//        Robot.drivetrain.drive(-10, 0.5, true);
//        sleep(1000);
//        Robot.drivetrain.turn(90, 0.5, true);
    }
}
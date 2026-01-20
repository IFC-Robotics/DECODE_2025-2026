package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="LaunchAutonBlue", group="Competition")
public class LaunchAuton extends LinearOpMode {
    int targetVelocity = 900;
    int target = targetVelocity * 11;

    @Override
    public void runOpMode() {
        Robot.init(this, false,false);
        waitForStart();
        Robot.drivetrain.drive(34, -0.5);
//        Robot.drivetrain.moveDrivetrain(1650, 1650, 1650, 1650, -0.5, -0.5, -0.5, -0.5, false);

//        Robot.motorLaunchR.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Robot.motorLaunchR.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        Robot.motorLaunchR.motor.setTargetPosition(target);
//        Robot.motorLaunchR.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        Robot.motorLaunchR.motor.setPower(0.375);
//
//        Robot.motorLaunchL.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Robot.motorLaunchL.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        Robot.motorLaunchL.motor.setTargetPosition(target);
//        Robot.motorLaunchL.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        Robot.motorLaunchL.motor.setPower(0.375);

        Robot.motorLaunchL.setConstVelocity(targetVelocity, target);

        Robot.servoLauncher.runToPosition("up");
        sleep(3000);
        Robot.servoLauncher.runToPosition("down");
        sleep(1000);
        Robot.servoLauncher.runToPosition("up");
        sleep(1500);
//        Robot.motorConveyor.motor.setTargetPosition(1500);
//        Robot.motorConveyor.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Robot.motorConveyor.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        Robot.motorConveyor.motor.setPower(1);
        Robot.motorConveyor.runToPosition(1500, true, 1);
        sleep(1000);

        Robot.servoLauncher.runToPosition("down");
        sleep(1000);
        Robot.servoLauncher.runToPosition("up");
        sleep(500);

//        Robot.motorIntake.motor.setTargetPosition(1500);
//        Robot.motorIntake.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Robot.motorIntake.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        Robot.motorIntake.motor.setPower(1);
        Robot.motorIntake.runToPosition(1500, true, 1);

        sleep(1000);
//        Robot.motorConveyor.motor.setTargetPosition(2500);
//        Robot.motorConveyor.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Robot.motorConveyor.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        Robot.motorConveyor.motor.setPower(1);
        Robot.motorConveyor.runToPosition(2500, true, 1);
        sleep(1500);
        Robot.servoLauncher.runToPosition("down");
        sleep(1000);
//        Robot.drivetrain.moveDrivetrain(1000, 1000, 1000, 1000, -0.5, -0.5, -0.5, -0.5, true);

        Robot.drivetrain.turn(135, 0.5);
        Robot.drivetrain.drive(45, 0.5, true);
        Robot.motorIntake.runToPosition(1500, true);
//        Robot.drivetrain.moveDrivetrain(1500, -1500, -1500, 1500, -0.5, 0.5, 0.5, -0.5, true);

        while (opModeIsActive() && Robot.motorLaunchR.motor.isBusy()) {
            telemetry.addData("Launch Motor Position", Robot.motorLaunchR.motor.getCurrentPosition());
            telemetry.addData("Target", target);
            telemetry.update();
        }

        // Optional: Stop the motor after reaching position
        Robot.motorLaunchR.motor.setPower(0);
    }

    }
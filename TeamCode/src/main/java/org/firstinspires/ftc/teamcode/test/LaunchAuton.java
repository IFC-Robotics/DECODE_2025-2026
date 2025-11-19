package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.MotorClass;
import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="LaunchAuton", group="Competition")
public class LaunchAuton extends LinearOpMode {
    @Override
    public void runOpMode() {
        Robot.init(this, false,false);
        waitForStart();
        Robot.drivetrain.moveDrivetrain(1650, 1650, 1650, 1650, -0.5, -0.5, -0.5, -0.5, true);
//        Robot.drivetrain.drive(-34,0.5);
//        Robot.drivetrain.turn(180,0.5);
        int target = 25000;

        Robot.motorConveyor.motor.setTargetPosition(25000);
        Robot.motorConveyor.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.motorConveyor.motor.setPower(1);

        Robot.motorLaunchR.motor.setTargetPosition(target);
        Robot.motorLaunchR.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.motorLaunchR.motor.setPower(0.35);

        Robot.motorLaunchL.motor.setTargetPosition(target);
        Robot.motorLaunchL.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.motorLaunchL.motor.setPower(0.35);


        while (opModeIsActive() && Robot.motorLaunchR.motor.isBusy()) {
            telemetry.addData("Launch Motor Position", Robot.motorLaunchR.motor.getCurrentPosition());
            telemetry.addData("Target", target);
            telemetry.update();
        }

        // Optional: Stop the motor after reaching position
        Robot.motorLaunchR.motor.setPower(0);
    }

    }
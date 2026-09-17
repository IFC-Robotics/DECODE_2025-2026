package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="LaunchAutonBlue", group="Competition")
public class LaunchAuton extends LinearOpMode {
    int target1 = 9500;
    int target2 = 3000;

    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {
        Robot.init(this, false,false);
        waitForStart();

        Robot.drivetrain.drive(38, 0.5);

        Robot.motorLaunchR.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.motorLaunchR.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Robot.motorLaunchR.motor.setTargetPosition(target1);
        Robot.motorLaunchR.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.motorLaunchR.motor.setPower(0.37);

        Robot.motorLaunchL.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.motorLaunchL.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Robot.motorLaunchL.motor.setTargetPosition(target1);
        Robot.motorLaunchL.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.motorLaunchL.motor.setPower(0.37);

        sleep(1500);
        Robot.servoLauncher.runToPosition("down");
        sleep(1000);
        Robot.servoLauncher.runToPosition("up");
        sleep(500);

        Robot.motorConveyor.runToPosition(1500, true, 1);
        sleep(1250);

        Robot.servoLauncher.runToPosition("down");
        sleep(1000);
        Robot.servoLauncher.runToPosition("up");
        sleep(500);

        Robot.motorIntake.runToPosition(1500, true, 1);
        sleep(1000);

        Robot.motorConveyor.runToPosition(2500, true, 1);
        sleep(1000);

        Robot.servoLauncher.runToPosition("down");
        sleep(1000);
        Robot.servoLauncher.runToPosition("up");

        Robot.drivetrain.drive(10, 0.5);
        Robot.drivetrain.turn(150, 0.5);
        Robot.drivetrain.strafe(5, 0.5);

        Robot.motorIntake.resetMotor();
        Robot.motorConveyor.resetMotor();

        Robot.drivetrain.drive(30, 0.5, true);
        Robot.motorIntake.runToPosition(1000, true);
        sleep(1500);
        Robot.motorConveyor.runToPosition(1000, true, 1);
        sleep(500);
        Robot.drivetrain.resetDrivetrain();
        Robot.drivetrain.drive(-30, 0.5);
        Robot.drivetrain.strafe(-5, 0.5);
        Robot.drivetrain.turn(-150, -0.5);
        Robot.drivetrain.drive(-10, 0.5);

        Robot.motorLaunchR.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.motorLaunchR.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Robot.motorLaunchR.motor.setTargetPosition(target2);
        Robot.motorLaunchR.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.motorLaunchR.motor.setPower(0.375);

        Robot.motorLaunchL.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.motorLaunchL.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Robot.motorLaunchL.motor.setTargetPosition(target2);
        Robot.motorLaunchL.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.motorLaunchL.motor.setPower(0.375);

        sleep(1500);
        Robot.servoLauncher.runToPosition("down");
        sleep(1000);


    }
}
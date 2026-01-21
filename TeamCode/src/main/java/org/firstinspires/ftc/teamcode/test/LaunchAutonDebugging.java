package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.Robot;
@Disabled
@Autonomous(name="LaunchAutonDebug", group="test")
public class LaunchAutonDebugging extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(this, false,false);
        waitForStart();
        Robot.drivetrain.turn(135, 0.5);
        Robot.drivetrain.drive(45, 0.5, true);
        Robot.motorIntake.runToPosition(1500, true);
        Robot.motorConveyor.runToPosition(1500, true, 1);
        sleep(10000);
    }
}
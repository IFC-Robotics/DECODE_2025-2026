package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.MotorClass;
import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="LaunchAuton", group="Competition")
public class LaunchAuton extends LinearOpMode {
    @Override
    public void runOpMode() {

        Robot.init(this, false,false);
        waitForStart();
        Robot.drivetrain.drive(34,0.5);
        Robot.drivetrain.turn(180,0.5);
        Robot.motorConveyor.runToPosition(2500,true, 1.0);
        Robot.motorLaunchL.runToPosition(2500,true, 1.0);
        Robot.motorLaunchR.runToPosition(2500,true, 1.0);
    }
}
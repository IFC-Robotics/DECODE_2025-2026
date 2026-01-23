package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.Robot;
@Autonomous(name="LaunchAutonDebug", group="test")
public class LaunchAutonDebugging extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(this, false,false);
        waitForStart();
        Robot.motorLaunchR.setConstVelocity(400,5);
        while (opModeIsActive() && Robot.motorLaunchR.motor.isBusy()) {
            telemetry.addData("Launch Motor Position", Robot.motorLaunchR.motor.getCurrentPosition());
            telemetry.update();
        }
    }
}
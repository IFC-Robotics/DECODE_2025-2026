package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.Robot;
@Autonomous(name="LaunchAutonDebug", group="test")
public class LaunchAutonDebugging extends LinearOpMode {
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(this, false,false);
        waitForStart();

        Robot.drivetrain.drive(10, 0.5, true);
//        sleep(5000);

        while (opModeIsActive()) {
            if (timer.seconds() < 15) {
                Robot.motorLaunchR.setConstVelocity(400);
            } else {
                Robot.motorLaunchR.stopMotor();
            }
            telemetry.addData("Launch Motor Position", Robot.motorLaunchR.motor.getCurrentPosition());
            telemetry.addData("Timer: ", timer.seconds());
            telemetry.update();
        }
    }
}
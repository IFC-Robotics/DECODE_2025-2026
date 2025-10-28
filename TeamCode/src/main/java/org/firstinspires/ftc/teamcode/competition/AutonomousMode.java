package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.robot.Robot;
@Autonomous(name="LeaveAuton")
public class AutonomousMode extends LinearOpMode{
    public void runOpMode() {
        telemetry.addLine("Initializing Auto...");
        telemetry.update();

        Robot.init(this,false);
        waitForStart();
        telemetry.addLine("Starting Auto...");
            Robot.drivetrain.drive (30, 1.0);
    }
}


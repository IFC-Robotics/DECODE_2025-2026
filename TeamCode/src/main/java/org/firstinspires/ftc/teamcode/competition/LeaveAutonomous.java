package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous(name="Autonomous", group="Competition")
public class LeaveAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() {
        Robot.init(this, false,false);
        waitForStart();
        Robot.drivetrain.drive(10,0.5);

    }
}
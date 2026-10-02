package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.Robot;
@Disabled
@Autonomous(name="Experimental_Autonomous", group="Competition")
public class Expermental_auto extends LinearOpMode {

    @Override
    public void runOpMode() {
        Robot.init(this, false,false);
        waitForStart();

        //launch

        Robot.drivetrain.drive(10,0.5, true);
        Robot.motorOutake.setConstVelocity(1,5);

        //Intake = yes

        //Robot.drivetrain.turn(180,1);
        //Robot.drivetrain.drive(10,0.5);
        //Robot.drivetrain.turn(180,1);
        // Robot.drivetrain.turn(90,1);
        //Robot.drivetrain.drive(10,0.5);
        //Robot.motorIntake .setConstVelocity(1,10);
        //Robot.drivetrain.turn(180,1);
        //Robot.drivetrain.drive(10,0.5);
        //Robot.drivetrain.turn(-90,1);
        //Robot.drivetrain.drive(10,0.5);
        //Robot.motorOutake.setConstVelocity(1,5);



        //park

        Robot.drivetrain.turn(270,1);
        Robot.drivetrain.drive(10,0.5);
        Robot.drivetrain.turn(90,1);
        Robot.drivetrain.drive(10,0.5);

    }
}

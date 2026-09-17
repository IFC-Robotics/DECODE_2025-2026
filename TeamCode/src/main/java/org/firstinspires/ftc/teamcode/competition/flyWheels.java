package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name = "flyWheels")
public class flyWheels extends LinearOpMode {
    public void runOpMode() {
        waitForStart();
        Robot.flywheel1 .teleOpSetVelocity(gamepad1.right_bumper, gamepad1.left_bumper, 100 );
        Robot.flywheel2 .teleOpSetVelocity(gamepad1.right_bumper, gamepad1.left_bumper, 100 );




    }
}

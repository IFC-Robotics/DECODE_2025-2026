package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.MotorClass;
import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name = "1MotorIntake")
public class hi extends LinearOpMode {
    @Override
    public void runOpMode() {
            waitForStart();
        while (opModeIsActive()) {
            Robot.motorIntake.teleOpSetVelocity(gamepad1.right_bumper, gamepad1.left_bumper, 100);
        }
    }
}

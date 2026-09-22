package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.MotorClass;
import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name = "1MotorIntake")
public class hi extends LinearOpMode {
    private DcMotor motorIn;

    @Override
    public void runOpMode() {
        motorIn = hardwareMap.get(DcMotor.class, "Motor_In");
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                motorIn.setPower(1);
            }
            else{
                    motorIn.setPower(0);
                }
            }
        }
    }



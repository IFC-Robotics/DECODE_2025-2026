package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Run Motor", group="example")
public class runMotor extends LinearOpMode{

    DcMotor motor;
    double power;

    @Override
    public void runOpMode() {

        telemetry.addLine("Initializing opMode...");
        telemetry.update();

        motor  = hardwareMap.get(DcMotor.class, "motor1");

        waitForStart();

        while (opModeIsActive()) {
            power = gamepad1.right_stick_y;

            motor.setPower(power);

//            telemetry.addLine(String.format("Motor power: %1$s", power));
//            telemetry.update();
        }
    }
}
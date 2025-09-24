package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name="Test Motors",group="tests")
public class TestMotors extends LinearOpMode {
    public DcMotor motor1;
    public DcMotor motor2;
    public static double power;

    @Override
    public void runOpMode() {
        telemetry.addLine("Initializing");
        telemetry.update();
        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        telemetry.addLine("Initialized, waiting for start");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            power = gamepad1.left_stick_x;
            motor1.setPower(-power);
            motor2.setPower(power);
        }
    }
}

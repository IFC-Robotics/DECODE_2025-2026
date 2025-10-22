package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.robot.ServoClass;
@Disabled
@TeleOp(name = "ServoTest")

public class ServoTest extends LinearOpMode {
    public void runOpMode() {
        ServoClass servoLauncher = new ServoClass("servo_launcher", "down", 0.0, "middle", 0.5, "up", 1.0, 0.5, 500, false);
        servoLauncher.init(this);
        waitForStart();

        while (opModeIsActive()) {
            servoLauncher.teleOpAssistMode(gamepad1.a, gamepad1.b, gamepad1.y);
        }
    }
}

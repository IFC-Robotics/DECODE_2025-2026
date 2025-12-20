package org.firstinspires.ftc.teamcode.competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;


import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name="TeleOp")
public class TeleOpMode extends LinearOpMode {
    @Override
    public void runOpMode() {
        telemetry.addLine("Initializing OpMode...");
        telemetry.update();
        VoltageSensor voltageSensor = hardwareMap.voltageSensor.iterator().next();

        Robot.init(this,false,false);
        waitForStart();
        telemetry.addLine("Starting OpMode...");
        while(opModeIsActive()){
            double voltage = voltageSensor.getVoltage();
            Robot.drivetrain.teleOp(gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x,gamepad1.left_trigger > 0);
            Robot.motorIntake.teleOpBool(gamepad1.b, gamepad1.x, false);
//            Robot.motorLaunchR.teleOpBool(gamepad2.left_bumper || gamepad1.left_bumper, gamepad1.left_trigger > 0, voltage>11);
//            Robot.motorLaunchL.teleOpBool(gamepad2.left_bumper || gamepad1.left_bumper, gamepad1.left_trigger > 0, voltage>11);
            Robot.motorLaunchL.teleOpSetVelocity(gamepad2.left_bumper || gamepad1.left_bumper, gamepad1.left_trigger > 0 || gamepad2.left_trigger > 0);
            Robot.motorLaunchR.teleOpSetVelocity(gamepad2.left_bumper || gamepad1.left_bumper, gamepad1.left_trigger > 0 || gamepad2.left_trigger > 0);
            Robot.motorConveyor.teleOpBool(gamepad2.a || gamepad1.a, gamepad2.y || gamepad1.y,  false);

            Robot.servoLauncher.teleOpAssistMode(gamepad1.right_bumper, false, gamepad1.right_trigger > 0.1 || gamepad2.b);
            Robot.servoFlipper.teleOpAssistMode(gamepad2.right_bumper, false, gamepad2.right_trigger > 0.1);

            //Robot.aprilTagWebcam.detectAprilTags();
        }
    }
}

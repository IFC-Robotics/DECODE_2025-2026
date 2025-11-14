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

        Robot.init(this,false,true);
        waitForStart();
        telemetry.addLine("Starting OpMode...");
        while(opModeIsActive()){
            double voltage = voltageSensor.getVoltage();
            Robot.drivetrain.teleOp(gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x,gamepad1.left_trigger > 0);
            Robot.motorIntake.teleOpBool(gamepad1.b, false, false);
            Robot.motorLaunchR.teleOpBool(gamepad2.left_bumper || gamepad1.left_bumper, gamepad2.left_trigger > 0, voltage>11);
            Robot.motorLaunchL.teleOpBool(gamepad2.left_bumper || gamepad1.left_bumper, gamepad2.left_trigger > 0, voltage>11);
            Robot.motorConveyor.teleOpBool(gamepad2.a || gamepad1.a, gamepad2.y || gamepad1.y,  false);

            //Robot.aprilTagWebcam.detectAprilTags();
        }
    }
}

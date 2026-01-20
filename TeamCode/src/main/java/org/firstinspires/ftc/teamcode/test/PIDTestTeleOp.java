package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name="PID Test TeleOp")
public class PIDTestTeleOp extends LinearOpMode {

    FtcDashboard dashboard; // http://192.168.43.1:8080/dash is the link to the dashboard
    Telemetry dashboardTelemetry;

    public static double targetRPM = 1900;
    int ticksPerRev = 28;
    double targetVelocityTicks = ticksPerRev * (targetRPM / 60);

    @Override
    public void runOpMode() {
        dashboard = FtcDashboard.getInstance();

        dashboardTelemetry = dashboard.getTelemetry();
        telemetry = new MultipleTelemetry(telemetry, dashboardTelemetry);

        telemetry.addLine("Initializing OpMode...");
        telemetry.update();
        VoltageSensor voltageSensor = hardwareMap.voltageSensor.iterator().next();

        Robot.init(this,false,false);
        waitForStart();
        telemetry.addLine("Starting OpMode...");
        while(opModeIsActive()){
            double voltage = voltageSensor.getVoltage();
            Robot.motorLaunchL.teleOpSetVelocity(gamepad2.left_bumper || gamepad1.left_bumper, gamepad1.left_trigger > 0 || gamepad2.left_trigger > 0, targetRPM);
            Robot.motorLaunchR.teleOpSetVelocity(gamepad2.left_bumper || gamepad1.left_bumper, gamepad1.left_trigger > 0 || gamepad2.left_trigger > 0, targetRPM);

            Robot.motorLaunchL.printVandPData();
            Robot.motorLaunchR.printVandPData();

            telemetry.addData("Target velocity", targetVelocityTicks);
            telemetry.update();
        }
    }
}

package org.firstinspires.ftc.teamcode.test;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import org.firstinspires.ftc.teamcode.robot.MotorClass;
import org.firstinspires.ftc.teamcode.robot.Robot;

import com.acmerobotics.dashboard.FtcDashboard;

@Config
@Autonomous(name="PIDTest", group="test")
public class PIDTest extends LinearOpMode {
    double integralSum = 0;
    public static double kP = 0.0005;
    public static double kI = 0.0;
    public static double kD = 0.0005;
    public static double kV = 0.00067;
    public static double alpha = 0.97;

    FtcDashboard dashboard; // http://192.168.43.1:8080/dash is the link to the dashboard
    Telemetry dashboardTelemetry;

    int ticksPerRev = 28;

    ElapsedTime timer = new ElapsedTime();

    ElapsedTime loopTimer = new ElapsedTime();
    public static double LOOP_PERIOD = 0.02;

    private double lastError = 0;
    double filteredVelocity = 0;
    double filteredVelocity2 = 0;

    MotorClass motor;

    public static double targetVelocity = 700;//ticksPerRev * ((double) 500 /60);

    @Override
    public void runOpMode() {
        motor = new MotorClass("motor", 1, 500,false);
        motor.init(this);
        motor.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        dashboard = FtcDashboard.getInstance();
        dashboardTelemetry = dashboard.getTelemetry();
        telemetry = new MultipleTelemetry(telemetry, dashboardTelemetry);

        waitForStart();
        timer.reset();

        int windowSize = 25;
        double[] velocities = new double[windowSize];
        double avgV;


        while (opModeIsActive()) {
            if (loopTimer.seconds() < LOOP_PERIOD) continue;
            loopTimer.reset();

            double rawVelocity = motor.motor.getVelocity();
                avgV = 0;
                for (int i = 0; i <= windowSize - 2; i++) {
                    velocities[i] = velocities[i+1];
                    avgV += velocities[i];
                }
                velocities[windowSize - 1] = rawVelocity;
                avgV = (avgV + rawVelocity) / windowSize;

            filteredVelocity = alpha * filteredVelocity + (1 - alpha) * avgV;
//            filteredVelocity2 = alpha * filteredVelocity2 + (1 - alpha) * filteredVelocity;

            double power = PIDControl(targetVelocity, filteredVelocity);
            motor.motor.setPower(power);

            telemetry.addData("Raw Velocity", rawVelocity);
            telemetry.addData("Filtered Velocity", filteredVelocity);
            telemetry.addData("Avg Velocity", avgV);

            telemetry.addData("Target velocity", targetVelocity);
            telemetry.addData("Power", power);
            telemetry.update();
        }
    }

    public double PIDControl(double target, double state){
        double error = target - state;

        double dt = timer.seconds();
        timer.reset();

        if (dt <= 0) return 0;

        integralSum += error * dt;
        double derivative = (error - lastError) / dt;
        telemetry.addData("P", error);
        telemetry.addData("I", integralSum);
        telemetry.addData("D", derivative);
        telemetry.update();
        lastError = error;

        double pid = kP * error + kI * integralSum + kD * derivative;
        double ff = kV * target;

        return Math.max(-1, Math.min(1, pid + ff));
    }
}

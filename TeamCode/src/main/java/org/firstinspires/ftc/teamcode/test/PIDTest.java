package org.firstinspires.ftc.teamcode.test;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import android.os.Environment;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import org.firstinspires.ftc.teamcode.robot.MotorClass;
import org.firstinspires.ftc.teamcode.robot.Robot;

import com.acmerobotics.dashboard.FtcDashboard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Config
@Autonomous(name="PIDTest", group="test")
public class PIDTest extends LinearOpMode {
    double integralSum = 0;
    public static double kP = 0.0005;
    public static double kI = 0.0;
    public static double kD = 0.0005;
    public static double kV = 0.00055;
//    public static double power = 0.3;
    public static double cutoffFreq1 = 5;
    public static double cutoffFreq2 = 0.5;

    public static boolean PIDOn = true;
    public static double topLine = 820;
    public static double bottomLine = 780;

    FtcDashboard dashboard; // http://192.168.43.1:8080/dash is the link to the dashboard
    Telemetry dashboardTelemetry;

    FileWriter csvWriter;
    boolean loggingEnabled = false;

    int ticksPerRev = 28;

    ElapsedTime timer = new ElapsedTime();

    ElapsedTime loopTimer = new ElapsedTime();
    public static double LOOP_PERIOD = 0.02;

    private double lastError = 0;
    double filteredVelocity = 0;

    MotorClass motor;

    public static double targetVelocity = 850;//ticksPerRev * ((double) 500 /60);

    @Override
    public void runOpMode() {
        VoltageSensor voltageSensor = hardwareMap.voltageSensor.iterator().next();

        motor = new MotorClass("motor", 1, 500,false);
        motor.init(this);
        motor.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        dashboard = FtcDashboard.getInstance();

        dashboardTelemetry = dashboard.getTelemetry();
        telemetry = new MultipleTelemetry(telemetry, dashboardTelemetry);

        double sampleHz = 1 / LOOP_PERIOD;
        BiquadLowPass Biquadfilter = new BiquadLowPass(this, sampleHz, cutoffFreq1, cutoffFreq2, targetVelocity);

        //open csv we'll log to
        try {
            File logFile = new File(
                    Environment.getExternalStorageDirectory(),
                    "FIRST/velocity_log.csv"
            );

            csvWriter = new FileWriter(logFile);
            csvWriter.write("time,voltage,power,targetvelocity,rawvelocity,biquadvelocity\n");
            loggingEnabled = true;
            telemetry.addLine("CSV log opened");
            telemetry.update();

        } catch (IOException e) {
            telemetry.addLine("CSV log failed");
            telemetry.update();
        }

        waitForStart();
        timer.reset();

        int windowSize = 25;
        double[] velocities = new double[windowSize];
        double avgV;

        while (opModeIsActive()) {
            if (loopTimer.seconds() < LOOP_PERIOD) continue;
            loopTimer.reset();

            double rawVelocity = motor.motor.getVelocity();
            filteredVelocity = Biquadfilter.filter(rawVelocity);

//            avgV = 0;
//            for (int i = 0; i <= windowSize - 2; i++) {
//                velocities[i] = velocities[i+1];
//                avgV += velocities[i];
//            }
//            velocities[windowSize - 1] = rawVelocity;
//            avgV = (avgV + rawVelocity) / windowSize;

//            filteredVelocity1 = Biquadfilter2.filter(filteredVelocity);

//            filteredVelocity1 = alpha * filteredVelocity1 + (1 - alpha) * avgV;
//            filteredVelocity2 = alpha * filteredVelocity2 + (1 - alpha) * filteredVelocity;
            double power;
            if (PIDOn){
                power = PIDControl(targetVelocity, filteredVelocity);
            } else {
                power = kV * targetVelocity;
            }
            motor.motor.setPower(power);
            double voltage = voltageSensor.getVoltage();

            if (loggingEnabled) {
                double time = getRuntime();
                try {
                    csvWriter.write(
                            time + "," +
                                    voltage + "," +
                                    power + "," +
                                    targetVelocity + "," +
                                    rawVelocity + "," +
                                    filteredVelocity + "\n"
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            telemetry.addData("Raw Velocity", rawVelocity);
            telemetry.addData("Biquad Filtered Velocity", filteredVelocity);
//            telemetry.addData("Avg Filtered Velocity", filteredVelocity1);
//            telemetry.addData("Filtered Velocity", filteredVelocity2);
//            telemetry.addData("Avg Velocity", avgV);

            telemetry.addData("Target velocity", targetVelocity);
            telemetry.addData("Top", topLine);
            telemetry.addData("Bottom", bottomLine);
//            telemetry.addData("Power", power);
            telemetry.update();
        }

        if (csvWriter != null) {
            try {
                csvWriter.flush();
                csvWriter.close();
            } catch (IOException ignored) {}
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

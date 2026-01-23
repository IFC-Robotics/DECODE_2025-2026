package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.test.BiquadLowPass;

import java.util.List;

public class MotorClass {

    public LinearOpMode opMode;
    public Telemetry telemetry;

    public DcMotorEx motor;
    public double  motorCurrentPower = 0;
    public double  motorRawVelocity = 0;
    public boolean continuous = false;

    public final String name;
    public final double maxSpeed;
    public final int sleepTime;
    public final boolean reverseDirection;
    public double[] PIDCoeffs;
    public int targetRPM;

    ElapsedTime timer = new ElapsedTime();
    ElapsedTime loopTimer = new ElapsedTime();
    ElapsedTime motorRunningTimer = new ElapsedTime();
    public static double LOOP_PERIOD = 0.02;
    double sampleHz = 1 / LOOP_PERIOD;

    double integralSum = 0;
    double lastError = 0;
    double filteredVelocity = 0;
    double ticksPerRev = 28;

    BiquadLowPass Biquadfilter;

    public MotorClass(String name, double maxSpeed, int sleepTime, boolean reverseDirection) {

        this.name = name;
        this.maxSpeed = maxSpeed;
        this.sleepTime = sleepTime;
        this.reverseDirection = reverseDirection;

    }

    public void init(LinearOpMode opModeParam) {
        init(opModeParam, true, new double[]{0.0}, 0, 0);
    }

    public void init(LinearOpMode opModeParam, boolean encoders, double[] PIDCoeffs, double cutoffFreq1, double cutoffFreq2) {

        opMode = opModeParam;
        telemetry = opMode.telemetry;
        this.PIDCoeffs = PIDCoeffs;
//        this.targetRPM = targetRPM;

        motor = opMode.hardwareMap.get(DcMotorEx.class, this.name);

        if (this.reverseDirection) motor.setDirection(DcMotor.Direction.REVERSE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        if (encoders) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        else {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            double targetTicksPerSec = targetRPM * 60 * ticksPerRev;
            Biquadfilter = new BiquadLowPass(opMode, sampleHz, cutoffFreq1, cutoffFreq2, targetTicksPerSec);
        }

    }
    // helper function


    // autonomous

    public void runToPosition(int position) {
        runToPosition(position, true, this.maxSpeed); }
    public void runToPosition(int position, boolean isSynchronous) {
        runToPosition(position, isSynchronous, this.maxSpeed); }

    public void runToPosition(int position, boolean isSynchronous, double speed) {
        int target = position;
        motor.setTargetPosition(target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorCurrentPower = speed;
        motor.setPower(motorCurrentPower);
        if (!isSynchronous) waitForMotor();
    }

    public void waitForMotor() {
        while (motor.isBusy()) {}
        opMode.sleep(this.sleepTime);
    }



    // teleOp

    public void teleOp(float button1,float button2) {

        float speed = 0;
        if (Math.abs(button1) > Math.abs(button2)) {
            speed = button1;
        } else if (Math.abs(button1) < Math.abs(button2)) {
            speed = button2;
        }

        motor.setPower(speed * maxSpeed);

        printData();

    }

    public void teleOpBool(boolean button1, boolean reverseButton, boolean halfPower) {
        double speed = 0;
        if (button1 || reverseButton){
            if (halfPower) {
                speed = 1;
            } else {
                speed = 1;
            }
            if (reverseButton) {
                speed *= -1;
            }
        }

        motor.setPower(speed * maxSpeed);

        printData();
    }

    public void teleOpSetVelocity(boolean button1, boolean reverseButton, double targetRPM) {
        double targetVelocityTicks = ticksPerRev * (targetRPM / 60);
        if (button1) {
            setConstVelocity(targetVelocityTicks);
        } else if (reverseButton) {
            motor.setPower(-1*maxSpeed);
        }
        else {
            stopMotor();
        }
    }

    public void setConstVelocity(double targetVelocityTicks) {
        setConstVelocity(targetVelocityTicks, 0);
    }
    public void setConstVelocity(double targetVelocityTicks, int runTimeSeconds) {

        if (!motor.isBusy()) {
            motorRunningTimer.reset();
        }

        if (runTimeSeconds == 0 || motorRunningTimer.seconds() < runTimeSeconds){
            if (loopTimer.seconds() > LOOP_PERIOD) {
                loopTimer.reset();
                motorRawVelocity = motor.getVelocity();

                filteredVelocity = Biquadfilter.filter(motorRawVelocity);
                double power = PIDControl(this.PIDCoeffs, targetVelocityTicks, filteredVelocity);
                motor.setPower(power);
            }
        } else{
            stopMotor();
        }

        telemetry.addLine(String.format("\n%1$s target velocity: %2$s", this.name, targetVelocityTicks));
        telemetry.addLine(String.format("\n%1$svelocity: %2$s", this.name, filteredVelocity));
        telemetry.update();
    }

    public void stopMotor() {
        motor.setPower(0);
        integralSum = 0;
        lastError = 0;
        motorRawVelocity = 0;
        filteredVelocity = 0;
        timer.reset();
        loopTimer.reset();
        motorRunningTimer.reset();
        Biquadfilter.reset();
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public double PIDControl(double[] PIDCoeffs, double target, double state){
        double error = target - state;

        double dt = timer.seconds();
        timer.reset();

        if (dt <= 0) return 0;

        integralSum += error * dt;
        double derivative = (error - lastError) / dt;
        lastError = error;

        double pid = PIDCoeffs[0] * error + PIDCoeffs[1] * integralSum + PIDCoeffs[2] * derivative;
        double ff = PIDCoeffs[3] * target;

        return Math.max(-1, Math.min(1, pid + ff));
    }


    public void printData() {
        telemetry.addLine(String.format("\n%1$s Running: %2$s", this.name, motor.isBusy()));
        telemetry.addLine(String.format("\n%1$s position: %2$s", this.name, motor.getCurrentPosition()));
        telemetry.addLine(String.format("%1$s power: %2$s", this.name, motor.getPower()));
    }

    public void printVandPData() {
        telemetry.addLine(String.format("\n%1$s Running: %2$s", this.name, motor.isBusy()));
        telemetry.addData(String.format("\n%1$s raw velocity", this.name), motorRawVelocity);
        telemetry.addData(String.format("\n%1$s filtered velocity", this.name), filteredVelocity);
        telemetry.addData(String.format("%1$s power", this.name), motor.getPower());
    }

}
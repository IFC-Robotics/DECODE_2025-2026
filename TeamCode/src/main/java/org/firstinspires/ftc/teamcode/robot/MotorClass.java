package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MotorClass {

    public LinearOpMode opMode;
    public Telemetry telemetry;

    public DcMotorEx motor;
    public double  motorCurrentSpeed = 0;
    public boolean continuous = false;

    public final String name;
    public final double maxSpeed;
    public final int sleepTime;
    public final boolean reverseDirection;
    public double[] PIDCoeffs;
    public double alpha;
    public int targetRPM;

    ElapsedTime timer = new ElapsedTime();
    ElapsedTime loopTimer = new ElapsedTime();
    public static double LOOP_PERIOD = 0.02;

    double integralSum = 0;
    double lastError = 0;
    double filteredVelocity = 0;
    int windowSize = 25;
    double[] velocities = new double[windowSize];
    double avgV;
    double ticksPerRev = 28;

    public MotorClass(String name, double maxSpeed, int sleepTime, boolean reverseDirection) {

        this.name = name;
        this.maxSpeed = maxSpeed;
        this.sleepTime = sleepTime;
        this.reverseDirection = reverseDirection;

    }

    public void init(LinearOpMode opModeParam) {
        init(opModeParam, true, new double[]{0.0}, 0, 0);
    }

    public void init(LinearOpMode opModeParam, boolean encoders, double[] PIDCoeffs, double alpha, int targetRPM) {

        opMode = opModeParam;
        telemetry = opMode.telemetry;
        this.PIDCoeffs = PIDCoeffs;
        this.alpha = alpha;
        this.targetRPM = targetRPM;

        motor = opMode.hardwareMap.get(DcMotorEx.class, this.name);

        if (this.reverseDirection) motor.setDirection(DcMotor.Direction.REVERSE);

        if (encoders) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        else {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

    }
    // helper function


    // autonomous

    public void runToPosition(int position) {
        runToPosition(position, false, this.maxSpeed); }
    public void runToPosition(int position, boolean isSynchronous) {
        runToPosition(position, isSynchronous, this.maxSpeed); }

    public void runToPosition(int position, boolean isSynchronous, double speed) {
        //int target = position;
        motor.setTargetPosition(position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorCurrentSpeed = speed;
        motor.setPower(motorCurrentSpeed);
        if (isSynchronous) waitForMotor();
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

    public void teleOpBool(boolean button1, boolean reverseButton, boolean halfPower, float halfPowerMultiplier) {
        double speed = 0;
        if (button1 || reverseButton){
            if (halfPower) {
                speed = halfPowerMultiplier;
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

    public void teleOpSetVelocity(boolean button1, boolean reverseButton) {
//        this.PIDCoeffs[1] = 0;
//        this.PIDCoeffs[2] = 0;
        if (button1) {
            setConstVelocity(this.PIDCoeffs, this.targetRPM);
        } else if (reverseButton) {
            motor.setPower(-1*maxSpeed);
        }
        else {
            stopMotor();
        }
    }

    public void setConstVelocity(double[] PIDCoeffs, double rpm) {
        double targetVelocity = ticksPerRev * (rpm / 60);

        if (loopTimer.seconds() > LOOP_PERIOD) {
            loopTimer.reset();
            double rawVelocity = motor.getVelocity();
            avgV = 0;
            for (int i = 0; i <= windowSize - 2; i++) {
                velocities[i] = velocities[i+1];
                avgV += velocities[i];
            }
            velocities[windowSize - 1] = rawVelocity;
            avgV = (avgV + rawVelocity) / windowSize;

            filteredVelocity = alpha * filteredVelocity + (1 - alpha) * avgV;
            double power = PIDControl(PIDCoeffs, targetVelocity, filteredVelocity);
            motor.setPower(power);
        }

        telemetry.addLine(String.format("\n%1$s target velocity: %2$s", this.name, targetVelocity));
        telemetry.addLine(String.format("\n%1$svelocity: %2$s", this.name, filteredVelocity));
        telemetry.update();
    }

    public void stopMotor() {
        motor.setPower(0);
        integralSum = 0;
        lastError = 0;
        filteredVelocity = 0;
        velocities = new double[windowSize];
        timer.reset();
        loopTimer.reset();
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
        telemetry.addLine(String.format("%1$s speed: %2$s", this.name, motorCurrentSpeed));
    }

}
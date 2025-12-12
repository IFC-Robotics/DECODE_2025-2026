package org.firstinspires.ftc.teamcode.test;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.arcrobotics.ftclib.controller.PController;
import com.arcrobotics.ftclib.controller.PDController;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.SimpleMotorFeedforward;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.MotorClass;
import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name="PIDTest", group="test")
public class PIDTest extends LinearOpMode {
    double integralSum = 0;
    double kP = 1;
    double kI = 0;
    double kD = 0.5;
    double kF = 1;

//    Ftc dashboard;

    int ticksPerRev = 28;

    ElapsedTime timer = new ElapsedTime();

    private double lastError = 0;

    MotorClass motor;

    double targetVelocity = ticksPerRev * (int)(500/60);

    @Override
    public void runOpMode() {
        motor = new MotorClass("motor", 1, 500,false);
        motor.init(this);
        motor.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                double power = PIDControl(targetVelocity, motor.motor.getVelocity());
                motor.motor.setPower(power);
                motor.telemetry.addLine(String.format("target velocity: %1$s", motor.motor.getVelocity()));
                motor.telemetry.addLine(String.format("velocity: %1$s", motor.motor.getVelocity()));
                motor.telemetry.update();
            }
        }
    }

    public double PIDControl(double target, double state){
        double error = target - state;
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / timer.seconds();
        lastError = error;


        timer.reset();

        double output = (error * kP) + (derivative * kD) + (integralSum * kI);
        return output;
    }

//    // Creates a PIDFController with gains kP, kI, kD, and kF
//    PIDFController pidf = new PIDFController(kP, kI, kD, kF);
//
//    // Create a new SimpleMotorFeedforward with gains kS, kV, and kA
//    SimpleMotorFeedforward feedforward =
//            new SimpleMotorFeedforward(kS, kV);
//
//    double output = pidf.calculate(
//            Robot.motorLaunchL.motor.getVelocity(), 560
//    );



}

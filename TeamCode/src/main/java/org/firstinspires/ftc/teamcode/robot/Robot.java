package org.firstinspires.ftc.teamcode.robot;

import android.graphics.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
//robot uses the different classes in this code so if you need specifics look at the class
public class Robot {
    //a log (pretty much console.log)
    public static Telemetry telemetry;
    public static Drivetrain drivetrain;
//    public static LiftClass verticalLiftL;
//    public static LiftClass verticalLiftR;
    public static ServoClass servoLauncher;
    public static MotorClass motorIntake;
    public static MotorClass motorLaunchL;
    public static MotorClass motorLaunchR;
    public static MotorClass motorConveyor;
    public static CameraClass aprilTagWebcam;

    public static MotorClass motorArm;
    //    public static MotorClass motorPulley;

    public static double MAX_LIFT_SPEED = 0.2;
    public static double MAX_MOTOR_SPEED = 0.7;
    public static double SERVO_SPEED = 0.01;
    public static int SERVO_TIME = 800;
    public static int CR_SERVO_TIME = 1600;
    public static int SLEEP_TIME = 50;

    public static String mode = "assist";


    // initialize

    public static void init(LinearOpMode opMode, boolean onlyDrive) {

        telemetry = opMode.telemetry;

        telemetry.addLine("initializing robot class...");
        telemetry.update();


        drivetrain     = new Drivetrain("launcher", SLEEP_TIME);
        drivetrain.init(opMode);

        if (!onlyDrive) {
//
//            servoLauncher = new ServoClass("servo_launcher", "up", 0.05, "middle",0.3 ,"down",0.38, SERVO_SPEED, SERVO_TIME, false);
            motorIntake = new MotorClass("motor_intake", MAX_MOTOR_SPEED, SLEEP_TIME,true);
            motorLaunchL = new MotorClass("motor_launch_left", MAX_MOTOR_SPEED*0.5, SLEEP_TIME, false);
            motorLaunchR = new MotorClass("motor_launch_right", MAX_MOTOR_SPEED*0.5, SLEEP_TIME, true);
            motorConveyor = new MotorClass("motor_conveyor", MAX_MOTOR_SPEED, SLEEP_TIME, false);
//            aprilTagWebcam = new CameraClass("webcam");                                                                                                                                                         // =)

//            verticalLiftL.init(opMode);
//            verticalLiftR.init(opMode);
//            servoLauncher.init(opMode);

//            motorPulley.init(opMode);
            motorIntake.init(opMode);
            motorLaunchL.init(opMode);
            motorLaunchR.init(opMode);
            motorConveyor.init(opMode);
//            aprilTagWebcam.init(opMode);
        }


//

    }

    //April Tag Methods





}
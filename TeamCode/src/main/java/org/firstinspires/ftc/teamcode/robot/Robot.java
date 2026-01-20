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
    public static ServoClass servoFlipper;
    public static MotorClass motorIntake;
    public static MotorClass motorLaunchL;
    public static MotorClass motorLaunchR;
    public static MotorClass motorConveyor;
    public static CameraClass aprilTagWebcam;

    public static MotorClass motorArm;
    //    public static MotorClass motorPulley;

    public static double MAX_LIFT_SPEED = 0.2;
    public static double MAX_MOTOR_SPEED = 1.0;
    public static double SERVO_SPEED = 0.01;
    public static int SERVO_TIME = 800;
    public static int CR_SERVO_TIME = 1600;
    public static int SLEEP_TIME = 50;

    public static double[] LAUNCH_L_PID_COEFFS = new double[]{0.0002, 0, 0.0001, 0.000626};
    public static double[] LAUNCH_R_PID_COEFFS = new double[]{0.0003, 0, 0.0003, 0.00055};
    public static double cutoffFreq1 = 5;
    public static double cutoffFreq2 = 0.5;
    public static int targetRPM = 1900;

    public static String mode = "assist";


    // initialize

    public static void init(LinearOpMode opMode, boolean onlyDrive, boolean useCamera) {

        telemetry = opMode.telemetry;

        telemetry.addLine("initializing robot class...");
        telemetry.update();


        drivetrain     = new Drivetrain("launcher", SLEEP_TIME);
        drivetrain.init(opMode);

        if (!onlyDrive) {
//
            servoLauncher = new ServoClass("servo_launcher", "down", 0, "middle",0.3 ,"up",0.5, SERVO_SPEED, SERVO_TIME, false);
            servoFlipper = new ServoClass("divider_servo", "down", 0, "middle",0.3 ,"up",0.4, SERVO_SPEED, SERVO_TIME, false);

            motorIntake = new MotorClass("motor_intake", MAX_MOTOR_SPEED, SLEEP_TIME,true);
            motorLaunchL = new MotorClass("motor_launch_left", MAX_MOTOR_SPEED*0.35, SLEEP_TIME, false);
            motorLaunchR = new MotorClass("motor_launch_right", MAX_MOTOR_SPEED*0.35, SLEEP_TIME, true);
            motorConveyor = new MotorClass("motor_conveyor", MAX_MOTOR_SPEED, SLEEP_TIME, true);
            if(useCamera){
                aprilTagWebcam = new CameraClass("webcam");
                aprilTagWebcam.init(opMode);
            }

//            verticalLiftL.init(opMode);
//            verticalLiftR.init(opMode);
              servoLauncher.init(opMode);
              servoFlipper.init(opMode);

//            motorPulley.init(opMode);
            motorIntake.init(opMode);
            motorLaunchL.init(opMode, false, LAUNCH_L_PID_COEFFS, cutoffFreq1, cutoffFreq2);
            motorLaunchR.init(opMode, false, LAUNCH_R_PID_COEFFS, cutoffFreq1, cutoffFreq2);
            motorConveyor.init(opMode);

        }


//

    }

    //April Tag Methods





}
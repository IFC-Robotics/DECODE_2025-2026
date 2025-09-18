package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
//robot uses the different classes in this code so if you need specifics look at the class
public class Robot {
    //a log (pretty much console.log)
    public static Telemetry telemetry;
    //
    public static Drivetrain drivetrain;
    //
    public static LiftClass verticalLiftL;
    public static LiftClass verticalLiftR;
    public static ServoClass servoArmL;
    public static ServoClass servoArmR;
    public static ServoClass servoClawL;
    public static ServoClass servoClawR;

    public static MotorClass motorArm;
    public static MotorClass motorHanger;
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


        drivetrain     = new Drivetrain("collector", SLEEP_TIME);
        drivetrain.init(opMode);

        if (onlyDrive == false) {
            verticalLiftL =   new LiftClass("motor_vertical_lift_l", MAX_LIFT_SPEED, 0.00006, SLEEP_TIME, false);
            verticalLiftR =   new LiftClass("motor_vertical_lift_r", MAX_LIFT_SPEED*1.055, 0.00042, SLEEP_TIME, false);
//            servoLR  =     new ServoClass("servo_left_right", "left", 0.2, "middle",0.5,"right", 0.8, SERVO_SPEED, SERVO_TIME, false);
            servoArmL =      new ServoClass("servo_arm_left", "up", 0.05, "middle",0.3 ,"down",0.38, SERVO_SPEED, SERVO_TIME, false);
            servoArmR =      new ServoClass("servo_arm_right", "down", 0.17, "middle",0.48 ,"up",0.52, SERVO_SPEED, SERVO_TIME, false);
            servoClawL = new ServoClass("servo_claw_left", "closed", 0.0, "middle",0.2 ,"open",0.35, SERVO_SPEED, SERVO_TIME, false);
            servoClawR = new ServoClass("servo_claw_right", "open", 0.15, "middle",0.2 ,"closed",0.41, SERVO_SPEED, SERVO_TIME, false);
//            motorArm       = new MotorClass("motor_arm", 0.5*MAX_MOTOR_SPEED, SLEEP_TIME, true);
            motorHanger =    new MotorClass("motor_hanger", MAX_MOTOR_SPEED, SLEEP_TIME, false);
//            motorPulley =       new MotorClass("motor_pulley", MAX_MOTOR_SPEED,SLEEP_TIME, false);

            verticalLiftL.init(opMode);
            verticalLiftR.init(opMode);
            servoArmL.init(opMode);
            servoArmR.init(opMode);

            servoClawL.init(opMode);
            servoClawR.init(opMode);
//            motorArm.init(opMode);
            motorHanger.init(opMode);
//            motorPulley.init(opMode);
        }


//

    }

    //April Tag Methods





}
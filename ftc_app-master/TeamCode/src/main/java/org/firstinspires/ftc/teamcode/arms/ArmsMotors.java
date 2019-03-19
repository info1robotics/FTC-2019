package org.firstinspires.ftc.teamcode.arms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 *      Class used to handle motors related to the robot's arms.
 *
 *      Supports
 *          * Initializing the motors to the desired position.
 *          * Setting the same RunMode on all motors.
 */

public class ArmsMotors {

    public DcMotor climbMotor;
    /*
         UNCOMMENT LINES BELLOW IF REVERTING TO ORIGINAL MINERAL ARM.

    public DcMotor collectMotorMove1;
    public DcMotor collectMotorMove2;
    public DcMotor collectMotorExtend;
    */
    public DcMotor collectMotor;
    public DcMotor dropMotor;

    public ArmsMotors(HardwareMap hardwareMap, Telemetry telemetry) {
        climbMotor = hardwareMap.get(DcMotor.class, "climbMotor");
        /*
             UNCOMMENT LINES BELLOW IF REVERTING TO ORIGINAL MINERAL ARM.

        collectMotorExtend = hardwareMap.get(DcMotor.class, "collectMotorExtend");
        collectMotorMove1 = hardwareMap.get(DcMotor.class, "collectMotorMove1");
        collectMotorMove2 = hardwareMap.get(DcMotor.class, "collectMotorMove2");
        */
        collectMotor = hardwareMap.get(DcMotor.class, "collectMotor");
        dropMotor = hardwareMap.get(DcMotor.class, "dropMotor");

        telemetry.addData("Arms motors", "Set up.");
    }

    public void setMode(DcMotor.RunMode mode) {
        this.climbMotor.setMode(mode);
        /*

            UNCOMMENT LINES BELLOW IF REVERTING TO ORIGINAL MINERAL ARM.

        this.collectMotorMove1.setMode(mode);
        this.collectMotorMove2.setMode(mode);
        this.collectMotorExtend.setMode(mode);
        */
        this.collectMotor.setMode(mode);
        this.dropMotor.setMode(mode);
    }

}

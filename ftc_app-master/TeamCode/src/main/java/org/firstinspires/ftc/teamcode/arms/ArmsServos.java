package org.firstinspires.ftc.teamcode.arms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 *          Class handling servos related to the robot's arms.
 *
 *          Supports
 *              * initializing the servos in the required position for the robot to start.
 */


public class ArmsServos {

    Servo mineralHit;
    CRServo mineralArmBrush;
    public Servo mineralBoxTilt;
    Servo macotBoxTilt;

    ArmsServos(HardwareMap hardwareMap, Telemetry telemetry) {
        mineralArmBrush = hardwareMap.get(CRServo.class, "mineralArmBrush");
        mineralHit = hardwareMap.get(Servo.class, "mineralHit");
        mineralBoxTilt = hardwareMap.get(Servo.class, "mineralBoxTilt");
        mineralBoxTilt.setDirection(Servo.Direction.REVERSE);
        //blah = hardwareMap.get(CRServo.class, "blah");
        //CRServo.Direction dir = blah.getDirection();
        //macotBoxTilt = hardwareMap.get(Servo.class, "mascotBoxTilt");

        telemetry.addData("Arms servos", "Setup");
    }

}

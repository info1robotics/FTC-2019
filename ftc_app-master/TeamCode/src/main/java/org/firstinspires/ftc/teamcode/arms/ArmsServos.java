package org.firstinspires.ftc.teamcode.arms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmsServos {

    Servo mineralHit;
    Servo mineralArmBrush;
    Servo mineralBoxTilt;
    Servo macotBoxTilt;

    ArmsServos(HardwareMap hardwareMap, Telemetry telemetry) {
        mineralArmBrush = hardwareMap.get(Servo.class, "mineralArmBrush");
        mineralHit = hardwareMap.get(Servo.class, "mineralHit");
        mineralBoxTilt = hardwareMap.get(Servo.class, "mineralBoxTilt");
        //macotBoxTilt = hardwareMap.get(Servo.class, "mascotBoxTilt");

        telemetry.addData("Arms servos", "Setup");
    }

}

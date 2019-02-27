package org.firstinspires.ftc.teamcode.oldies;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@Autonomous
public class servoplm extends LinearOpMode {
    Servo servoGold;
    @Override
    public void runOpMode(){
        servoGold=hardwareMap.get(Servo.class,"servoGold");
        telemetry.addData("Status","init");
        telemetry.update();
        waitForStart();
        servoGold.setPosition(0);
        sleep(1000);
        telemetry.addData("position",servoGold.getPosition());
        telemetry.update();
        servoGold.setPosition(0.75);
        idle();
        sleep(1000);
        telemetry.addData("position",servoGold.getPosition());
        telemetry.update();
        //servoGold.setDirection(Servo.Direction.REVERSE);
        servoGold.setPosition(0);
        telemetry.addData("position",servoGold.getPosition());
        telemetry.update();
        //servoGold.setDirection(Servo.Direction.FORWARD);
        sleep(10000);

    }
}

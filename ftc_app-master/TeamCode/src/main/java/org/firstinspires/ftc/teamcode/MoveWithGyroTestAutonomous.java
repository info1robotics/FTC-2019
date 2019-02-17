package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;

@Autonomous
public class MoveWithGyroTestAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        MoveWithGyro movement = new MoveWithGyro(0.75, telemetry, hardwareMap, this);
        movement.resetAngle();
        waitForStart();

        // Move right 10 cm
        movement.moveRightAutonomous(238);
        sleep(10);
        movement.correctPosition();
        //sleep(3000);

        // Move back 40 cm
        movement.moveBackAutonomous(952);
        sleep(10);
        movement.correctPosition();
        sleep(1000);

        // Move left 50 cm
        movement.moveLeftAutonomous(1190);
        sleep(10);
        movement.correctPosition();
        sleep(1000);

        // Move right 75 cm
        movement.moveRightAutonomous(1785);
        sleep(10);
        movement.correctPosition();
        sleep(1000);


        // Move right 65 cm
        movement.moveRightAutonomous(1547);
        sleep(10);
        movement.correctPosition();
        //sleep(3000);

        // Rotate right 45 degrees
        movement.rotate(-40, true);
        sleep(10);
        //sleep(3000);

        // Move right 90 cm
        movement.moveForwardAutonomous(2142);
        sleep(10);
        movement.correctPosition();
        //sleep(3000);


        // Rotate left 90 degrees
        movement.rotate(80, true);
        sleep(10);


        telemetry.addLine("Dropping mascot");
        telemetry.update();
        sleep(3000);
        telemetry.addLine("Dropped mascot");
        telemetry.update();

        //Rotate left 90 degrees
        movement.rotate(80, true);
        sleep(10);
        //sleep(3000);

        // Move forward 145 cm
        movement.moveForwardAutonomous(3600);
        sleep(10);
        movement.correctPosition();
        //sleep(3000);
    }

}

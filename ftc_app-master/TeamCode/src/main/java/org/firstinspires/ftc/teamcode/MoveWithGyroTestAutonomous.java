package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;
import org.firstinspires.ftc.teamcode.movement.Movement;
import org.firstinspires.ftc.teamcode.vision_smecher.ObjectCodes;
import org.firstinspires.ftc.teamcode.vision_smecher.WebcamVision;

import java.io.ObjectStreamException;

@Autonomous
public class MoveWithGyroTestAutonomous extends LinearOpMode {

    MoveWithGyro movement;
    WebcamVision webCamViewer;
    int count = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        webCamViewer = new WebcamVision(hardwareMap, telemetry);
        movement = new MoveWithGyro(0.75, telemetry, hardwareMap, this);
        movement.resetAngle();
        waitForStart();

        // Move right 10 cm
        movement.moveRightAutonomous(238);
        sleep(10);
        movement.correctPosition();
        //sleep(3000);

        lookForGold();
        /*
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
        */
    }

    private void lookForGold() {
        webCamViewer.activateRecognition();
        if (foundGold()) engage(1430);
        else {

            // Move back 40 cm
            movement.moveBackAutonomous(952);
            sleep(10);
            movement.correctPosition();

            // Move left 50 cm
            movement.moveLeftAutonomous(1190);
            sleep(10);
            movement.correctPosition();

            if (foundGold()) engage(476);
            else {

                // Move right 75 cm
                movement.moveRightAutonomous(1785);
                sleep(10);
                movement.correctPosition();
                engage(476);
            }
        }
        webCamViewer.deactivateRecognition();
    }

    private boolean foundGold() {
        int objectFound = webCamViewer.getDetected();
        while (objectFound == ObjectCodes.NO_OBJECT) {
            count += 1;
            telemetry.addData("Object code", objectFound);
            telemetry.addData("Tries", count);
            telemetry.update();
            objectFound = webCamViewer.getDetected();
        }
        telemetry.addData("Object code", objectFound);
        telemetry.update();
        return (objectFound == ObjectCodes.GOLD_MINERAL);
    }

    private void engage(int forwardTicks) {
        movement.moveBackAutonomous(forwardTicks);
        movement.correctPosition();
        movement.moveForwardAutonomous(476);
        movement.correctPosition();
    }

}

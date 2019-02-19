package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;
import org.firstinspires.ftc.teamcode.vision_smecher.ObjectCodes;
import org.firstinspires.ftc.teamcode.vision_smecher.WebcamVision;

@Autonomous
public class AutonomousTestCrater extends LinearOpMode {

    private MoveWithGyro movement;
    private WebcamVision webCamViewer;
    private Servo servoGold;
    private static final int CM_TO_TICKS_FACTOR = 112;
    private static final double DEFAULT_POWER = 0.7;

    @Override
    public void runOpMode() {
        movement = new MoveWithGyro(DEFAULT_POWER, telemetry, hardwareMap, this);
        webCamViewer = new WebcamVision(hardwareMap, telemetry);
        servoGold = hardwareMap.get(Servo.class, "servoGold");
        movement.resetAngle();
        telemetry.update();
        servoGold.setPosition(0);

        waitForStart();

        // TODO: Add code for going down from lander here

        // Moving right 10 cm
        movement.moveRightAutonomous(cmToTicks(10));
        movement.correctPosition();

        // Looking for gold
        lookForGold();
        /*
        // Turning 45 degrees to the right
        movement.rotate(-40, true);

        // Moving forward 100 cm
        movement.moveForwardAutonomous(cmToTicks(85));
        movement.correctPosition();

        // Rotating 90 degrees to the left
        movement.rotate(85, true);

        // Dropping mascot
        sleep(2000);
        // TODO: Add code here for dropping mascot

        // Rotating 90 degrees to the left
        movement.rotate(85, true);

        // Moving forward 145 cm
        movement.moveForwardAutonomous(cmToTicks(145));
        movement.correctPosition();
        */
    }

    private void foundInFirstTryPolicy() {
        movement.moveBackAutonomous(cmToTicks(47));
        movement.correctPosition();
        movement.moveBackAutonomous(cmToTicks(5));
        movement.correctPosition();
        engage(0, 0);
        movement.moveRightAutonomous(cmToTicks(95));
        movement.correctPosition();
    }

    private void foundInSecondTryPolicy() {
        engage(0, 0);
        movement.moveRightAutonomous(cmToTicks(155));
        movement.correctPosition();
    }

    private void foundInThirdTryPolicy() {
        movement.moveRightAutonomous(cmToTicks(82));
        movement.correctPosition();
        engage(0,0);
        movement.moveRightAutonomous(cmToTicks(70));
    }

    private void lookForGold() {
        webCamViewer.activateRecognition();
        if (foundGold()) foundInFirstTryPolicy();
        else {

            // Move back 35 cm
            movement.moveBackAutonomous(cmToTicks(37));
            sleep(10);
            movement.correctPosition();

            // Move left 50 cm
            movement.moveLeftAutonomous(cmToTicks(50));
            sleep(10);
            movement.correctPosition();

            if (foundGold()) foundInSecondTryPolicy();
            else foundInThirdTryPolicy();
        }
        webCamViewer.deactivateRecognition();
    }

    private boolean foundGold() {
        int objectFound = webCamViewer.getDetected();
        while (objectFound == ObjectCodes.NO_OBJECT) {
            telemetry.addData("Object code", objectFound);
            telemetry.update();
            objectFound = webCamViewer.getDetected();
        }
        telemetry.addData("Object code", objectFound);
        telemetry.update();
        return (objectFound == ObjectCodes.GOLD_MINERAL);
    }

    private void engage(int backCm, int forwardCm) {
        sleep(500);
        servoGold.setPosition(.5);
        sleep(1000);
        servoGold.setPosition(0);
    }

    private int cmToTicks(int cm) {
        return (cm * CM_TO_TICKS_FACTOR) / 5;
    }
}

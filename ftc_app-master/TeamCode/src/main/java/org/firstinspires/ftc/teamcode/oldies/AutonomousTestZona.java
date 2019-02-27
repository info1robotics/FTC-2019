package org.firstinspires.ftc.teamcode.oldies;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;
import org.firstinspires.ftc.teamcode.vision_smecher.ObjectCodes;
import org.firstinspires.ftc.teamcode.vision_smecher.WebcamVision;

@Disabled
public class AutonomousTestZona extends LinearOpMode {

    private MoveWithGyro movement;
    private WebcamVision webCamViewer;
    private static final int CM_TO_TICKS_FACTOR = 112;
    private static final double DEFAULT_POWER = 0.7;
    private static final double SCANNING_POWER = 0.5;

    @Override
    public void runOpMode() {
        movement = new MoveWithGyro(telemetry, hardwareMap, this);
        webCamViewer = new WebcamVision(hardwareMap, telemetry);
        movement.resetAngle();
        telemetry.update();

        waitForStart();

        // TODO: Add code for going down from lander here

        // Moving right 10 cm
        movement.moveRightAutonomous(cmToTicks(10), true);

        // Looking for gold
        lookForGold();

        // Turning 45 degrees to the left
        movement.spinAutonomous(45, true);

        // Moving back 100 cm
        movement.moveBackAutonomous(cmToTicks(100), true);

        // Rotating 90 degrees to the left
        movement.spinAutonomous(90, true);

        // Dropping mascot
        sleep(2000);
        // TODO: Add code here for dropping mascot

        // Rotating 90 degrees to the right
        movement.spinAutonomous(-90, true);

        // Moving forward 145 cm
        movement.moveForwardAutonomous(cmToTicks(145), true);
    }

    private void foundInFirstTryPolicy() {
        engage(50, 15);
        movement.moveLeftAutonomous(cmToTicks(105), true);
    }

    private void foundInSecondTryPolicy() {
        engage(15, 15);
        movement.moveLeftAutonomous(cmToTicks(155), true);
    }

    private void foundInThirdTryPolicy() {
        movement.moveLeftAutonomous(cmToTicks(80), true);
        engage(15,15);
        movement.moveLeftAutonomous(cmToTicks(75), true);
    }

    private void lookForGold() {
        webCamViewer.activateRecognition();
        //movement.setMovementPower(SCANNING_POWER);
        telemetry.addLine("SCANNING...");
        telemetry.update();
        if (foundGold()) foundInFirstTryPolicy();
        else {
            // Move back 35 cm
            movement.moveBackAutonomous(cmToTicks(30), true);
            sleep(10);

            // Move left 50 cm
            movement.moveRightAutonomous(cmToTicks(50), true);
            sleep(10);
            movement.correctPosition();

            if (foundGold()) foundInSecondTryPolicy();
            else foundInThirdTryPolicy();
        }
        //movement.setMovementPower(DEFAULT_POWER);
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
        movement.moveBackAutonomous(cmToTicks(backCm), true);
        movement.moveForwardAutonomous(cmToTicks(forwardCm), true);
    }

    private int cmToTicks(int cm) {
        return (cm * CM_TO_TICKS_FACTOR) / 5;
    }


}

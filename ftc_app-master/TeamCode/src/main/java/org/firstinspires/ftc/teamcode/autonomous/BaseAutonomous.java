package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.arms.ArmsAction;
import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;
import org.firstinspires.ftc.teamcode.vision.ObjectCodes;
import org.firstinspires.ftc.teamcode.vision.WebcamVision;

/**
 *      Base (abstract) class used in autonomous movement.
 *
 *      Supports:
 *          * Initializing all required handlers (movement & arms & vision)
 *          * Detaching from lander
 *          * Searching and hitting the gold mineral.
 */

abstract class BaseAutonomous extends LinearOpMode {

    MoveWithGyro movementHandler;
    ArmsAction armsHandler;
    WebcamVision visionHandler;

    private static long MINERAL_SEARCH_TIMEOUT = 1000;
    public static int DISTANCE_DETACH_FROM_LANDER = 8;


    void initialize() {
        movementHandler = new MoveWithGyro(telemetry, hardwareMap, this, true);
        movementHandler.setMovementPower(0.3);
        armsHandler = new ArmsAction(hardwareMap, telemetry, this, true);
        visionHandler = new WebcamVision(hardwareMap, telemetry);
    }

    void hitMineral() {
        this.armsHandler.hitMineral();
    }

    boolean foundGold() {
        int objectFound = visionHandler.getDetected();
        long startedAt = System.currentTimeMillis();
        while (objectFound == ObjectCodes.NO_OBJECT) {
            telemetry.addData("Object code", objectFound);
            telemetry.update();
            objectFound = visionHandler.getDetected();
            if (System.currentTimeMillis() - startedAt > MINERAL_SEARCH_TIMEOUT)
                break;
        }
        telemetry.addData("Object code", objectFound);
        telemetry.update();
        return (objectFound == ObjectCodes.GOLD_MINERAL);
    }

    void detachFromLander() {
        armsHandler.descendFromLanderAutonomous();
        movementHandler.moveRightAutonomous(DISTANCE_DETACH_FROM_LANDER, true);
    }

    abstract void park();

    void lookForGold() {
        visionHandler.activateRecognition();
        if (foundGold()) foundInFirstTryPolicy();
        else {
            movementHandler.moveBackAutonomous(30, true);
            movementHandler.moveLeftAutonomous(45, true);
            if (foundGold()) foundInSecondTryPolicy();
            else foundInThirdTryPolicy();
        }

        visionHandler.deactivateRecognition();
    }

    void dropMascot() {
        // TODO: needs implementing. Will be done in further stages of the project
    }


    private void foundInFirstTryPolicy() {
        movementHandler.moveBackAutonomous(50, false);
        hitMineral();
        movementHandler.moveForwardAutonomous(13, true);
        movementHandler.moveRightAutonomous(145, true);
    }

    private void foundInSecondTryPolicy() {
        movementHandler.moveBackAutonomous(16, true);
        hitMineral();
        movementHandler.moveForwardAutonomous(16, true);
        movementHandler.moveRightAutonomous(175, true);
    }

    private void foundInThirdTryPolicy() {
        movementHandler.moveRightAutonomous(80, true);
        movementHandler.moveBackAutonomous(16, true);
        hitMineral();
        movementHandler.moveForwardAutonomous(16, true);
        movementHandler.moveRightAutonomous(95, true);
    }



}
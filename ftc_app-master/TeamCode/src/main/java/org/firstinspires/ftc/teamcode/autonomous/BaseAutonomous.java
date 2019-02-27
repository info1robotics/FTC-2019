package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.arms.ArmsAction;
import org.firstinspires.ftc.teamcode.autonomous.constants.GeneralConstants;
import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;
import org.firstinspires.ftc.teamcode.vision_smecher.ObjectCodes;
import org.firstinspires.ftc.teamcode.vision_smecher.WebcamVision;

import java.util.Timer;

abstract class BaseAutonomous extends LinearOpMode {

    MoveWithGyro movementHandler;
    ArmsAction armsHandler;
    WebcamVision visionHandler;

    void initialize() {
        movementHandler = new MoveWithGyro(telemetry, hardwareMap, this);
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
            if (System.currentTimeMillis() - startedAt > GeneralConstants.MINERAL_SEARCH_TIMEOUT)
                break;
        }
        telemetry.addData("Object code", objectFound);
        telemetry.update();
        return (objectFound == ObjectCodes.GOLD_MINERAL);
    }

    void detachFromLander() {
        armsHandler.descendFromLanderAutonomous();
        movementHandler.moveRightAutonomous(GeneralConstants.DISTANCE_DETACH_FROM_LANDER, true);
    }

    abstract void lookForGold();
    abstract void park();


    void dropMascot() {
        // TODO: needs implementing. Will be done in further stages of the project
    }



}

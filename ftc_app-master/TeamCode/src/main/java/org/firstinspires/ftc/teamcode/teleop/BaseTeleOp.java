package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.arms.ArmsAction;
import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;
import org.firstinspires.ftc.teamcode.teleop.buttons.Button;
import org.firstinspires.ftc.teamcode.teleop.buttons.ButtonNames;

import java.util.HashSet;

/**
 *      Base (abstract) class for the controlled stage.
 *
 *      Supports
 *          * Initializing the required handlers (for arms & movement)
 */

abstract class BaseTeleOp extends LinearOpMode {

    MoveWithGyro movementHandler;
    ArmsAction armsHandler;
    HashSet<Button> buttonsList;

    void initialize() {
        movementHandler = new MoveWithGyro(this.telemetry, this.hardwareMap, this);
        armsHandler = new ArmsAction(this.hardwareMap, this.telemetry, this, false);
        buttonsList = new HashSet<>();
        addButtonsToList();
    }

    private void addButtonsToList() {
        buttonsList.add(ButtonNames.A);
        buttonsList.add(ButtonNames.B);
        buttonsList.add(ButtonNames.X);
        buttonsList.add(ButtonNames.Y);
        buttonsList.add(ButtonNames.RB);
        buttonsList.add(ButtonNames.LB);
        buttonsList.add(ButtonNames.LT);
        buttonsList.add(ButtonNames.RT);
        buttonsList.add(ButtonNames.DPAD_DOWN);
        buttonsList.add(ButtonNames.DPAD_UP);
        buttonsList.add(ButtonNames.DPAD_LEFT);
        buttonsList.add(ButtonNames.DPAD_RIGHT);
        buttonsList.add(ButtonNames.LEFT_JOYSTICK);
        buttonsList.add(ButtonNames.RIGHT_JOYSTICK);
    }

    abstract void mapButtonsToActions();

}

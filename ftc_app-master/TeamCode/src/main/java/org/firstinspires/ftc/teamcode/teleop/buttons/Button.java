package org.firstinspires.ftc.teamcode.teleop.buttons;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.arms.ArmsAction;
import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;
import org.firstinspires.ftc.teamcode.teleop.buttons.ButtonAction;

public abstract class Button {

    public String name;
    public boolean continousPressing;
    public boolean usedLastTime;
    private ButtonAction action;

    public Button(String name, boolean continousPressing) {
        this.name = name;
        this.continousPressing = continousPressing;
        this.usedLastTime = false;
    }
    
    public void recordPressed() {
        this.usedLastTime = true;
    }
    
    public void setAction(ButtonAction newAction) {
        action = newAction;
    }
    
    public void doStuff(MoveWithGyro moveHandler, ArmsAction armsHandler, Gamepad gamepad) {
        if (this.action == null) {
            // The button is not yet configured, so we'll do nothing
            return;
        }
        if (!checkIfAppropriate(gamepad)) {
            // The button was not pressed.
            this.usedLastTime = false;
            return;
        }

        if (usedLastTime && !continousPressing) {
            // The button is not configured for continuous pressing and it was held pressed.
            return;
        }

        this.action.doStuff(armsHandler, moveHandler, gamepad);
        this.usedLastTime = true;
    }

    abstract boolean checkIfAppropriate(Gamepad gamepad);

}

package org.firstinspires.ftc.teamcode.teleop.buttons;

import com.qualcomm.robotcore.hardware.Gamepad;

public class ButtonNames {

    // Defining the button class for the left joystick and the check if action is required.
    public static final Button LEFT_JOYSTICK = new Button("left_stick", true) {
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return (gamepad.left_stick_x != 0 || gamepad.left_stick_y != 0);
        }
    };
    // Defining the button class for the right joystick and the check if action is required.
    public static final Button RIGHT_JOYSTICK = new Button("right_stick", true) {
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return (gamepad.right_stick_x != 0 || gamepad.right_stick_y != 0);
        }
    };
    // Defining the button class for the up button and the check if action is required.
    public static final Button DPAD_UP = new Button("dpad_up", true) {
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return gamepad.dpad_up;
        }
    };
    // Defining the button class for the down button and the check if action is required.
    public static final Button DPAD_DOWN = new Button("dpad_down", true) {
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return gamepad.dpad_down;
        }
    };
    public static final Button DPAD_RIGHT = new Button("dpad_right", true) {
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return gamepad.dpad_right;
        }
    };
    public static final Button DPAD_LEFT = new Button("dpad_left", true) {
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return gamepad.dpad_left;
        }
    };
    public static final Button A = new Button("a", true){
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return gamepad.a;
        }
    };
    public static final Button B = new Button("b", true) {
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return gamepad.b;
        }
    };
    public static final Button X = new Button("x", false) {
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return gamepad.x;
        }
    };
    public static final Button Y = new Button("y", false){
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return gamepad.y;
        }
    };
    public static final Button RT = new Button("rt", true){
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return gamepad.right_trigger > 0;
        }
    };
    public static final Button LT = new Button("lt", true){
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return gamepad.left_trigger > 0;
        }
    };
    public static final Button RB = new Button("rb", false){
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return gamepad.right_bumper;
        }
    };
    public static final Button LB = new Button("lb", false){
        @Override
        boolean checkIfAppropriate(Gamepad gamepad) {
            return gamepad.left_bumper;
        }
    };
}

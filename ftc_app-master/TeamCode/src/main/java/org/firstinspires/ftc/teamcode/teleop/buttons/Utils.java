package org.firstinspires.ftc.teamcode.teleop.buttons;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.arms.ArmsAction;
import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;

public class Utils {

    public static void moveForward(MoveWithGyro movementHandler, Gamepad gamepad, LinearOpMode opMode) {
        while(opMode.opModeIsActive() && gamepad.dpad_up) movementHandler.moveForward();
        movementHandler.correctPosition();
        movementHandler.stopAll();
    }

    public static void moveBack(MoveWithGyro movementHandler, Gamepad gamepad, LinearOpMode opMode) {
        while(opMode.opModeIsActive() && gamepad.dpad_down) movementHandler.moveBack();
        movementHandler.correctPosition();
        movementHandler.stopAll();
    }

    public static void moveRight(MoveWithGyro movementHandler, Gamepad gamepad, LinearOpMode opMode) {
        while(opMode.opModeIsActive() && gamepad.dpad_right) movementHandler.moveRight();
        movementHandler.correctPosition();
        movementHandler.stopAll();
    }

    public static void moveLeft(MoveWithGyro movementHandler, Gamepad gamepad, LinearOpMode opMode) {
        while(opMode.opModeIsActive() && gamepad.dpad_left) movementHandler.moveLeft();
        movementHandler.correctPosition();
        movementHandler.stopAll();
    }

    public static void latchAndUnlatch(ArmsAction armsHandler, Gamepad gamepad, LinearOpMode opMode) {
        while (opMode.opModeIsActive() && gamepad.right_stick_y != 0) {
            double power = Math.abs(gamepad.right_stick_y);
            if (gamepad.right_stick_y > 0) armsHandler.climbOnLander(power);
            else armsHandler.descendFromLander(power);
        }
        armsHandler.stopAll();
    }

    public static void moveMineralArm(ArmsAction armsHandler, Gamepad gamepad, LinearOpMode opMode) {
        while (opMode.opModeIsActive() && gamepad.left_stick_x != 0) {
            double power = Math.abs(gamepad.left_stick_x);
            if (gamepad.left_stick_x > 0) armsHandler.moveMineralArmRight(power);
            else armsHandler.moveMineralArmLeft(power);
        }
        armsHandler.stopAll();
    }

    public static void extendMineralArm(ArmsAction armsHandler, Gamepad gamepad, LinearOpMode opMode) {
        while (opMode.opModeIsActive() && gamepad.a) armsHandler.extendMineralArm(1);
        armsHandler.stopAll();
    }

    public static void contractMineralArm(ArmsAction armsHandler, Gamepad gamepad, LinearOpMode opMode) {
        while (opMode.opModeIsActive() && gamepad.b) armsHandler.contractMineralArm(1);
        armsHandler.stopAll();
    }

    public static void tiltMineralBoxUnloading(ArmsAction armsHandler) {
        armsHandler.setMineralBoxTo0();
    }

    public static void tiltMineralBoxMoving(ArmsAction armsHandler) {
        armsHandler.setMineralBoxTo45();
    }

    public static void changeMineralBrushDirection(ArmsAction armsHandler) {
        armsHandler.changeDirectionMineralArmBrush();
    }

    public static void spinLeft(MoveWithGyro movementHandler, Gamepad gamepad, LinearOpMode opMode) {
        while(opMode.opModeIsActive() && gamepad.left_trigger != 0) {
            double power = gamepad.left_trigger;
            movementHandler.spin(power, -1);
        }
        movementHandler.resetAngle();
        movementHandler.stopAll();
    }

    public static void spinRight(MoveWithGyro movementHandler, Gamepad gamepad, LinearOpMode opMode) {
        while (opMode.opModeIsActive() && gamepad.right_trigger != 0) {
            double power = gamepad.right_trigger;
            movementHandler.spin(power, 1);
        }
        movementHandler.resetAngle();
        movementHandler.stopAll();
    }

}

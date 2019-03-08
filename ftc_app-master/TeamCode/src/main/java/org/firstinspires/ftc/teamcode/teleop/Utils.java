package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.arms.ArmsAction;
import org.firstinspires.ftc.teamcode.arms.constants.MotorTicks;
import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;


/**
 *      Class defining all required actions for a controlled stage.
 *
 *      Supports
 *          * Moving the robot on all 4 axes.
 *          * Moving the mineral arm.
 *          * Extending/ Contracting the robot arm.
 *          * Spinning the robot.
 *          * Changing the direction of the mineral brush.
 *          * Tilting the mineral box at 90/ 45 degrees from the mineral arm.
 *          * Turning the correction on/ off.
 */

public class Utils {

    private static void checkMineralArmSync(ArmsAction armsHandler, Gamepad gamepad) {
        if (gamepad.left_stick_x > 0) {
            armsHandler.moveMineralArmRight(gamepad.left_stick_x);
        } else if (gamepad.left_stick_x < 0) {
            armsHandler.moveMineralArmLeft(Math.abs(gamepad.left_stick_x));
        } else {
            armsHandler.armsMotors.collectMotorMove1.setPower(0);
            armsHandler.armsMotors.collectMotorMove2.setPower(0);
        }
    }

    private static void checkMineralArmExtendContractSync(ArmsAction armsHandler, Gamepad gamepad) {
        if (gamepad.a) armsHandler.extendMineralArm(1);
        else if (gamepad.b) armsHandler.contractMineralArm(1);
        else armsHandler.armsMotors.collectMotorExtend.setPower(0);
    }

    private static void checkMovementSync(MoveWithGyro movementHandler, Gamepad gamepad) {
        if (gamepad.dpad_up) movementHandler.moveForward();
        else if (gamepad.dpad_down) movementHandler.moveBack();
        else if (gamepad.dpad_right) movementHandler.moveRight();
        else if (gamepad.dpad_left) movementHandler.moveLeft();
        else movementHandler.stopAll();
    }

    public static void moveForward(MoveWithGyro movementHandler, Gamepad gamepad, LinearOpMode opMode,
                                   ArmsAction armsHandler) {
        while(opMode.opModeIsActive() && gamepad.dpad_up) {
            movementHandler.moveForward();
            checkMineralArmSync(armsHandler, gamepad);
            opMode.idle();
        }
        movementHandler.correctPosition();
        movementHandler.stopAll();
    }

    public static void moveBack(MoveWithGyro movementHandler, Gamepad gamepad, LinearOpMode opMode,
                                ArmsAction armsHandler) {
        while(opMode.opModeIsActive() && gamepad.dpad_down) {
            movementHandler.moveBack();
            checkMineralArmSync(armsHandler, gamepad);
            checkMineralArmExtendContractSync(armsHandler, gamepad);
            opMode.idle();
        }
        movementHandler.correctPosition();
        movementHandler.stopAll();
    }

    public static void moveRight(MoveWithGyro movementHandler, Gamepad gamepad, LinearOpMode opMode,
                                 ArmsAction armsHandler) {
        while(opMode.opModeIsActive() && gamepad.dpad_right) {
            movementHandler.moveRight();
            checkMineralArmSync(armsHandler, gamepad);
            checkMineralArmExtendContractSync(armsHandler, gamepad);
            opMode.idle();
        }
        movementHandler.correctPosition();
        movementHandler.stopAll();
    }

    public static void moveLeft(MoveWithGyro movementHandler, Gamepad gamepad, LinearOpMode opMode,
                                ArmsAction armsHandler) {
        while(opMode.opModeIsActive() && gamepad.dpad_left) {
            movementHandler.moveLeft();
            checkMineralArmSync(armsHandler, gamepad);
            checkMineralArmExtendContractSync(armsHandler, gamepad);
            opMode.idle();
        }
        movementHandler.correctPosition();
        movementHandler.stopAll();
    }

    public static void latchAndUnlatch(ArmsAction armsHandler, Gamepad gamepad, LinearOpMode opMode) {
        int refPos = armsHandler.armsMotors.climbMotor.getCurrentPosition();
        int targetTicks = Math.abs(MotorTicks.DESCEND_ROBOT);
        while (opMode.opModeIsActive() && gamepad.right_stick_y != 0) {
            double power = Math.abs(gamepad.right_stick_y);
            if (gamepad.right_stick_y > 0) armsHandler.climbOnLander(power);
            else armsHandler.descendFromLander(power);
            int currentPos = armsHandler.armsMotors.climbMotor.getCurrentPosition();
            if (Math.abs(currentPos - refPos) > targetTicks) break;
            opMode.idle();
        }
        armsHandler.stopAll();
    }

    public static void moveMineralArm(ArmsAction armsHandler, Gamepad gamepad, LinearOpMode opMode,
                                      MoveWithGyro movementHandler) {
        int referenceTicks = (Math.abs(armsHandler.armsMotors.collectMotorMove1.getCurrentPosition())
                + (Math.abs(armsHandler.armsMotors.collectMotorMove2.getCurrentPosition()))) / 2;
        int direction = 0;
        int counter = 0;
        while (opMode.opModeIsActive() && gamepad.left_stick_x != 0) {
            double power = Math.abs(gamepad.left_stick_x);
            if (gamepad.left_stick_x > 0) direction = 1;
            else direction = -1;
            if (gamepad.left_stick_x > 0) armsHandler.moveMineralArmRight(power);
            else armsHandler.moveMineralArmLeft(power);
            if (gamepad.left_bumper) tiltMineralBoxUnloading(armsHandler);
            if (gamepad.right_bumper) tiltMineralBoxMoving(armsHandler);
            int currentTicks = (Math.abs(armsHandler.armsMotors.collectMotorMove1.getCurrentPosition())
                    + (Math.abs(armsHandler.armsMotors.collectMotorMove2.getCurrentPosition()))) / 2;
            if (Math.abs(currentTicks - referenceTicks) >= MotorTicks.MINERAL_ARM_TO_LANDER / 2 && gamepad.left_stick_x > 0)
                tiltMineralBoxMoving(armsHandler);
            checkMineralArmExtendContractSync(armsHandler, gamepad);
            checkMovementSync(movementHandler, gamepad);
            opMode.idle();
        }
        while (opMode.opModeIsActive() && counter < 5 && gamepad.left_stick_x == 0 ) {
            if (direction == 1) {
                armsHandler.moveMineralArmLeft(1);
            } else {
                armsHandler.moveMineralArmRight(0);
            }
            counter += 1;
            opMode.idle();
        }
        armsHandler.stopAll();
    }

    public static void extendMineralArm(ArmsAction armsHandler, Gamepad gamepad, LinearOpMode opMode,
                                        MoveWithGyro movementHandler) {
        while (opMode.opModeIsActive() && gamepad.a) {
            armsHandler.extendMineralArm(1);
            checkMineralArmSync(armsHandler, gamepad);
            checkMovementSync(movementHandler, gamepad);
            opMode.idle();
        }
        armsHandler.stopAll();
    }

    public static void contractMineralArm(ArmsAction armsHandler, Gamepad gamepad, LinearOpMode opMode,
                                          MoveWithGyro movementHandler) {
        while (opMode.opModeIsActive() && gamepad.b) {
            armsHandler.contractMineralArm(1);
            checkMineralArmSync(armsHandler, gamepad);
            checkMovementSync(movementHandler, gamepad);
            opMode.idle();
        }
        armsHandler.stopAll();
    }

    public static void tiltMineralBoxUnloading(ArmsAction armsHandler) {
        armsHandler.setMineralBoxDrop();
    }

    public static void tiltMineralBoxCollecting(ArmsAction armsHandler) {
        armsHandler.setMineralBoxTo0();
    }

    /**
     *  FUNCTIE PENTRU TEST
     * @param armsHandler
     */
    public static void tiltMineralBoxMoving(ArmsAction armsHandler) {
        armsHandler.setMineralBoxTo45();
    }

    public static void changeMineralBrushDirection(ArmsAction armsHandler) {
        armsHandler.changeDirectionMineralArmBrush();
    }

    public static void spinLeft(MoveWithGyro movementHandler, Gamepad gamepad, LinearOpMode opMode,
                                ArmsAction armsHandler) {
        while(opMode.opModeIsActive() && gamepad.left_trigger != 0) {
            double power = gamepad.left_trigger;
            movementHandler.spin(power, 1);
            checkMineralArmExtendContractSync(armsHandler, gamepad);
            checkMineralArmSync(armsHandler, gamepad);
            opMode.idle();
        }
        movementHandler.stopAll();
        movementHandler.resetAngle();
        movementHandler.stopAll();
    }

    public static void spinRight(MoveWithGyro movementHandler, Gamepad gamepad, LinearOpMode opMode,
                                 ArmsAction armsHandler) {
        while (opMode.opModeIsActive() && gamepad.right_trigger != 0) {
            double power = gamepad.right_trigger;
            movementHandler.spin(power, -1);
            opMode.idle();
        }
        movementHandler.stopAll();
        movementHandler.resetAngle();
        movementHandler.stopAll();
    }

    public static void turnMovementCorrectionOnOff(MoveWithGyro movementHandler) {
        movementHandler.turnCorrectionOnOff();
    }

}

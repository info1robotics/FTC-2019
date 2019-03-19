package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 *      Initial (not elegant) implementation of a TeleOp class.
 *
 *      Supports
 *          * Initializing the required handlers.
 *          * Mapping controller buttons to actions.
 */

@TeleOp
public class  TeleOpBruteForce extends BaseTeleOp{

    @Override
    void mapButtonsToActions() {
        // Not used here
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_down) Utils.moveBack(movementHandler, gamepad1, this, armsHandler);
            if (gamepad1.dpad_up) Utils.moveForward(movementHandler, gamepad1, this, armsHandler);
            if (gamepad1.dpad_right) Utils.moveRight(movementHandler, gamepad1, this, armsHandler);
            if (gamepad1.dpad_left) Utils.moveLeft(movementHandler, gamepad1, this, armsHandler);
            if (gamepad1.a) Utils.extendMineralArm(armsHandler, gamepad1, this, movementHandler);
            if (gamepad1.b) Utils.contractMineralArm(armsHandler, gamepad1, this, movementHandler);
            if (gamepad1.y) {
                Utils.changeMineralBrushDirection(armsHandler);
                while (gamepad1.y){
                    idle();
                    // wait
                }
            }
            if (gamepad1.left_stick_x != 0) {
                telemetry.addData("x coordinate", gamepad1.left_stick_x);
                Utils.moveMineralArm(armsHandler, gamepad1, this, movementHandler);
            }
            if (gamepad1.right_stick_y != 0) Utils.latchAndUnlatch(armsHandler, gamepad1, this);
            if (gamepad1.left_trigger > 0) Utils.spinLeft(movementHandler, gamepad1, this, armsHandler);
            if (gamepad1.right_trigger > 0) Utils.spinRight(movementHandler, gamepad1, this, armsHandler);
            if (gamepad1.left_bumper) Utils.tiltMineralBoxUnloading(armsHandler);
            if (gamepad1.right_bumper)  Utils.tiltMineralBoxCollecting(armsHandler);

            /**
             *  PENTRU TESTE DE 45 DE GRADE LA SERVO:
             *      Utils.mineralBoxMoving(armsHandler);
             *      Utils.tiltMineralBoxCollecting(armsHandler);
             *  ^
             *  |
             *  pe un buton
             */
            if (gamepad1.x) {
                Utils.turnMovementCorrectionOnOff(movementHandler);
                while (gamepad1.x){
                    idle();
                }
            }
            idle();
        }

    }
}

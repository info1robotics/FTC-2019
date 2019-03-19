package org.firstinspires.ftc.teamcode.teleop;

public class TeleOpNew extends BaseTeleOp{


    @Override
    void mapButtonsToActions() {
        // NOT USED HERE
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_down) Utils.moveBack(movementHandler, gamepad1, this, armsHandler);
            if (gamepad1.dpad_up) Utils.moveForward(movementHandler, gamepad1, this, armsHandler);
            if (gamepad1.dpad_right) Utils.moveRight(movementHandler, gamepad1, this, armsHandler);
            if (gamepad1.dpad_left) Utils.moveLeft(movementHandler, gamepad1, this, armsHandler);
            if (gamepad1.y) {
                Utils.changeMineralBrushDirection(armsHandler);
                while (gamepad1.y){
                    idle();
                    // wait
                }
            }
            if (gamepad1.x) {
                Utils.turnMovementCorrectionOnOff(movementHandler);
                while (gamepad1.x){
                    // wait
                    idle();
                }
            }

            idle();

        }

    }
}

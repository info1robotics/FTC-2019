package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutonomousCraterNoMascot extends BaseAutonomous {

    @Override
    void lookForGold() {
        visionHandler.activateRecognition();
        if (foundGold()) foundInFirstTryPolicy();
        else {
            movementHandler.moveBackAutonomous(37, true);
            movementHandler.moveLeftAutonomous(50, true);
            if (foundGold()) foundInSecondTryPolicy();
            else foundInThirdTryPolicy();
        }
        visionHandler.deactivateRecognition();
    }

    @Override
    void park() {
        movementHandler.spinAutonomous(-45, true);
        movementHandler.moveBackAutonomous(50, true);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        telemetry.update();
        waitForStart();
        detachFromLander();
        lookForGold();
        park();
    }

    private void foundInFirstTryPolicy() {
        movementHandler.moveBackAutonomous(55, true);
        hitMineral();
        movementHandler.moveForwardAutonomous(20, true);
        movementHandler.moveRightAutonomous(95, true);
    }

    private void foundInSecondTryPolicy() {
        movementHandler.moveBackAutonomous(20, true);
        hitMineral();
        movementHandler.moveForwardAutonomous(20, true);
        movementHandler.moveRightAutonomous(155, true);
    }

    private void foundInThirdTryPolicy() {
        movementHandler.moveRightAutonomous(82, true);
        movementHandler.moveBackAutonomous(20, true);
        hitMineral();
        movementHandler.moveForwardAutonomous(20, true);
        movementHandler.moveRightAutonomous(65, true);
    }
}

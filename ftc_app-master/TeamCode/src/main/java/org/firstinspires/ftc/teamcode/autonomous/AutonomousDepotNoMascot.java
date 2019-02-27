package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutonomousDepotNoMascot extends BaseAutonomous{
    @Override
    void lookForGold() {
        visionHandler.activateRecognition();
        if (foundGold()) foundInFirstTryPolicy();
        else {
            movementHandler.moveBackAutonomous(37, true);
            movementHandler.moveRightAutonomous(50, true);
            if (foundGold()) foundInSecondTryPolicy();
            else foundInThirdTryPolicy();
        }
        visionHandler.deactivateRecognition();
    }

    @Override
    void park() {
        movementHandler.spinAutonomous(45, true);
        movementHandler.moveBackAutonomous(120, true);
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
        movementHandler.moveLeftAutonomous(95, true);
    }

    private void foundInSecondTryPolicy() {
        movementHandler.moveBackAutonomous(20, true);
        hitMineral();
        movementHandler.moveForwardAutonomous(20, true);
        movementHandler.moveLeftAutonomous(155, true);
    }

    private void foundInThirdTryPolicy() {
        movementHandler.moveLeftAutonomous(82, true);
        movementHandler.moveBackAutonomous(20, true);
        hitMineral();
        movementHandler.moveLeftAutonomous(65, true);
    }

}

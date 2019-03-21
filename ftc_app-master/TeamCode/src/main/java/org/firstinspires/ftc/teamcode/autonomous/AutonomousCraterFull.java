package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutonomousCraterFull extends BaseAutonomous {
    @Override
    void park() {
        movementHandler.moveRightAutonomous(145, true);
        armsHandler.extendArmAutonomous(100);
    }

    @Override
    void dropMascot() {
        movementHandler.spinAutonomous(45, true);
        movementHandler.moveRightAutonomous(50, true);
        armsHandler.changeDirectionMineralArmBrush();
        sleep(500);
        movementHandler.spinAutonomous(180, true);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        telemetry.update();
        waitForStart();
        detachFromLander();
        lookForGold();
        dropMascot();
        park();
    }
}

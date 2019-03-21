package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class MineralHitTest extends BaseAutonomous {


    @Override
    void park() {
        return;
    }

    @Override
    void dropMascot() {

    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        telemetry.update();
        waitForStart();
        detachFromLander();
        hitMineral();
    }
}

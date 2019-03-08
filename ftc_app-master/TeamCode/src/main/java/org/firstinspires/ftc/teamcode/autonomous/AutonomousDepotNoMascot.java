package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 *      Class defining an autonomous protocol when starting next to the depot.
 *          Note: This implementation does not support dropping the mascot.
 *
 *      Supports
 *          * Defining the correct sequence of actions the robot has to take in
 *          this case of the autonomous stage.
 *          * Parking the robot at the end.
 */

@Autonomous
public class AutonomousDepotNoMascot extends BaseAutonomous{

    @Override
    void park() {
        movementHandler.spinAutonomous(45, true);
        movementHandler.moveRightAutonomous(10, true);
        armsHandler.extendArmAutonomous(-2000);
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


}

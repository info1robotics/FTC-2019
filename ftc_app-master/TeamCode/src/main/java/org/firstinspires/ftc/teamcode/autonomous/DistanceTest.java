package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.movement.Constants;
import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;

@Autonomous
public class DistanceTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        MoveWithGyro movementHandler = new MoveWithGyro(telemetry, hardwareMap, this);
        movementHandler.stopAll();
        waitForStart();
        int cm = (int) (1000 / Constants.TICKS_PER_CM);
        movementHandler.moveForwardAutonomous(cm, true);
    }
}

package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.vision.WebcamVision;

@Autonomous
public class RecognitionTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        WebcamVision visionHandler = new WebcamVision(hardwareMap, telemetry);
        telemetry.update();
        waitForStart();
        visionHandler.activateRecognition();
        sleep(30000);
    }
}

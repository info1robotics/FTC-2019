package org.firstinspires.ftc.teamcode.oldies;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.vision.WebcamVision;

@Disabled
@Autonomous
public class TestObjectDetectionWebcam extends LinearOpMode {

    @Override
    public void runOpMode() {
        WebcamVision webCamViewer = new WebcamVision(hardwareMap, telemetry);
        telemetry.update();
        waitForStart();

        webCamViewer.activateRecognition();

        while (opModeIsActive()) {
            telemetry.addLine(Integer.toString(webCamViewer.getDetected()));
            telemetry.update();
        }

        webCamViewer.deactivateRecognition();
    }
}

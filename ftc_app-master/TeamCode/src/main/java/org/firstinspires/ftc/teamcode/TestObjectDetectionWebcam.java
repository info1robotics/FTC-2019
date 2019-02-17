package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.vision_smecher.WebcamVision;

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

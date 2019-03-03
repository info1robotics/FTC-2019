package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

/**
 *      Class handling the vision task in autonomous mode.
 *
 *      Supports
 *          * Initializing the webcam from the robot.
 *          * Configuring the classification engine.
 *          * Activating/ Deactivating recognition.
 *          * Getting the closest possible object that is recognised by the classifier.
 */

public class WebcamVision{

    private HardwareMap hardwareMap;
    private Telemetry telemetryLogger;
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private final String VUFORIA_KEY = "AcTB3h7/////AAABma7je7SvYkkqrJT5rzDrtvh/dZ4kzPKDHWZs" +
            "kG12sOplNFyVylw2VzUahIt1kP22rq+oYVqkn+++JewM0W0NXk7KDbcMo0cQAtI8WcgJjYh+jTmoNuokUg2A" +
            "NIpNyrqpKBR9VU5tjQEb5akUNBkyfJiKLXWfxv79vaTGptYiGoK4pn9THnHo2PTWtlE5mpts4NjjdUJJe5u8D" +
            "9g8g0GIaLYDr6qmVuGaZ/ZeM8ZVwIo390U6uc5xJ37SmvZH4DNCALdd+isOsOJ9LYJV5Qvn0kZhO3IAoN0mLk" +
            "fJ2lhqTjHnzba9K8JSTM2LE+fbmdxSsoUj3uEhCWENSqyjZCbLouLfBpRjkVEVor3mhYI+emGF\n";

    public WebcamVision(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetryLogger = telemetry;

        this.setupVuforia();
        this.setupTFObjectDetection();
    }

    public void activateRecognition() {
        if (tfod != null) {
            tfod.activate();
        }
    }

    public void deactivateRecognition() {
        if (tfod != null) {
            tfod.deactivate();
        }
    }

    public int getDetected() {
        if (tfod != null) {
            List<Recognition> recognitions = tfod.getRecognitions();
            if (recognitions == null || recognitions.size() == 0) {
                return ObjectCodes.NO_OBJECT;
            }

            Recognition mainObject = null;
            int maxSize = 0;

            for (Recognition r: recognitions) {
                int size = r.getImageHeight() * r.getImageWidth();
                if (size > maxSize) {
                    maxSize = size;
                    mainObject = r;
                }
            }

            if (mainObject == null) return ObjectCodes.NO_OBJECT;
            if (mainObject.getLabel().equals(LABEL_GOLD_MINERAL)) return ObjectCodes.GOLD_MINERAL;
            if (mainObject.getLabel().equals(LABEL_SILVER_MINERAL)) return ObjectCodes.SILVER_MINERAL;

        }
        return ObjectCodes.NO_OBJECT;
    }

    private void setupVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        telemetryLogger.addData("Vuforia engine", "Set up");
    }

    private void setupTFObjectDetection() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
        telemetryLogger.addData("TF Object Detection", "Set up");
    }

}

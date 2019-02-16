package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@Autonomous
//@Disabled
public class VisionJavaExample extends LinearOpMode{
    MasterVision vision;
    SampleRandomizedPositions goldPosition;
    private DcMotor motorTest;

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;// recommended camera direction
        parameters.vuforiaLicenseKey = "AcTB3h7/////AAABma7je7SvYkkqrJT5rzDrtvh/dZ4kzPKDHWZskG12sOplNFyVylw2VzUahIt1kP22rq+oYVqkn+++JewM0W0NXk7KDbcMo0cQAtI8WcgJjYh+jTmoNuokUg2ANIpNyrqpKBR9VU5tjQEb5akUNBkyfJiKLXWfxv79vaTGptYiGoK4pn9THnHo2PTWtlE5mpts4NjjdUJJe5u8D9g8g0GIaLYDr6qmVuGaZ/ZeM8ZVwIo390U6uc5xJ37SmvZH4DNCALdd+isOsOJ9LYJV5Qvn0kZhO3IAoN0mLkfJ2lhqTjHnzba9K8JSTM2LE+fbmdxSsoUj3uEhCWENSqyjZCbLouLfBpRjkVEVor3mhYI+emGF";
        motorTest=hardwareMap.get(DcMotor.class,"motorTest");
        vision = new MasterVision(parameters, hardwareMap, true, MasterVision.TFLiteAlgorithm.INFER_NONE);
        vision.init();// enables the camera overlay. this will take a couple of seconds
        //motorTest.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //motorTest.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        waitForStart();
        vision.enable();// enables the tracking algorithms. this might also take a little time
        sleep(3000);
        vision.disable();// disables tracking algorithms. this will free up your phone's processing power for other jobs.

        goldPosition = vision.getTfLite().getLastKnownSampleOrder();

            telemetry.addData("goldPosition was", goldPosition);// giving feedback

            /*switch (goldPosition){ // using for things in the autonomous program
                case GOLD:
                    telemetry.addLine("GOLD boi");
                    target(1440);
                    motorTest.setPower(0);
                    break;
                case SILVER:
                    telemetry.addLine("silvester stalone");
                    break;
                case UNKNOWN:
                    telemetry.addLine("ce-i fa asta?");
                    break;
            }*/
            if(goldPosition==SampleRandomizedPositions.GOLD){
                target(4000);
                while(opModeIsActive() && motorTest.isBusy()) {
                    telemetry.addData("encoder-fwd", motorTest.getCurrentPosition() + "  busy=" + motorTest.isBusy());
                    telemetry.update();
                    idle();
                }
                motorTest.setPower(0);
            }
            telemetry.update();


        vision.shutdown();
    }
    private void target(int inp){
        motorTest.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorTest.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorTest.setTargetPosition(inp);
        motorTest.setPower(0.8);
    }

}

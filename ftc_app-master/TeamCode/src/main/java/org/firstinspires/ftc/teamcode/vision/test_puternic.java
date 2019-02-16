package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@Autonomous
public class test_puternic extends LinearOpMode {
        MasterVision vision;
        SampleRandomizedPositions goldPosition;
        testputernichw robot=new testputernichw();
        @Override
        public void runOpMode() throws InterruptedException{
            robot.init(hardwareMap);
            VuforiaLocalizer.Parameters parameters=new VuforiaLocalizer.Parameters();
            parameters.cameraDirection=VuforiaLocalizer.CameraDirection.BACK;
            parameters.vuforiaLicenseKey= "AcTB3h7/////AAABma7je7SvYkkqrJT5rzDrtvh/dZ4kzPKDHWZskG12sOplNFyVylw2VzUahIt1kP22rq+oYVqkn+++JewM0W0NXk7KDbcMo0cQAtI8WcgJjYh+jTmoNuokUg2ANIpNyrqpKBR9VU5tjQEb5akUNBkyfJiKLXWfxv79vaTGptYiGoK4pn9THnHo2PTWtlE5mpts4NjjdUJJe5u8D9g8g0GIaLYDr6qmVuGaZ/ZeM8ZVwIo390U6uc5xJ37SmvZH4DNCALdd+isOsOJ9LYJV5Qvn0kZhO3IAoN0mLkfJ2lhqTjHnzba9K8JSTM2LE+fbmdxSsoUj3uEhCWENSqyjZCbLouLfBpRjkVEVor3mhYI+emGF";
            vision = new MasterVision(parameters, hardwareMap, false, MasterVision.TFLiteAlgorithm.INFER_NONE);

            vision.init();
            waitForStart();
            vision.enable();
            sleep(2000);
            vision.disable();
            goldPosition=vision.getTfLite().getLastKnownSampleOrder();
            if(goldPosition==SampleRandomizedPositions.GOLD){
                robot.setTarget(2000);
                while(opModeIsActive() && robot.motorTest.isBusy())idle();
                robot.setMotorToZero();
            }
            else if(goldPosition==SampleRandomizedPositions.SILVER){
                robot.motorTest.setPower(0.2);
                sleep(2000);
                robot.motorTest.setPower(0);
            }
            vision.shutdown();
        }

}

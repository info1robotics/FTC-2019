package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class testputernichw {
    public DcMotor motorTest=null;
    int MTT=0;
    HardwareMap hwMap=null;

    public void init(HardwareMap ahwMap){
        hwMap=ahwMap;
        motorTest=hwMap.get(DcMotor.class,"motorTest");
        motorTest.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorTest.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void setTarget(int newTarget){
        motorTest.setTargetPosition(MTT+newTarget);
        MTT+=newTarget;
        motorTest.setPower(0.5);
    }
    public void setMotorToZero(){

    }
}


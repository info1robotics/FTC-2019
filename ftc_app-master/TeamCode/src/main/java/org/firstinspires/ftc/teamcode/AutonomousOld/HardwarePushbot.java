package org.firstinspires.ftc.teamcode.AutonomousOld;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwarePushbot {
    DcMotor motorFL=null;
    DcMotor motorFR=null;
    DcMotor motorBL=null;
    DcMotor motorBR=null;
    DcMotor motorArm=null;
    DcMotor motorMinRaise=null;
    DcMotor motorMinEnlarge=null;
    private int FLT=0;
    private int FRT=0;
    private int BLT=0;
    private int BRT=0;
    private int ARMT=0;
    private final double SPEEDCOEF=0.5;

    HardwareMap hwMap=null;

    HardwarePushbot(){

    }
    public void init(HardwareMap ahwMap){
        hwMap=ahwMap;
        motorFL=hwMap.get(DcMotor.class,"motorFL");
        motorFR=hwMap.get(DcMotor.class,"motorFR");
        motorBL=hwMap.get(DcMotor.class,"motorBL");
        motorBR=hwMap.get(DcMotor.class,"motorBR");
        motorArm=hwMap.get(DcMotor.class,"motorArm");
        motorMinRaise=hwMap.get(DcMotor.class,"motorMinRaise");
        motorMinEnlarge=hwMap.get(DcMotor.class,"motorMinEnlarge");
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorMinRaise.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorMinEnlarge.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorMinRaise.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorMinEnlarge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void setTarget(int newFLT,int newFRT,int newBLT,int newBRT){
        motorFL.setTargetPosition(BLT+newBLT);
        motorFR.setTargetPosition(FLT+newFLT);
        motorBL.setTargetPosition(BRT+newBRT);
        motorBR.setTargetPosition(FRT+newFRT);
        FLT+=newFLT;
        FRT+=newFRT;
        BLT+=newBLT;
        BRT+=newBRT;
        motorFL.setPower(SPEEDCOEF);
        motorFR.setPower(SPEEDCOEF);
        motorBL.setPower(SPEEDCOEF);
        motorBR.setPower(SPEEDCOEF);
    }
    public void setMotorsToZero(){
        motorFL.setPower(0.0);
        motorFR.setPower(0.0);
        motorBL.setPower(0.0);
        motorBR.setPower(0.0);
    }
    public void moveArm(int newARMT){
        motorArm.setTargetPosition(ARMT+newARMT);
        ARMT+=newARMT;
        motorArm.setPower(0.3);
    }
    public void ArmZero(){
        motorArm.setPower(0);
    }
}

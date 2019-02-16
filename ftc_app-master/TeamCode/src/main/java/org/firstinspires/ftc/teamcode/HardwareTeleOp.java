package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareTeleOp {
    DcMotor motorFL=null;
    DcMotor motorFR=null;
    DcMotor motorBL=null;
    DcMotor motorBR=null;
    DcMotor motorArm=null;
    DcMotor motorMinRaise=null;
    DcMotor motorMinEnlarge=null;
    BNO055IMU imu;
    private int FLT=0;
    private int FRT=0;
    private int BLT=0;
    private int BRT=0;
    private double rotationPower=0.3;
    private double movePower=0.75;
    HardwareMap hwMap=null;
    //constructor
    public HardwareTeleOp(){

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
        imu=hwMap.get(BNO055IMU.class,"imu");
        motorMinEnlarge.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorMinEnlarge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void setDirection(double FLD, double FRD, double BLD, double BRD){
        motorFL.setPower(FLD*movePower);
        motorFR.setPower(FRD*movePower);
        motorBL.setPower(BLD*movePower);
        motorBR.setPower(BRD*movePower);
    }

    public void setEnlarge(){
        motorMinEnlarge.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorMinEnlarge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorMinEnlarge.setTargetPosition(400);
        motorMinEnlarge.setPower(1);
    }
    public void setRetract(){
        motorMinEnlarge.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorMinEnlarge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorMinEnlarge.setTargetPosition(-400);
        motorMinEnlarge.setPower(1);
    }
    public void varRotation(double power){
        motorFL.setPower(power);
        motorFR.setPower(power);
        motorBL.setPower(power);
        motorBR.setPower(power);
    }
}

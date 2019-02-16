package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous
public class testgyroandencoders extends LinearOpMode {
    DcMotor motorFL;
    DcMotor motorFR;
    DcMotor motorBL;
    DcMotor motorBR;
    BNO055IMU imu;
    Orientation lastAngles= new Orientation();
    double globalAngle, correction;
    double power=0.5;
    @Override
    public void runOpMode(){
        motorFL=hardwareMap.get(DcMotor.class,"motorFL");
        motorFR=hardwareMap.get(DcMotor.class,"motorFR");
        motorBL=hardwareMap.get(DcMotor.class,"motorBL");
        motorBR=hardwareMap.get(DcMotor.class,"motorBR");
        imu=hardwareMap.get(BNO055IMU.class,"imu");
        BNO055IMU.Parameters parameters=new BNO055IMU.Parameters();
        parameters.mode=BNO055IMU.SensorMode.IMU;
        parameters.angleUnit=BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit=BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled=false;
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        imu.initialize(parameters);
        resetAngle();
        telemetry.addData("Mode", "calibrating...");
        telemetry.update();
        waitForStart();
        telemetry.addData("Mode","running");
        telemetry.update();
        motorFL.setTargetPosition(-5000);
        motorFR.setTargetPosition(5000);
        motorBL.setTargetPosition(-5000);
        motorBR.setTargetPosition(5000);
        motorFL.setPower(power);
        motorFR.setPower(power);
        motorBL.setPower(power);
        motorBR.setPower(power);
        while (opModeIsActive() && motorFL.isBusy()){
            correction=getAngle();
            if(correction>0){
                motorFL.setPower(power+0.05);
                motorFR.setPower(power);
                motorBL.setPower(power+0.05);
                motorBR.setPower(power);
            }
            else if(correction<0){
                motorFL.setPower(power);
                motorFR.setPower(power+0.05);
                motorBL.setPower(power);
                motorBR.setPower(power+0.05);
            }
            else {
                motorFL.setPower(power);
                motorFR.setPower(power);
                motorBL.setPower(power);
                motorBR.setPower(power);
            }
            telemetry.addData("correction",correction);
            telemetry.addData("encoder-fwd", motorFL.getCurrentPosition() + "  busy=" + motorFL.isBusy());
            telemetry.addData("encoder-fwd", motorFR.getCurrentPosition() + "  busy=" + motorFR.isBusy());
            telemetry.addData("encoder-fwd", motorBL.getCurrentPosition() + "  busy=" + motorBL.isBusy());
            telemetry.addData("encoder-fwd", motorBR.getCurrentPosition() + "  busy=" + motorBR.isBusy());
            telemetry.update();
            idle();
        }
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
        telemetry.addData("correction",correction);
        telemetry.update();
        sleep(3000);

        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        correction=getAngle();
        if(correction>0) {
            while (correction > 3) {
                motorFL.setPower(-0.1);
                motorFR.setPower(-0.1);
                motorBL.setPower(-0.1);
                motorBR.setPower(-0.1);
                correction = getAngle();
                telemetry.addData("corection",correction);
                telemetry.update();
            }
            motorFL.setPower(0.0);
            motorFR.setPower(0.0);
            motorBL.setPower(0.0);
            motorBR.setPower(0.0);
        }
        else if(correction<0){
            while(correction<-3){
                motorFL.setPower(0.1);
                motorFR.setPower(0.1);
                motorBL.setPower(0.1);
                motorBR.setPower(0.1);
                correction=getAngle();
                telemetry.addData("correction",correction);
                telemetry.update();
            }
            motorFL.setPower(0.0);
            motorFR.setPower(0.0);
            motorBL.setPower(0.0);
            motorBR.setPower(0.0);
        }
        correction=getAngle();
        telemetry.addData("correction",correction);
        telemetry.update();
        sleep(4000);

    }
    private double getAngle()
    {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;
        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;
        globalAngle += deltaAngle;
        lastAngles = angles;
        return globalAngle;
    }
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        globalAngle = 0;
    }
}

package org.firstinspires.ftc.teamcode;

import android.graphics.drawable.GradientDrawable;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous
public class gyrotestauto extends LinearOpMode {
private DcMotor motorFL;
private DcMotor motorFR;
private DcMotor motorBL;
private DcMotor motorBR;
BNO055IMU imu;
Orientation lastAngles= new Orientation();
double globalAngle, correction;
double power=0.7;
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
        imu.initialize(parameters);
        telemetry.addData("Mode", "calibrating...");
        telemetry.update();
        waitForStart();
        telemetry.addData("Mode","running");
        telemetry.update();
        while(opModeIsActive()){
            correction=checkDirection();
            telemetry.addData("1 imu heading", lastAngles.firstAngle);
            telemetry.addData("2 global heading", globalAngle);
            telemetry.addData("3 correction", correction);
            telemetry.update();
            motorFL.setPower(-power-correction);
            motorBL.setPower(-power-correction);
            motorFR.setPower(power+correction);
            motorBR.setPower(power+correction);
        }
    }
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        globalAngle = 0;
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
    private double checkDirection()
    {
        double correction, angle, gain = 0.25;
        angle = getAngle();
        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.
        correction = correction * gain;
        return correction;
    }
}

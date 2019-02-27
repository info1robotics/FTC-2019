package org.firstinspires.ftc.teamcode.arms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmsMotors {

    public DcMotor climbMotor;
    public DcMotor collectMotorMove1;
    public DcMotor collectMotorMove2;
    public DcMotor collectMotorExtend;

    public ArmsMotors(HardwareMap hardwareMap, Telemetry telemetry) {
        climbMotor = hardwareMap.get(DcMotor.class, "climbMotor");
        collectMotorExtend = hardwareMap.get(DcMotor.class, "collectMotorExtend");
        collectMotorMove1 = hardwareMap.get(DcMotor.class, "collectMotorMove1");
        collectMotorMove2 = hardwareMap.get(DcMotor.class, "collectMotorMove2");

        telemetry.addData("Arms motors", "Set up.");
    }

    public void setMode(DcMotor.RunMode mode) {
        this.climbMotor.setMode(mode);
        this.collectMotorMove1.setMode(mode);
        this.collectMotorMove2.setMode(mode);
        this.collectMotorExtend.setMode(mode);
    }

}

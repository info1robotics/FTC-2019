package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MovementMotors {

    private DcMotor FR;
    private DcMotor FL;
    private DcMotor BR;
    private DcMotor BL;
    private Telemetry telemetryLogger;

    private final Power WEIGHT_CORRECTIONS = new Power(1, 1, 1, 1);

    public MovementMotors(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetryLogger = telemetry;
        telemetry.addData("Movement movementMotors", "Setting up.");
        telemetry.update();

        this.FL = hardwareMap.get(DcMotor.class,"motorFL");
        this.FR = hardwareMap.get(DcMotor.class,"motorFR");
        this.BL = hardwareMap.get(DcMotor.class,"motorBL");
        this.BR = hardwareMap.get(DcMotor.class,"motorBR");

        telemetry.addData("Movement movementMotors", "Set up.");
    }

    public void setMode(DcMotor.RunMode mode) {
        this.BR.setMode(mode);
        this.BL.setMode(mode);
        this.FL.setMode(mode);
        this.FR.setMode(mode);
    }

    public void setTargetPosition(int frTarget, int flTarget, int brTarget, int blTarget) {
        this.FR.setTargetPosition(frTarget);
        this.FL.setTargetPosition(flTarget);
        this.BR.setTargetPosition(brTarget);
        this.BL.setTargetPosition(blTarget);
    }

    public void setPower(Power powers) {
        powers = powers.multiply(this.WEIGHT_CORRECTIONS);
        this.FR.setPower(powers.FR);
        this.FL.setPower(powers.FL);
        this.BR.setPower(powers.BR);
        this.BL.setPower(powers.BL);
    }

    public boolean allBusy() {
        return (this.FR.isBusy() && this.FL.isBusy() && this.BR.isBusy() && this.BL.isBusy());
    }

}

package org.firstinspires.ftc.teamcode.movement;

/**
 *      Class representing the power distribution over the four motors controlling the robot's
 *      movement.
 *
 *      Supports:
 *          * multiplying two powers - used to apply different spinning directions and/ or
 *          corrections applied to the motors.
 */
public class Power {

    public double FR = 0.0;
    public double FL = 0.0;
    public double BR = 0.0;
    public double BL = 0.0;

    public Power() {
        // Do nothing - all are initialised to 0;
    }

    public Power(double power) {
        this.FL = this.FR = this.BR = this.BL = power;
    }

    public Power (Speed power) {
        this.FL = this.FR = this.BR = this.BL = power.getPower();
    }

    public Power(double fr, double fl, double br, double bl) {
        this.FR = fr;
        this.FL = fl;
        this.BR = br;
        this.BL = bl;
    }

    public Power multiply(Power otherPower) {
        return new Power(
                this.FR * otherPower.FR,
                this.FL * otherPower.FL,
                this.BR * otherPower.BR,
                this.BL * otherPower.BL
        );
    }

}
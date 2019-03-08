package org.firstinspires.ftc.teamcode.movement;

public class ConstantSpeed extends Speed{

    private double power;

    public ConstantSpeed(double power) {
        this.power = power;
    }

    @Override
    public double getPower() {
        return this.power;
    }

    @Override
    public void setDirection(int directionCode) {

    }

    @Override
    public void setNewTargetPower(double power) {
        this.power = power;
    }
}

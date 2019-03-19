package org.firstinspires.ftc.teamcode.movement;

/**
 *      Class representing motor powers which increase asympthotically to a maximum power.
 *
 *      Supports
 *          * setting a new direction of movement.
 *          * getting the current power value, which gets updated over time.
 *          * setting a new target power.
 */


public class AsympthoticalPower {

    private int direction = -1;
    public int count = 0;
    private double targetPower;
    private double BASE_POWER = 0;
    private double actualPower;
    private int RESET_AFTER = 50;

    public AsympthoticalPower(double target) {
        this.setNewTargetPower(target);
    }

    public void setDirection(int directionCode) throws IllegalArgumentException{
        if (directionCode == this.direction) return;
        if (directionCode != DirectionCodes.FORWARD && directionCode != DirectionCodes.BACKWARD &&
                directionCode != DirectionCodes.RIGHT && directionCode != DirectionCodes.LEFT) {
            throw new IllegalArgumentException();
        }
        this.direction = directionCode;
        this.actualPower = this.BASE_POWER;
        this.count = 0;
    }

    public double getPower() {
        if (direction == -1) return 0;
        if (this.count % this.RESET_AFTER == 0)
            this.actualPower = (this.targetPower + this.actualPower) / 2;
        count += 1;
        return actualPower;
    }

    public void setNewTargetPower(double power) throws IllegalArgumentException{
        if (power < 0 || power > 1) throw new IllegalArgumentException();
        this.targetPower = power;
    }

}

package org.firstinspires.ftc.teamcode.movement;

public class Interval {

    private double left = 0.0;
    private double right = 0.0;

    public Interval(double left, double right) {
        this.left = left;
        this.right = right;
    }

    public boolean contains(double value) {
        return (value >= this.left && value <= this.right);
    }

    public String toString() {
        return String.valueOf(left) + ' ' + String.valueOf(right);
    }

}

package org.firstinspires.ftc.teamcode.movement.deprecated;

@Deprecated
public class Direction {

    private double x;
    private double y;

    private final double ALPHA = 2 * Double.MIN_VALUE;

    public Direction(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean is_close(Direction otherDirection) {
        return (Math.abs(this.x - otherDirection.x) <= this.ALPHA) &&
                (Math.abs(this.y - otherDirection.y) <= this.ALPHA);
    }
}

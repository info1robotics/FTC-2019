package org.firstinspires.ftc.teamcode.movement;

import java.util.ArrayList;

/**
 *      List of angles used when applying corrections.
 *
 *      Supports:
 *          * adding new angles to the list.
 *          * checking if the past N angles stayed the same - used to forcibly exit the correction
 *          loop in order to avoid an infinite loop.
 */

public class AnglesList extends ArrayList {

    private final int SIZE_LIMIT = 10;
    private final double ERROR = 0.001;

    public AnglesList() {
        super();
    }

    public boolean add(double d) {
        if (this.size() == this.SIZE_LIMIT) {
            this.remove(0);
        }
        return super.add(d);
    }

    public boolean stayedConstant() {
        double firstAngle = (double) this.get(0);

        if (this.size() < 2) return false;

        for (int i=1; i < Math.min(this.SIZE_LIMIT, this.size()); i++) {
            if (Math.abs(firstAngle - (double) this.get(i)) > this.ERROR) return false;
        }

        return true;
    }

}

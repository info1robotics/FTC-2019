package org.firstinspires.ftc.teamcode.arms.constants;

import org.firstinspires.ftc.teamcode.utils.ServoPosition;

/**
 *      Class holding constants related to servo positions.
 */

public class ServoPositions {

    public static final ServoPosition MINERAL_HIT = new ServoPosition(0.1, 0.75);
    public static final ServoPosition MASCOT_DROP = new ServoPosition(0, 0);
    /**
     *      MINERAL_BOX_TILT.start (primul element) = pozitia de inceput
     *      MINERAL_BOX_TILT.end (al doiles) = pozitita de devarsare
     *      MINERAL_BOX_45 = pozitia din tranzit
     */
    public static final ServoPosition MINERAL_BOX_TILT = new ServoPosition(0.75, 0.80);
    public static final double MINERAL_BOX_45 = 0.525;

}

package org.firstinspires.ftc.teamcode.movement;

/**
 *      Class storing signs corresponding to the different directions the robot can move in.
 */
public class Signs {

    static final Power FORWARD = new Power(1, -1, 1, -1);
    static final Power BACKWARD = new Power(-1, 1, -1, 1);
    static final Power RIGHT = new Power(-1, -1, 1, 1);
    static final Power LEFT = new Power(1, 1, -1, -1);
    static final Power ROTATE_LEFT = new Power(1);
    static final Power ROTATE_RIGHT = new Power(-1);
}

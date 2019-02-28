package org.firstinspires.ftc.teamcode.arms;

import org.firstinspires.ftc.teamcode.arms.constants.MotorTicks;

public class MineralArmPowerInTransition {

    private final double DECAY_FACTOR = 0.5;
    private final int START_DECAY_AFTER = MotorTicks.MINERAL_ARM_START_DECAY_AFTER;
    private final int DECAY_STEP = 50;
    private final double START_POWER = 1.0;
    private double currentPower = 1.0;
    private int currentTicks = 0;

    public void reset() {
        this.currentPower = START_POWER;
        this.currentTicks = 0;
    }

    private void computeNewPower() {
        if (currentTicks < START_DECAY_AFTER) return;
        if (currentTicks % DECAY_STEP == 0) currentPower *= DECAY_FACTOR;
    }

    public double getPower(int ticks) {
        currentTicks = ticks;
        computeNewPower();
        return currentPower;
    }

}

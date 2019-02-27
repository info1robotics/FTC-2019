package org.firstinspires.ftc.teamcode.teleop.buttons;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.arms.ArmsAction;
import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;

public abstract class ButtonAction {

    public abstract void doStuff(ArmsAction armsHandler, MoveWithGyro moveHandler, Gamepad gamepad);

}

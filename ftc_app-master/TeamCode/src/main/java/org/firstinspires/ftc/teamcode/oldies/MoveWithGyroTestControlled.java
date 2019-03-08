package org.firstinspires.ftc.teamcode.oldies;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;
import org.firstinspires.ftc.teamcode.movement.Power;

@Disabled
public class MoveWithGyroTestControlled extends LinearOpMode {
    @Override
    public void runOpMode() {
        MoveWithGyro movement = new MoveWithGyro(telemetry, hardwareMap, this, true);
        boolean positionCorrect=true;
        movement.resetAngle();
        waitForStart();

        while (opModeIsActive()) {
            //telemetry.addData("Want correction", positionCorrect);
            //telemetry.update();
            if (gamepad1.dpad_right) {
                while (gamepad1.dpad_right) movement.moveRight();
                if(positionCorrect)movement.correctPosition();
                movement.stopAll();
            }

            if (gamepad1.dpad_left) {
                while (gamepad1.dpad_left) movement.moveLeft();
                if(positionCorrect)movement.correctPosition();
                movement.stopAll();
            }

            if (gamepad1.dpad_up) {
                while (gamepad1.dpad_up) movement.moveForward();
                if(positionCorrect)movement.correctPosition();
                movement.stopAll();
            }

            if (gamepad1.dpad_down) {
                while (gamepad1.dpad_down) movement.moveBack();
                if(positionCorrect)movement.correctPosition();
                movement.stopAll();
            }

            if(gamepad1.x){
                movement.resetAngle();
            }

            if(gamepad1.a){
                positionCorrect=true;
            }

            if(gamepad1.b){
                positionCorrect=false;
            }

            if (gamepad1.y) {
                movement.spinAutonomous(-45, true);
            }

        }
    }

}

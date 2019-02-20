package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.Math;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.movement.Movement;
import org.firstinspires.ftc.teamcode.movement.Power;

@TeleOp
public class tryHard extends LinearOpMode {
    HardwareTeleOp robot=new HardwareTeleOp();
    Orientation angles;
    @Override
    public void runOpMode(){

        robot.init(hardwareMap);
        BNO055IMU.Parameters parameters=new BNO055IMU.Parameters();
        parameters.mode=BNO055IMU.SensorMode.IMU;
        parameters.angleUnit=BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit=BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled=false;
        robot.imu.initialize(parameters);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        Movement.set_functions();
        robot.motorArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();

        while(opModeIsActive()){

            double xPower=gamepad1.left_stick_x;
            double yPower=gamepad1.left_stick_y;
            double armPower=gamepad1.right_stick_y;
            double rotationRightPower=gamepad1.right_trigger;
            double rotationLeftPower=gamepad1.left_trigger;
            //Power powers = Movement.move(xPower, -1.0 * yPower, telemetry);
            //robot.motorFL.setPower(powers.FL);
            //robot.motorFR.setPower(powers.FR);
            //robot.motorBL.setPower(powers.BL);
            //robot.motorBR.setPower(powers.BR);
            robot.motorArm.setPower(armPower);
            //robot.motorMinRaise.setPower(MinRaisePower/3);
            telemetry.addData("ticks",robot.motorArm.getCurrentPosition());
            telemetry.update();
            if(gamepad1.dpad_up){
                while(gamepad1.dpad_up)
                robot.setDirection(-1,1,-1,1);
                robot.varRotation(0);
            }
            if(gamepad1.dpad_down){
                while(gamepad1.dpad_down)
                robot.setDirection(1,-1,1,-1);
                robot.varRotation(0);
            }
            if(gamepad1.dpad_left){
                while(gamepad1.dpad_left)
                robot.setDirection(1,1,-1,-1);
                robot.varRotation(0);
            }
            if(gamepad1.dpad_right){
                while(gamepad1.dpad_right)
                robot.setDirection(-1,-1,1,1);
                robot.varRotation(0);
            }
            if(gamepad1.x){
                while (gamepad1.x)
                robot.setDirection(1,1,1,1);
                robot.varRotation(0);
            }
            if(gamepad1.b){
                while(gamepad1.b)
                robot.setDirection(-1,-1,-1,-1);
                robot.varRotation(0);
            }
            if(gamepad1.y){
                robot.setRetract();
                while(opModeIsActive() && robot.motorMinEnlarge.isBusy()){
                    telemetry.addData("encoder-fwd", robot.motorMinEnlarge.getCurrentPosition() + "  busy=" + robot.motorMinEnlarge.isBusy());
                    telemetry.update();
                    idle();
                }
                robot.motorMinEnlarge.setPower(0);
            }
            if(gamepad1.a){
                robot.setEnlarge();
                while(opModeIsActive() && robot.motorMinEnlarge.isBusy()){
                    telemetry.addData("encoder-fwd", robot.motorMinEnlarge.getCurrentPosition() + "  busy=" + robot.motorMinEnlarge.isBusy());
                    telemetry.update();
                    idle();
                }
                robot.motorMinEnlarge.setPower(0);
            }
            if(gamepad1.left_bumper){
                while(gamepad1.left_bumper)
                robot.motorMinRaise.setPower(0.5);
            robot.motorMinRaise.setPower(0);
            }
            if(gamepad1.right_bumper){
                while (gamepad1.right_bumper)
                robot.motorMinRaise.setPower(-0.5);
            robot.motorMinRaise.setPower(0);
            }
            if(rotationLeftPower>0)robot.varRotation(rotationLeftPower);
            if(rotationRightPower>0)robot.varRotation(rotationRightPower*(-1));
        }
    }


}

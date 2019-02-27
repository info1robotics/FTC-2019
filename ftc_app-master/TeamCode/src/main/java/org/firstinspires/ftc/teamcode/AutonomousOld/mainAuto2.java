package org.firstinspires.ftc.teamcode.AutonomousOld;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Disabled
@Autonomous
public class mainAuto2 extends LinearOpMode {
    HardwarePushbot robot=new HardwarePushbot();
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        telemetry.addData("Status","Initialized");
        telemetry.update();
        waitForStart();

        robot.setTarget(1000,1000,-1000,-1000);
        active();
        robot.setMotorsToZero();

        robot.setTarget(2900,-2900,2900,-2900);
        active();
        robot.setMotorsToZero();

        robot.setTarget(687,687,0,687);
        active();
        robot.setMotorsToZero();

        robot.setTarget(-2760,2760,-2760,2760);
        active();
        robot.setMotorsToZero();

        robot.motorMinEnlarge.setTargetPosition(200);
        robot.motorMinEnlarge.setPower(1);
        while(opModeIsActive() && robot.motorMinEnlarge.isBusy()){
            telemetry.addData("encoder-fwd", robot.motorMinEnlarge.getCurrentPosition() + "  busy=" + robot.motorMinEnlarge.isBusy());
            telemetry.update();
            idle();
        }
        robot.motorMinEnlarge.setPower(0);

        robot.setTarget(3100,-3100,3100,-3100);
        active();
        robot.setMotorsToZero();

        robot.motorMinRaise.setTargetPosition(-1500);
        robot.motorMinRaise.setPower(1);

        while(opModeIsActive() && robot.motorMinRaise.isBusy()){
            telemetry.addData("encoder-fwd", robot.motorMinRaise.getCurrentPosition() + "  busy=" + robot.motorMinRaise.isBusy());
            telemetry.update();
            idle();
        }
        robot.motorMinRaise.setPower(0);
    }
    private void active(){
        while(opModeIsActive() && robot.motorFL.isBusy()){
            telemetry.addData("encoder-fwd", robot.motorFL.getCurrentPosition() + "  busy=" + robot.motorFL.isBusy());
            telemetry.addData("encoder-fwd", robot.motorFR.getCurrentPosition() + "  busy=" + robot.motorFR.isBusy());
            telemetry.addData("encoder-fwd", robot.motorBL.getCurrentPosition() + "  busy=" + robot.motorBL.isBusy());
            telemetry.addData("encoder-fwd", robot.motorBR.getCurrentPosition() + "  busy=" + robot.motorBR.isBusy());
            telemetry.update();
            idle();
        }
    }
}

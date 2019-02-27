package org.firstinspires.ftc.teamcode.AutonomousOld;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class HWPTest extends LinearOpMode {
    HardwarePushbot robot=new HardwarePushbot();
    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        telemetry.addData("Status","Initialized");
        telemetry.update();
        waitForStart();
        robot.setTarget(1000,1000,-1000,-1000);
        robot.setMotorsToZero();
        robot.setTarget(2900,-2900,2900,-2900);
        robot.setMotorsToZero();
        robot.setTarget(687,687,0,687);
        robot.setMotorsToZero();
        robot.setTarget(-2760,2760,-2760,2760);
        robot.setMotorsToZero();
        robot.setTarget(3100,-3100,3100,-3100);
    }
}

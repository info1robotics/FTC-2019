package org.firstinspires.ftc.teamcode.oldies;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.movement.MovementMotors;
import org.firstinspires.ftc.teamcode.movement.MoveWithGyro;
import org.firstinspires.ftc.teamcode.movement.Power;

@Disabled
@Autonomous
public class testgyroandencoders extends LinearOpMode {
    DcMotor motorFL;
    DcMotor motorFR;
    DcMotor motorBL;
    DcMotor motorBR;
    BNO055IMU imu;
    Orientation lastAngles= new Orientation();
    double globalAngle, correction;
    double power=0.7;
    MovementMotors movementMotors;
    @Override
    public void runOpMode(){
        MoveWithGyro movement = new MoveWithGyro(telemetry, hardwareMap, this, false);
        movement.setMovementPower(power);
        imu = movement.imu;
        waitForStart();
        telemetry.addData("Mode","running");
        telemetry.update();
        resetAngle();
        movementMotors = movement.movementMotors;
        movement.moveForwardAutonomous(1000, false);
        movementMotors.setPower(new Power());
        movementMotors.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        movement.correctPosition();

        sleep(1000);

        movement.moveRightAutonomous(1000, false);
        movementMotors.setPower(new Power());
        movementMotors.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        movement.correctPosition();

        sleep(1000);

        movement.moveLeftAutonomous(1000, false);
        movementMotors.setPower(new Power());
        movementMotors.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        movement.correctPosition();

        sleep(1000);

        movement.moveBackAutonomous(1000, false);
        movementMotors.setPower(new Power());
        movementMotors.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        movement.correctPosition();

    }
    private double getAngle()
    {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;
        while (deltaAngle < -180) deltaAngle += 360;
        while (deltaAngle > 180) deltaAngle -= 360;
        return deltaAngle;
    }
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        globalAngle = 0;
    }
}

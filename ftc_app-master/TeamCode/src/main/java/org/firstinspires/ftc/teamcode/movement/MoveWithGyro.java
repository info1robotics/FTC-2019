package org.firstinspires.ftc.teamcode.movement;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MoveWithGyro {

    public BNO055IMU imu;
    private Orientation lastAngle = new Orientation();
    private double movementPower;
    private double correctionPower = .1;
    private Telemetry telemetryLogger;
    public Motors motors;
    private LinearOpMode opMode;

    public MoveWithGyro(double power, Telemetry telemetry, HardwareMap hardwareMap,
                        LinearOpMode opMode) {
        this.setMovementPower(power);
        this.telemetryLogger = telemetry;
        this.motors = new Motors(hardwareMap, telemetry);
        this.opMode = opMode;

        // Initializing gyro setup
        telemetry.addData("Gyroscope", "Calibrating");
        telemetry.update();

        imu=hardwareMap.get(BNO055IMU.class,"imu");
        BNO055IMU.Parameters parameters=new BNO055IMU.Parameters();
        parameters.mode=BNO055IMU.SensorMode.IMU;
        parameters.angleUnit=BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit=BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled=false;
        imu.initialize(parameters);

        this.telemetryLogger.addData("Gyroscope", "Calibrated");
        this.telemetryLogger.update();
    }

    public void setMovementPower(double power) throws IllegalArgumentException {
        if (power < -1 || power > 1) throw new IllegalArgumentException();
        this.movementPower = power;
    }

    private void move(Power signs) {
        double angleDiff = this.getAngleDiff(this.lastAngle);
        Power power = new Power(this.movementPower);
        if (angleDiff < 0) {
            power.FR += .05;
            power.BR += .05;
        } else if (angleDiff > 0) {
            power.FL += .05;
            power.BL += .05;
        }

        motors.setPower(power.multiply(signs));
    }

    public void moveForward() {
        this.move(Signs.FORWARD);
    }

    public void moveBack() {
        this.move(Signs.BACKWARD);
    }

    public void moveRight() {
        this.move(Signs.RIGHT);
    }

    public void moveLeft() {
        this.move(Signs.LEFT);
    }

    private void moveAutonomous(int ticks, Power signs) {
        motors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motors.setTargetPosition(
                (int) signs.FR * ticks, (int) signs.FL * ticks,
                (int) signs.BR * ticks,(int) signs.BL *  ticks
        );

        Power power = new Power(movementPower);
        motors.setPower(power.multiply(signs));

        this.resetAngle();

        while (this.opMode.opModeIsActive() && this.motors.allBusy()) {
            move(new Power(1.0));
        }
    }

    /** Move the robot forward for a fixed number of ticks.
     *
     * @param ticks Desired number of motor ticks to move for.
     * @return The resulting powers for the 4 motors.
     */
    public void moveForwardAutonomous(int ticks) {
        this.moveAutonomous(ticks, Signs.FORWARD);
    }

    /** Move the robot backward for a fixed number of ticks.
     *
     * @param ticks Desired number of motor ticks to move for.
     * @return The resulting powers for the 4 motors.
     */
    public void moveBackAutonomous(int ticks) {
        this.moveAutonomous(ticks, Signs.BACKWARD);
    }

    /** Move the robot to the right for a fixed number of ticks.
     *
     * @param ticks Desired number of motor ticks to move for.
     * @return The resulting powers for the 4 motors.
     */
    public void moveRightAutonomous(int ticks) {
        this.moveAutonomous(ticks, Signs.RIGHT);
    }

    /** Move the robot to the left for a fixed number of ticks.
     *
     * @param ticks Desired number of motor ticks to move for.
     * @return The resulting powers for the 4 motors.
     */
    public void moveLeftAutonomous(int ticks) {
        this.moveAutonomous(ticks, Signs.LEFT);
    }

    private double getAngleDiff(Orientation angle) {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double deltaAngle = angles.firstAngle - angle.firstAngle;
        this.telemetryLogger.addData("Angle", deltaAngle);
        this.telemetryLogger.update();
        while (deltaAngle < -180) deltaAngle += 360;
        while (deltaAngle > 180) deltaAngle -= 360;
        return deltaAngle;
    }

    private void resetAngle()
    {
        this.lastAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX,
                AngleUnit.DEGREES);
    }

    public void correctPosition() {
        double deltaAngle = getAngleDiff(this.lastAngle);

        while (Math.abs(deltaAngle) > 1) {
            if (deltaAngle < 0) {
                // Turn left
                motors.setPower(new Power(this.correctionPower));
            } else {
                // Turn right
                motors.setPower(new Power(-1.0 * this.correctionPower));
            }
            deltaAngle = getAngleDiff(this.lastAngle);
        }

        this.motors.setPower(new Power());
    }

}

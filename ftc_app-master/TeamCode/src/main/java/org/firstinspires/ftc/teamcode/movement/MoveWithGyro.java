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
    private AsympthoticalPower movementPower;
    private double correctionPower = .2;
    private double rotationPower = .2;
    private Telemetry telemetryLogger;
    public Motors motors;
    private LinearOpMode opMode;

    public MoveWithGyro(double power, Telemetry telemetry, HardwareMap hardwareMap,
                        LinearOpMode opMode) {
        this.movementPower = new AsympthoticalPower(power);
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
        this.movementPower.setNewTargetPower(power);
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
        this.movementPower.setDirection(DirectionCodes.FORWARD);
        this.move(Signs.FORWARD);
    }

    public void moveBack() {
        this.movementPower.setDirection(DirectionCodes.BACKWARD);
        this.move(Signs.BACKWARD);
    }

    public void moveRight() {
        this.movementPower.setDirection(DirectionCodes.RIGHT);
        this.move(Signs.RIGHT);
    }

    public void moveLeft() {
        this.movementPower.setDirection(DirectionCodes.LEFT);
        this.move(Signs.LEFT);
    }

    private void moveAutonomous(int ticks, Power signs) {
        motors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motors.setTargetPosition(
                (int) signs.FR * ticks, (int) signs.FL * ticks,
                (int) signs.BR * ticks,(int) signs.BL *  ticks
        );

        //this.resetAngle();

        Power power = new Power(movementPower);
        motors.setPower(power);

        while (this.opMode.opModeIsActive() && this.motors.allBusy()) {
            Power movementSigns = new Power(1.0);
            move(movementSigns);
        }
    }

    /** Move the robot forward for a fixed number of ticks.
     *
     * @param ticks Desired number of motor ticks to move for.
     */
    public void moveForwardAutonomous(int ticks) {
        this.movementPower.setDirection(DirectionCodes.FORWARD);
        this.moveAutonomous(ticks, Signs.FORWARD);
    }

    /** Move the robot backward for a fixed number of ticks.
     *
     * @param ticks Desired number of motor ticks to move for.
     * @return The resulting powers for the 4 motors.
     */
    public void moveBackAutonomous(int ticks) {
        this.movementPower.setDirection(DirectionCodes.BACKWARD);
        this.moveAutonomous(ticks, Signs.BACKWARD);
    }

    /** Move the robot to the right for a fixed number of ticks.
     *
     * @param ticks Desired number of motor ticks to move for.
     */
    public void moveRightAutonomous(int ticks) {
        this.movementPower.setDirection(DirectionCodes.RIGHT);
        this.moveAutonomous(ticks, Signs.RIGHT);
    }

    /** Move the robot to the left for a fixed number of ticks.
     *
     * @param ticks Desired number of motor ticks to move for.
     */
    public void moveLeftAutonomous(int ticks) {
        this.movementPower.setDirection(DirectionCodes.LEFT);
        this.moveAutonomous(ticks, Signs.LEFT);
    }

    private double getAngleDiff(Orientation angle) {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double deltaAngle = angles.firstAngle - angle.firstAngle;
        this.telemetryLogger.addData("Angle diff", deltaAngle);
        this.telemetryLogger.addData("Original", angle.firstAngle);
        this.telemetryLogger.addData("Current", angles.firstAngle);
        this.telemetryLogger.update();
        while (deltaAngle < -180) deltaAngle += 360;
        while (deltaAngle > 180) deltaAngle -= 360;
        return deltaAngle;
    }

    public void resetAngle()
    {
        this.lastAngle = getCurrentAngle();
    }

    private Orientation getCurrentAngle() {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    public void correctPosition() {
        AnglesList anglesList = new AnglesList();
        double deltaAngle = getAngleDiff(this.lastAngle);
        anglesList.add(deltaAngle);
        //this.rotate(-1 * Math.signum(deltaAngle) * Math.max((Math.abs(deltaAngle) - 5), 0), false);

        this.motors.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (Math.abs(deltaAngle) > 3 && !anglesList.stayedConstant()) {
            if (deltaAngle < 0) {
                // Turn left
                motors.setPower(new Power(this.correctionPower));
            } else {
                // Turn right
                motors.setPower(new Power(-1.0 * this.correctionPower));
            }
            deltaAngle = getAngleDiff(this.lastAngle);
            anglesList.add(deltaAngle);
        }

        this.motors.setPower(new Power());

    }

    public void stopAll() {
        motors.setPower(new Power());
    }

    public void rotate(double angle, boolean resetAngle) {
        Power signs;
        if (angle < 0) signs = Signs.ROTATE_RIGHT;
        else signs = Signs.ROTATE_LEFT;
        this.motors.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Orientation refAngle = getCurrentAngle();
        double angleDiff = getAngleDiff(refAngle);

        while (Math.abs(angleDiff) < Math.abs(angle)) {
            this.motors.setPower(signs.multiply(new Power(this.rotationPower)));
            angleDiff = getAngleDiff(refAngle);
        }
        this.stopAll();
        if (resetAngle) this.resetAngle();
    }

}

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

/**
 *      Class handling robot movement using the gyroscope.
 *
 *      Supports
 *          * Correcting the robot's position relative to the start angle.
 *          * Moving autonomously in 4 directions: front, back, left and right.
 *          * Moving in the 4 directions above when in controlled mode.
 *          * Spinning autonomously for a fixed number of degrees.
 *          * Spinning in controlled mode, both clockwise and counter-clockwise.
 *          * Resetting the reference angle used in corrections.
 *          * Setting a new (asympthotical) movement power.
 */

public class MoveWithGyro {

    public BNO055IMU imu;
    private Orientation lastAngle = new Orientation();
    private AsympthoticalPower movementPower;
    private double correctionPower = .2;
    private double rotationPower = .2;
    private Telemetry telemetryLogger;
    public MovementMotors movementMotors;
    private LinearOpMode opMode;
    private boolean correctPosition = true;

    public MoveWithGyro(Telemetry telemetry, HardwareMap hardwareMap, LinearOpMode opMode) {
        this.movementPower = new AsympthoticalPower(Constants.POWER_HIGHER_LIMIT);
        this.telemetryLogger = telemetry;
        this.movementMotors = new MovementMotors(hardwareMap, telemetry);
        this.opMode = opMode;

        // Initializing gyro setup
        imu=hardwareMap.get(BNO055IMU.class,"imu");
        BNO055IMU.Parameters parameters=new BNO055IMU.Parameters();
        parameters.mode=BNO055IMU.SensorMode.IMU;
        parameters.angleUnit=BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit=BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled=false;
        imu.initialize(parameters);

        this.telemetryLogger.addData("Gyroscope", "Calibrated");
    }

    public void turnCorrectionOnOff() {
        correctPosition = !correctPosition;
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

        movementMotors.setPower(power.multiply(signs));
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

    private void moveAutonomous(double cm, Power signs, boolean correctAfter) {
        movementMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        movementMotors.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        int ticks = cmToTicks(cm);
        movementMotors.setTargetPosition(
                (int) signs.FR * ticks, (int) signs.FL * ticks,
                (int) signs.BR * ticks,(int) signs.BL *  ticks
        );

        Power power = new Power(movementPower);
        movementMotors.setPower(power);

        while (this.opMode.opModeIsActive() && this.movementMotors.allBusy()) {
            Power movementSigns = new Power(1.0);
            move(movementSigns);
        }
        
        if (correctAfter) {
            this.correctPosition();
        }
        
    }

    /** Move the robot forward over a fixed distance.
     *
     * @param cm Desired distance to move the robot for.
     * @param correctAfter Whether to correct the position of the robot after it stops moving or not.
     */
    public void moveForwardAutonomous(double cm, boolean correctAfter) {
        this.movementPower.setDirection(DirectionCodes.FORWARD);
        this.moveAutonomous(cm, Signs.FORWARD, correctAfter);
    }

    /** Move the robot backward over a fixed distance
     *
     * @param cm Desired distance to move the robot for.
     * @param correctAfter Whether to correct the position of the robot after it stops moving or not.
     */
    public void moveBackAutonomous(double cm, boolean correctAfter) {
        this.movementPower.setDirection(DirectionCodes.BACKWARD);
        this.moveAutonomous(cm, Signs.BACKWARD, correctAfter);
    }

    /** Move the robot to the right over a fixed distance.
     *
     * @param cm Desired distance of motor ticks to move for.
     * @param correctAfter Whether to correct the position of the robot after it stops moving or not.
     */
    public void moveRightAutonomous(double cm, boolean correctAfter) {
        this.movementPower.setDirection(DirectionCodes.RIGHT);
        this.moveAutonomous(cm, Signs.RIGHT, correctAfter);
    }

    /** Move the robot to the left over a fixed distance.
     *
     * @param cm Desired distance ticks to move for.
     * @param correctAfter Whether to correct the position of the robot after it stops moving or not.
     */
    public void moveLeftAutonomous(double cm, boolean correctAfter) {
        this.movementPower.setDirection(DirectionCodes.LEFT);
        this.moveAutonomous(cm, Signs.LEFT, correctAfter);
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
        if (!correctPosition) return;
        AnglesList anglesList = new AnglesList();
        double deltaAngle = getAngleDiff(this.lastAngle);
        anglesList.add(deltaAngle);

        //this.rotate(-1 * Math.signum(deltaAngle) * Math.max((Math.abs(deltaAngle) - 5), 0), false);

        this.movementMotors.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (Math.abs(deltaAngle) > 3 && !anglesList.stayedConstant()) {
            telemetryLogger.addData("Correction angle", deltaAngle);
            telemetryLogger.update();
            if (deltaAngle < 0) {
                // Turn left
                movementMotors.setPower(new Power(this.correctionPower));
            } else {
                // Turn right
                movementMotors.setPower(new Power(-1.0 * this.correctionPower));
            }
            deltaAngle = getAngleDiff(this.lastAngle);
            anglesList.add(deltaAngle);
        }

        this.stopAll();
    }

    public void stopAll() {
        movementMotors.setPower(new Power());
    }

    public void spinAutonomous(double angle, boolean resetAngle) {
        Power signs;
        if (angle < 0) signs = Signs.ROTATE_RIGHT;
        else signs = Signs.ROTATE_LEFT;
        this.movementMotors.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Orientation refAngle = getCurrentAngle();
        double angleDiff = getAngleDiff(refAngle);

        while (Math.abs(angleDiff) < Math.abs(angle)) {
            this.movementMotors.setPower(signs.multiply(new Power(this.rotationPower)));
            angleDiff = getAngleDiff(refAngle);
        }
        this.stopAll();
        if (resetAngle) this.resetAngle();
    }

    public void spin(double power, double direction) {
        if (direction != 1 && direction != -1) return;
        Power actualPower = (new Power(power)).multiply(Constants.ROTATION_POWER_TELEOP).multiply(new Power(direction));
        movementMotors.setPower(actualPower);
    }

    private int cmToTicks(double cm) {
        return (int) (cm * Constants.TICKS_PER_CM);
    }

}

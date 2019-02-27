package org.firstinspires.ftc.teamcode.arms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.arms.constants.MotorSpeeds;
import org.firstinspires.ftc.teamcode.arms.constants.MotorTicks;
import org.firstinspires.ftc.teamcode.arms.constants.ServoPositions;

public class ArmsAction {

    private ArmsServos armsServos;
    private ArmsMotors armsMotors;
    private LinearOpMode opMode;
    private Telemetry telemetry;
    private HardwareMap hardwareMap;
    private boolean autonomous;

    public ArmsAction(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode opMode, boolean autonomous) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.opMode = opMode;

        this.armsMotors = new ArmsMotors(hardwareMap, telemetry);
        this.armsServos = new ArmsServos(hardwareMap, telemetry);

        if (autonomous){
            this.armsMotors.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.armsMotors.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public void climbOnLander(double powerFactor) {
        this.armsMotors.climbMotor.setPower(powerFactor * MotorSpeeds.CLIMB_MOTOR);
    }

    public void descendFromLander(double powerFactor) {
        this.armsMotors.climbMotor.setPower(-1.0 * powerFactor * MotorSpeeds.CLIMB_MOTOR);
    }

    public void descendFromLanderAutonomous() {
        if (!this.autonomous) return;
        this.armsMotors.climbMotor.setTargetPosition(MotorTicks.DESCEND_ROBOT);
        while (opMode.opModeIsActive() && this.armsMotors.climbMotor.isBusy()) {
            this.armsMotors.climbMotor.setPower(MotorSpeeds.CLIMB_MOTOR);
        }
    }

    public void hitMineral() {
        this.armsServos.mineralHit.setPosition(ServoPositions.MINERAL_HIT.end);
        opMode.sleep(500);
        this.armsServos.mineralHit.setPosition(ServoPositions.MINERAL_HIT.start);
    }

    public void moveMineralArmRight(double powerFactor) {
        telemetry.addData("Motor1 power", powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_1);
        telemetry.addData("Motor2 power", -1.0 * powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_2);
        telemetry.update();
        this.armsMotors.collectMotorMove1.setPower(powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_1);
        this.armsMotors.collectMotorMove2.setPower(-1.0 * powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_2);
    }

    public void moveMineralArmLeft(double powerFactor) {
        telemetry.addData("Motor1 power", -1.0 * powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_1);
        telemetry.addData("Motor2 power", powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_2);
        telemetry.update();
        this.armsMotors.collectMotorMove1.setPower(-1.0 * powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_1);
        this.armsMotors.collectMotorMove2.setPower(powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_2);
    }

    public void extendMineralArm(double powerFactor) {
        this.armsMotors.collectMotorExtend.setPower(powerFactor * MotorSpeeds.COLLECT_MOTOR_EXTEND);
    }

    public void contractMineralArm(double powerFactor) {
        this.armsMotors.collectMotorExtend.setPower(-1.0 * powerFactor * MotorSpeeds.COLLECT_MOTOR_EXTEND);
    }

    public void changeDirectionMineralArmBrush() {
        Servo.Direction originalDirection = this.armsServos.mineralArmBrush.getDirection();
        if (originalDirection == null) {
            this.armsServos.mineralArmBrush.setDirection(Servo.Direction.FORWARD);
        } else if (originalDirection.equals(Servo.Direction.FORWARD)) {
            this.armsServos.mineralArmBrush.setDirection(Servo.Direction.REVERSE);
        } else {
            this.armsServos.mineralArmBrush.setDirection(Servo.Direction.FORWARD);
        }
    }

    public void dropMascot() {
        this.armsServos.macotBoxTilt.setPosition(ServoPositions.MASCOT_DROP.end);
    }

    private void setMineralBoxPosition(double position) {
        this.armsServos.mineralBoxTilt.setPosition(position);
    }

    public void setMineralBoxTo0() {
        this.setMineralBoxPosition(ServoPositions.MINERAL_BOX_TILT.start);
    }

    public void setMineralBoxTo45() {
        this.setMineralBoxPosition(ServoPositions.MINERAL_BOX_TILT.end/2);
    }

    public void stopMineralBrush() {
        this.armsServos.mineralArmBrush.setPosition(0);
    }

    public void moveMineralArmToPark() {
        this.armsMotors.collectMotorMove1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.armsMotors.collectMotorMove2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.armsMotors.collectMotorMove1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.armsMotors.collectMotorMove2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void stopAll() {
        this.armsMotors.climbMotor.setPower(0);
        this.armsMotors.collectMotorMove1.setPower(0);
        this.armsMotors.collectMotorMove2.setPower(0);
        this.armsMotors.collectMotorExtend.setPower(0);
    }

}

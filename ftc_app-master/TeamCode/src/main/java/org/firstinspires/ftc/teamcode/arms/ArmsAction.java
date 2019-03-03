package org.firstinspires.ftc.teamcode.arms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.arms.constants.MotorSpeeds;
import org.firstinspires.ftc.teamcode.arms.constants.MotorTicks;
import org.firstinspires.ftc.teamcode.arms.constants.ServoPositions;

/**
 *      Class used to handle all necessary actions related to robot arms.
 *
 *      Supports:
 *          * Initializing the required motors and servos.
 *          * Moving the mineral arm (left -> right)
 *          * Extending/ Contracting the mineral arm.
 *          * Changing the spin direction of the mineral-collecting brush.
 *          * Tilting the mineral box.
 *          * Actioning the arm that hits the gold mineral in the autonomous stage.
 *          * Moving the latching arm (up/ down)
 *          * Stopping the motors from all arms.
 */

public class ArmsAction {

    public ArmsServos armsServos;
    public ArmsMotors armsMotors;
    private LinearOpMode opMode;
    private Telemetry telemetry;
    private HardwareMap hardwareMap;
    private boolean autonomous;

    public ArmsAction(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode opMode, boolean autonomous) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.opMode = opMode;
        this.autonomous = autonomous;

        this.armsMotors = new ArmsMotors(hardwareMap, telemetry);
        this.armsServos = new ArmsServos(hardwareMap, telemetry);

        if (autonomous){
            this.armsMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            this.armsMotors.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            this.armsServos.mineralHit.setPosition(ServoPositions.MINERAL_HIT.start);
        } else {
            this.setMineralBoxTo0();
        }
    }

    public void climbOnLander(double powerFactor) {
        this.armsMotors.climbMotor.setPower(powerFactor * MotorSpeeds.CLIMB_MOTOR);
    }

    public void descendFromLander(double powerFactor) {
        this.armsMotors.climbMotor.setPower(-1.0 * powerFactor * MotorSpeeds.CLIMB_MOTOR);
        telemetry.addData("Ticks", this.armsMotors.climbMotor.getCurrentPosition());
        telemetry.update();
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
        telemetry.addData("Motor1 power", powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_1_RIGHT);
        telemetry.addData("Motor2 power", -1.0 * powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_2_RIGHT);
        telemetry.addData("Motor1 ticks", armsMotors.collectMotorMove1.getCurrentPosition());
        telemetry.addData("Motor2 ticks", armsMotors.collectMotorMove2.getCurrentPosition());
        telemetry.addData("Motors ticks (avg)", (armsMotors.collectMotorMove2.getCurrentPosition() +
                armsMotors.collectMotorMove1.getCurrentPosition()) / 2);
        telemetry.update();
        this.armsMotors.collectMotorMove1.setPower(powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_1_RIGHT);
        this.armsMotors.collectMotorMove2.setPower(-1.0 * powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_2_RIGHT);
    }

    public void moveMineralArmLeft(double powerFactor) {
        telemetry.addData("Motor1 power", -1.0 * powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_1_LEFT);
        telemetry.addData("Motor2 power", powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_2_LEFT);
        telemetry.update();
        this.armsMotors.collectMotorMove1.setPower(-1.0 * powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_1_LEFT);
        this.armsMotors.collectMotorMove2.setPower(powerFactor * MotorSpeeds.COLLECT_MOTOR_MOVE_2_LEFT);
    }

    
    public void extendMineralArm(double powerFactor) {
        this.armsMotors.collectMotorExtend.setPower(powerFactor * MotorSpeeds.COLLECT_MOTOR_EXTEND);
        telemetry.addData("Ticks", armsMotors.collectMotorExtend.getCurrentPosition());
        telemetry.update();
    }

    public void contractMineralArm(double powerFactor) {
        this.armsMotors.collectMotorExtend.setPower(-1.0 * powerFactor * MotorSpeeds.COLLECT_MOTOR_EXTEND);
    }

    public void changeDirectionMineralArmBrush() {
        CRServo.Direction originalDirection = this.armsServos.mineralArmBrush.getDirection();
        telemetry.addData("Original direction", originalDirection);
        telemetry.update();
        if (originalDirection == null) {
            this.armsServos.mineralArmBrush.setDirection(CRServo.Direction.FORWARD);
            //this.armsServos.minearlArmBrush.setPosition(0)
            this.armsServos.mineralArmBrush.setPower(1);
        } else if (originalDirection.equals(CRServo.Direction.FORWARD)) {
            this.armsServos.mineralArmBrush.setDirection(CRServo.Direction.REVERSE);
            //this.armsServos.mineralArmBrush.setPosition(0);
            this.armsServos.mineralArmBrush.setPower(1);
        } else {
            this.armsServos.mineralArmBrush.setDirection(CRServo.Direction.FORWARD);
            //this.armsServos.mineralArmBrush.setPosition(1);
            this.armsServos.mineralArmBrush.setPower(1);
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
        //this.armsServos.mineralArmBrush.setPosition(0);
        this.armsServos.mineralArmBrush.setPower(0);
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

    private void setCollectionMotorsToEncoder() {
        this.armsMotors.collectMotorMove1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.armsMotors.collectMotorMove2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.armsMotors.collectMotorMove1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.armsMotors.collectMotorMove2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void setCollectionMotorsWithoutEncoder() {
        this.armsMotors.collectMotorMove1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.armsMotors.collectMotorMove2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void moveMineralArmToLander() {
        setCollectionMotorsToEncoder();
        MineralArmPowerInTransition powerGenerator = new MineralArmPowerInTransition();

        this.armsMotors.collectMotorMove1.setTargetPosition(MotorTicks.MINERAL_ARM_TO_LANDER);
        this.armsMotors.collectMotorMove2.setTargetPosition(-1 * MotorTicks.MINERAL_ARM_TO_LANDER);

        while (this.opMode.opModeIsActive() &&
                (armsMotors.collectMotorMove1.isBusy() && armsMotors.collectMotorMove2.isBusy())) {
            int ticks = (Math.abs(armsMotors.collectMotorMove1.getCurrentPosition()) +
                    Math.abs(armsMotors.collectMotorMove2.getCurrentPosition())) / 2;
            double power = powerGenerator.getPower(ticks);
            this.armsMotors.collectMotorMove1.setPower(power);
            this.armsMotors.collectMotorMove2.setPower(power);
        }

        setCollectionMotorsWithoutEncoder();
    }

    public void moveMineralArmToCrater() {
        setCollectionMotorsToEncoder();
        MineralArmPowerInTransition powerGenerator = new MineralArmPowerInTransition();

        this.armsMotors.collectMotorMove1.setTargetPosition(MotorTicks.MINERAL_ARM_TO_CRATER);
        this.armsMotors.collectMotorMove2.setTargetPosition(MotorTicks.MINERAL_ARM_TO_CRATER);

        while (this.opMode.opModeIsActive() && armsMotors.collectMotorMove1.isBusy() &&
                this.armsMotors.collectMotorMove2.isBusy()) {
            int ticks = (Math.abs(armsMotors.collectMotorMove1.getCurrentPosition()) +
                    Math.abs(armsMotors.collectMotorMove2.getCurrentPosition())) / 2;
            double power = powerGenerator.getPower(ticks);
            this.armsMotors.collectMotorMove1.setPower(power);
            this.armsMotors.collectMotorMove2.setPower(power);

        }

        setCollectionMotorsWithoutEncoder();
    }

}

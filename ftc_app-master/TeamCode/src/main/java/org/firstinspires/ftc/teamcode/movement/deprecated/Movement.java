package org.firstinspires.ftc.teamcode.movement.deprecated;

import java.util.HashMap;
import java.util.Set;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.movement.Power;

@Deprecated
public class Movement {

    private static final Power COMPENSATIONS = new Power(1.0, .95, .95, .95);

    private static Power addCompensation(Power raw) {
        return raw.multiply(COMPENSATIONS);
    }

    private static final DirectionsList latest_directions = new DirectionsList();

    private static final double CORRECTION_COEFFICIENT = 0.25;
    private static final double POWER_COEFFICIENT = 1.0;

    private static HashMap<Interval, Move> functions = new HashMap();

    private interface Move {
        public Power move(double power, double alpha);
    }

    public static void set_functions() {
        functions.put(Limits.RIGHT_1, new Move() {
            public Power move(double power, double alpha) {return moveSideways(power, 1.0);}
        });
        functions.put(Limits.RIGHT_2, new Move() {
            public Power move(double power, double alpha) {return moveSideways(power, 1.0);}
        });
        functions.put(Limits.FRONT, new Move() {
            public Power move(double power, double alpha) {return moveBackForth(power, -1.0);}
        });
        functions.put(Limits.DOWN, new Move() {
            public Power move(double power, double alpha) {return moveBackForth(power, 1.0);}
        });
        functions.put(Limits.LEFT, new Move() {
            public Power move(double power, double alpha) {return moveSideways(power, -1.0);}
        });
        functions.put(Limits.FRONT_RIGHT, new Move() {
            public Power move(double power, double alpha) {return moveFrontRight(power, alpha);}
        });
        functions.put(Limits.FRONT_LEFT, new Move() {
            public Power move(double power, double alpha) {return moveFrontLeft(power, alpha);}
        });
        functions.put(Limits.DOWN_LEFT, new Move() {
            public Power move(double power, double alpha) {return moveBackLeft(power, alpha);}
        });
        functions.put(Limits.DOWN_RIGHT, new Move() {
            public Power move(double power, double alpha) {return moveBackRight(power, alpha);}
        });
    }

    private static double getAngle(double x, double y) {
        if (x == 0 && y == 0) return 0.0;

        double angle = Math.toDegrees(Math.atan2(y, x));
        if (angle < 0) {
            return 360.0 + angle;
        }

        return angle;
    }

    public static Power move(double x, double y, Telemetry telemetry) {
        Direction d = new Direction(x, y);
        latest_directions.add(d);
        if (!latest_directions.stayedConstant()) {
            return new Power();
        }
        double power = getPower(x, y);
        double angle = getAngle(x, y);

        telemetry.addData("Angle", angle);
        telemetry.update();

        Set<Interval> keys = functions.keySet();

        for (Interval interval: keys) {
            if (interval.contains(angle)) {
                //telemetry.addData("Interval", interval);
                //telemetry.update();
                return functions.get(interval).move(power, angle);
            }
        }

        return new Power();
    }

    private static double getCorrectionPower(double originalPower, double angleDifference) {
        double factor = Math.sin(Math.toRadians(Math.abs(angleDifference)));
        
        return CORRECTION_COEFFICIENT * factor * originalPower;
    }

    private static double getPower(double x, double y) {
        return POWER_COEFFICIENT * Math.sqrt(x*x + y*y);
    }

    private static Power moveBackForth(double power, double sign) {
        Power result = new Power();

        result.FL = result.BL = power * sign;
        result.FR = result.BR = power * sign * -1.0;

        return addCompensation(result);
    }

    private static Power moveSideways(double power, double sign) {
        Power result = new Power();

        result.FL = result.FR = sign * power * -1.0;
        result.BL = result.BR = sign * power;

        return addCompensation(result);
    }

    private static Power moveFrontRight(double power, double alpha) {
        Power result = new Power();

        result.FL = -1.0 * power;
        result.BR = power;

        double angleDiff = alpha - Limits.MID_FIRST_QUADRANT;
        double correctionPower = getCorrectionPower(power, angleDiff);
        result.FR = Math.signum(angleDiff) * correctionPower;

        return addCompensation(result);
    }

    private static Power moveFrontLeft(double power, double alpha) {
        Power result = new Power();

        result.FR = power;
        result.BL = -1.0 * power;

        double angleDiff = alpha - Limits.MID_SECOND_QUADRANT;
        double correctionPower = getCorrectionPower(power, angleDiff);
        result.FL = Math.signum(angleDiff) * correctionPower;

        return addCompensation(result);
    }

    private static Power moveBackRight(double power, double alpha) {
        Power result = new Power();

        result.FR = -1.0 * power;
        result.BL = power;

        double angleDiff = alpha - Limits.MID_FOURTH_QUADRANT;
        double correctionPower = getCorrectionPower(power, angleDiff);
        result.BR = Math.signum(angleDiff) * correctionPower;

        return addCompensation(result);
    }

    private static Power moveBackLeft(double power, double alpha) {
        Power result = new Power();

        result.FL = power;
        result.BR = -1.0 * power;

        double angleDiff = alpha - Limits.MID_THIRD_QUADRANT;
        double correctionPower = getCorrectionPower(power, angleDiff);
        result.BL = Math.signum(angleDiff) * correctionPower;

        return addCompensation(result);
    }

}

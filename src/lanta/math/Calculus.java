package lanta.math;

public class Calculus {
    public static double limit(double goal, Expression<Double> function) {
        double delta = 1e-9;
        double minorValue = function.eval(goal - delta);
        double majorValue = function.eval(goal + delta);
        if (Math.abs(minorValue - majorValue) < 1e-7)  return (minorValue + majorValue) / 2;
        return Double.NaN;
    }

    public static <T extends Number> Expression<Double> derivate(Expression<T> function){
        double limitDelta = 1e-9;
        return (x) -> (function.eval(x.doubleValue() + limitDelta).doubleValue() - function.eval(x.doubleValue() - limitDelta).doubleValue())/(2 * limitDelta);
    }
}

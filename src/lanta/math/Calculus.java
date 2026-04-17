package lanta.math;

import lanta.math.expressions.BivariateExpression;
import lanta.math.expressions.Expression;

public class Calculus {
    private static final double DELTA = 1e-9;
    public static  Expression<Double> limit(Expression<? extends Number> function) {
        return x -> {
            double goal = x.doubleValue();
            double minorValue = function.eval(goal - DELTA).doubleValue();
            double majorValue = function.eval(goal + DELTA).doubleValue();
            if (Math.abs(minorValue - majorValue) < 1e-7) return (minorValue + majorValue) / 2;
            return Double.NaN;
        };
    }

    public static Expression<Double> derivative(Expression<? extends Number> function){
        return (x) -> (function.eval(x.doubleValue() + DELTA).doubleValue() - function.eval(x.doubleValue() - DELTA).doubleValue())/(2 * DELTA);
    }

    public static Expression<Double> integral(Expression<? extends Number> function, int delta) throws IllegalArgumentException{
        BivariateExpression<Double> expression = biIntegral(function, delta);
        return y -> expression.eval(0, y);
    }

    public static BivariateExpression<Double> biIntegral(Expression<? extends Number> function, int delta) throws IllegalArgumentException {
        if (delta % 2 != 0) throw new IllegalArgumentException("delta must be even");
        return (x, y) -> {
            double a = x.doubleValue();
            double b = y.doubleValue();
            double step = (b - a) / delta;
            double soma = function.eval(a).doubleValue() + function.eval(b).doubleValue();
            for (int i = 1; i < delta; i++) soma +=function.eval(a + i * step).doubleValue() * ((i % 2 == 0) ? 2 : 4);
            return (step / 3.0) * soma;
        };
    }
}

package lanta.math;

import java.util.function.Function;

public class RootFinder {
    public static double Bisector(double Xa, double Xb, Function<Double, Double> equation, double precision) throws NumberFormatException {
        while(true){
            double Xm = (Xa + Xb)/2;
            if(Double.isNaN(Xa) || Double.isNaN(Xb) || Double.isNaN(Xm)){
                if(Double.isNaN(Xa)) throw new NumberFormatException("Key value Xa is NaN");
                if(Double.isNaN(Xb)) throw new NumberFormatException("Key value Xb is NaN");
                if(Double.isNaN(Xm)) throw new NumberFormatException("Key value Xm is NaN");
            }
            if(Math.abs(equation.apply(Xa)) <= precision) return Xa;
            if(Math.abs(equation.apply(Xb)) <= precision) return Xb;
            if(Math.abs(equation.apply(Xm)) <= precision) return Xm;
            if(equation.apply(Xa) * equation.apply(Xm) >= 0) Xa = Xm; else Xb = Xm;
        }
    }
    public static double Newton(double x, Function<Double, Double> equation, Function<Double, Double> derivateEquation, double precision) throws NumberFormatException {
        while(true){
            if(Double.isNaN(x)) throw new NumberFormatException("Key value x is NaN");
            if(Math.abs(equation.apply(x)) < precision) return x;
            x = x - (equation.apply(x) / derivateEquation.apply(x));
        }
    }
    public static double Secants(double Xa, double Xb, Function<Double, Double> equation, double precision) throws NumberFormatException {
        double Xc;
        while(true){
            if(Math.abs(equation.apply(Xa)) <= precision) return Xa;
            if(Math.abs(equation.apply(Xb)) <= precision) return Xb;
            if(Double.isNaN(Xa) || Double.isNaN(Xb)){
                if(Double.isNaN(Xa)) throw new NumberFormatException("Key value Xa is NaN");
                if(Double.isNaN(Xb)) throw new NumberFormatException("Key value Xb is NaN");
            }
            Xc = Xb - (equation.apply(Xb) * ((Xb - Xa) / (equation.apply(Xb) - equation.apply(Xa))));
            Xa = Xb; Xb = Xc;
        }
    }
}

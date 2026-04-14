package lanta.math;

public class RootFinder {
    public static double Bisector(double Xa, double Xb, Expression<Double> equation, double precision) throws NumberFormatException {
        while(true){
            double Xm = (Xa + Xb)/2;
            if(Double.isNaN(Xa) || Double.isNaN(Xb) || Double.isNaN(Xm)){
                if(Double.isNaN(Xa)) throw new NumberFormatException("Key value Xa is NaN");
                if(Double.isNaN(Xb)) throw new NumberFormatException("Key value Xb is NaN");
                if(Double.isNaN(Xm)) throw new NumberFormatException("Key value Xm is NaN");
            }
            if(Math.abs(equation.eval(Xa)) <= precision) return Xa;
            if(Math.abs(equation.eval(Xb)) <= precision) return Xb;
            if(Math.abs(equation.eval(Xm)) <= precision) return Xm;
            if(equation.eval(Xa) * equation.eval(Xm) >= 0) Xa = Xm; else Xb = Xm;
        }
    }

    public static double Newton(double x, Expression<Double> equation,Expression<Double> derivateEquation, double precision) throws NumberFormatException {
        while(true){
            if(Double.isNaN(x)) throw new NumberFormatException("Key value x is NaN");
            if(Math.abs(equation.eval(x)) < precision) return x;
            x = x - (equation.eval(x) / derivateEquation.eval(x));
        }
    }

    public static double Secants(double Xa, double Xb, Expression<Double> equation, double precision) throws NumberFormatException {
        double Xc;
        while(true){
            if(Math.abs(equation.eval(Xa)) <= precision) return Xa;
            if(Math.abs(equation.eval(Xb)) <= precision) return Xb;
            if(Double.isNaN(Xa) || Double.isNaN(Xb)){
                if(Double.isNaN(Xa)) throw new NumberFormatException("Key value Xa is NaN");
                if(Double.isNaN(Xb)) throw new NumberFormatException("Key value Xb is NaN");
            }
            Xc = Xb - (equation.eval(Xb) * ((Xb - Xa) / (equation.eval(Xb) - equation.eval(Xa))));
            Xa = Xb; Xb = Xc;
        }
    }
}
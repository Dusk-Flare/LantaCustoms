import lanta.math.Calculus;
import lanta.math.expressions.Expression;
import lanta.math.Parser;
import lanta.math.RootFinder;

public class Main {
    public static void main(String[] args) {
        String infix = "4+xcos(x)";
        String postfix = Parser.toPostfix(infix);
        System.out.println(infix);
        System.out.println(postfix);
        Expression<Double> equation = Parser.buildExpression(postfix);
        Expression<Double> derivative = Calculus.derivative(equation);
        System.out.println(RootFinder.Newton(8, equation, derivative, Math.pow(10, -5)));
        System.out.println(RootFinder.Newton(12, equation, derivative, Math.pow(10, -5)));
    }
}
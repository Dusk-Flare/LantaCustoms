import lanta.math.Matrix;
import lanta.math.Parser;
import lanta.math.expressions.Expression;

public class Main {
    public static void main(String[] args) {
        String infix = "sin(x)^2+cos(x)^2";
        String postfix = Parser.toPostfix(infix);
        Expression<Double> function = Parser.buildExpression(postfix);

        System.out.println(infix);
        System.out.println(postfix);
        System.out.println(function.eval(2));
    }
}
import lanta.math.Matrix;
import lanta.math.Parser;

public class Main {
    public static void main(String[] args) {
        Integer[][] array = {{1, 2},{3,4}};
        Matrix<Integer> matrix = new Matrix<>(array, Number::intValue);
        System.out.println(matrix);
        Matrix<Double> wow = new Matrix<>(matrix.toString(), Double::valueOf, Number::doubleValue);
        System.out.println(wow);
        String post = Parser.toPostfix("toDegrees(pi)");
        System.out.println(post);
        System.out.println(Parser.buildExpression(post).eval(0));
    }
}
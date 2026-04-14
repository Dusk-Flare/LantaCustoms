import lanta.lists.CustomLinkedList;
import lanta.math.Expression;
import lanta.math.Parser;
import lanta.math.RootFinder;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String infix = "3(x) + 15";
        String postfix = Parser.toPostfix(infix);
        Expression<Double> expression = Parser.buildExpression(postfix);
        System.out.println(infix + " = " + postfix);
        System.out.println(infix.split("=", 2).length);
        System.out.println(RootFinder.Bisector(-20, 20, expression, 0.001));
    }
}
package lanta.math;

import lanta.math.expressions.Expression;

import java.util.*;
import java.util.function.Function;

import static java.lang.Math.*;

public final class Parser {
    private Parser() {}

    private static final Map<Character, Integer> PRECEDENCE = Map.of(
        '+', 1, '-', 1,
        '*', 2, '/', 2,
        '^', 3,
        '~', 4, '°', 4
    );

    private static final Map<String, Double> CONSTANTS = Map.of(
            "e", Math.E,
            "pi", Math.PI
    );

    private static final Map<Character, String> FUNCTIONS = Map.of(
            'A', "sin",
            'B', "cos",
            'C', "tan",
            'D', "floor",
            'E', "ceil"
    );

    private static final List<Character> IMPLICITS = new ArrayList<>(List.of(
            'x', 'X', '('
    ));
    static {
        IMPLICITS.addAll(FUNCTIONS.keySet());
    }

    public static <T extends Number> T toNumber(String text, Function<String, T> parser){
        try{
            return toNumber(text, parser, null);
        } catch(NumberFormatException e){
            return null;
        }
    }

    public static <T extends Number> T toNumber(String text, Function<String, T> parser, T defaultsTo) throws NumberFormatException {
        text = text.replaceAll("^(-?(\\d+(\\.\\d+)?|\\.\\d+)).*", "$1");
        try {
            return parser.apply(text);
        } catch (NumberFormatException e) {
            if(defaultsTo == null) throw e;
            return defaultsTo;
        }
    }

    public static String toPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<Character> operators = new Stack<>();
        System.out.println(invertUnitPos(infix));
        for (Map.Entry<Character, String> entry : FUNCTIONS.entrySet()) {
            infix = infix.replace(entry.getValue(), entry.getKey().toString());
        }
        char[] tokens = infix.replace(" ", "").toCharArray();
        boolean nextCanBeUnary = true;

        for (int i = 0; i < tokens.length; i++) {
            char token = tokens[i];
            if (isNumber(token)) {
                i = appendNumber(tokens, i, output);
                nextCanBeUnary = false;
                if (hasImplicitMultiplication(tokens, i)) {
                    pushOperator('*', operators, output);
                }
                continue;
            }

            if (isVariable(token)) {
                output.append("x ");
                nextCanBeUnary = false;
                if (hasImplicitMultiplication(tokens, i)) {
                    pushOperator('*', operators, output);
                }
                continue;
            }

            if (token == '(') {
                operators.push(token);
                nextCanBeUnary = true;
                continue;
            }

            if (token == ')') {
                unwindUntilOpenParenthesis(operators, output);
                nextCanBeUnary = false;
                continue;
            }

            if (isOperator(token) || isFunction(token)) {
                if (nextCanBeUnary) {
                    pushOperator(token == '-' ? '~' : token, operators, output);
                } else {
                    pushOperator(token, operators, output);
                }
                nextCanBeUnary = true;
            }
        }

        while (!operators.isEmpty()) {
            char op =  operators.pop();
            output.append(isFunction(op) ? FUNCTIONS.get(op) : op).append(' ');
        }

        return output.toString().trim();
    }

    public static Expression<Double> buildExpression(String postfix) {
        Stack<Expression<Double>> stack = new Stack<>();

        for (String token : postfix.split("\\s+")) {
            if (token.isEmpty()) continue;

            if (isOperatorToken(token)) {
                char op = token.charAt(0);
                if (op == '~') {
                    Expression<Double> arg = stack.pop();
                    stack.push(x -> -arg.eval(x));
                } else if(op == '°'){
                    Expression<Double> arg = stack.pop();
                    stack.push(x -> Math.toRadians(arg.eval(x)));
                } else{
                    Expression<Double> right = stack.pop();
                    Expression<Double> left = stack.pop();
                    stack.push(applyOperator(op, left, right));
                }
                continue;
            }

            if(isFunctionToken(token)){
                Expression<Double> arg = stack.pop();
                stack.push(applyFunctions(token, arg));
                continue;
            }

            if (isVariableToken(token)) {
                stack.push(Number::doubleValue);
                continue;
            }

            if(isConstantToken(token)){
                stack.push(_ -> CONSTANTS.get(token));
                continue;
            }

            double value = Double.parseDouble(token);
            stack.push(_ -> value);
        }

        return stack.pop();
    }

    private static String invertUnitPos(String input){
        return input.replaceAll("(\\d+)(°)", " $2$1")
                .replaceAll("(\\(*\\))(°)", " $2$1")
                .replaceAll("([xX])(°)", " $2$1");
    }

    private static int appendNumber(char[] tokens, int index, StringBuilder output) {
        while (index < tokens.length && (Character.isDigit(tokens[index]) || 
               tokens[index] == '.' || tokens[index] == 'e' || tokens[index] == 'E')) {
            output.append(tokens[index++]);
        }
        output.append(' ');
        return index - 1;
    }

    private static boolean hasImplicitMultiplication(char[] tokens, int index) {
        int next = index + 1;
        return next < tokens.length && IMPLICITS.contains(tokens[next]);
    }

    private static void pushOperator(char operator, Stack<Character> stack, StringBuilder output) {
        while (!stack.isEmpty() && stack.peek() != '(') {
            char stackToken = isFunction(stack.peek()) ? '°' : stack.peek();
            int top = PRECEDENCE.getOrDefault(stackToken, 0);
            int current = PRECEDENCE.get(isFunction(operator) ? '°' : operator);

            if (top > current || (top == current && operator != '^' && operator != '°' && operator != '~' && !isFunction(operator))) {
                char op = stack.pop();
                output.append(isFunction(op) ? FUNCTIONS.get(op) : op).append(' ');
            } else {
                break;
            }
        }
        stack.push(operator);
    }

    private static void unwindUntilOpenParenthesis(Stack<Character> stack, StringBuilder output) {
        while (!stack.isEmpty() && stack.peek() != '(') {
            char op = stack.pop();
            output.append(isFunction(op) ? FUNCTIONS.get(op) : op).append(' ');;
        }
        if (!stack.isEmpty()) stack.pop();
    }

    private static Expression<Double> applyOperator(char operator, Expression<Double> left, Expression<Double> right) {
        return x -> {
            double a = left.eval(x);
            double b = right.eval(x);
            return switch (operator) {
                case '+' -> a + b;
                case '-' -> a - b;
                case '*' -> a * b;
                case '/' -> a / b;
                case '^' -> Math.pow(a, b);
                default  -> 0.0;
            };
        };
    }

    private static Expression<Double> applyFunctions(String function, Expression<Double> operand) {
        return x -> {
            double a = operand.eval(x);
            return switch (function) {
                case "sin" -> sin(a);
                case "cos" -> cos(a);
                case "tan" -> tan(a);
                case "floor" -> floor(a);
                case "ceil" -> ceil(a);
                default -> 0.0;
            };
        };
    }

    private static boolean isNumber(char c) {
        return Character.isDigit(c);
    }

    private static boolean isVariable(char c) {
        return c == 'x' || c == 'X';
    }

    private static boolean isOperator(char c) {
        return PRECEDENCE.containsKey(c);
    }

    private static boolean isFunction(char c) {
        return FUNCTIONS.containsKey(c);
    }

    private static boolean isOperatorToken(String token) {
        return token.length() == 1 && isOperator(token.charAt(0));
    }

    private static boolean isVariableToken(String token) {
        return token.equalsIgnoreCase("x");
    }

    private static boolean isConstantToken(String token) {
        return CONSTANTS.containsKey(token);
    }

    private static boolean isFunctionToken(String token) {
        return FUNCTIONS.containsValue(token);
    }
}
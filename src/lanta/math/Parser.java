package lanta.math;

import java.util.Map;
import java.util.Stack;

public final class Parser {
    private Parser() {}

    private static final Map<Character, Integer> PRECEDENCE = Map.of(
        '+', 1, '-', 1,
        '*', 2, '/', 2,
        '^', 3, '~', 4
    );

    public static String toPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<Character> operators = new Stack<>();
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

            if (isOperator(token)) {
                if (nextCanBeUnary) {
                    if (token == '-') {
                        pushOperator('~', operators, output);
                    }
                } else {
                    pushOperator(token, operators, output);
                }
                nextCanBeUnary = true;
            }
        }

        while (!operators.isEmpty()) {
            output.append(operators.pop()).append(' ');
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
                } else {
                    Expression<Double> right = stack.pop();
                    Expression<Double> left = stack.pop();
                    stack.push(applyOperator(op, left, right));
                }
                continue;
            }

            if (isVariableToken(token)) {
                stack.push(Number::doubleValue);
                continue;
            }

            double value = Double.parseDouble(token);
            stack.push(_ -> value);
        }

        return stack.pop();
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
        return next < tokens.length && (tokens[next] == 'x' || tokens[next] == 'X' || tokens[next] == '(');
    }

    private static void pushOperator(char operator, Stack<Character> stack, StringBuilder output) {
        while (!stack.isEmpty() && stack.peek() != '(') {
            int top = PRECEDENCE.getOrDefault(stack.peek(), 0);
            int current = PRECEDENCE.get(operator);

            if (top > current || (top == current && operator != '^' && operator != '~')) {
                output.append(stack.pop()).append(' ');
            } else {
                break;
            }
        }
        stack.push(operator);
    }

    private static void unwindUntilOpenParenthesis(Stack<Character> stack, StringBuilder output) {
        while (!stack.isEmpty() && stack.peek() != '(') {
            output.append(stack.pop()).append(' ');
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

    private static boolean isNumber(char c) {
        return Character.isDigit(c);
    }

    private static boolean isVariable(char c) {
        return c == 'x' || c == 'X';
    }

    private static boolean isOperator(char c) {
        return PRECEDENCE.containsKey(c);
    }

    private static boolean isOperatorToken(String token) {
        return token.length() == 1 && isOperator(token.charAt(0));
    }

    private static boolean isVariableToken(String token) {
        return token.equalsIgnoreCase("x");
    }
}
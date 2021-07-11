package demo.statck;

import java.util.Scanner;
import java.util.Stack;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-25
 */
public class Calculator {
    public static final String USAGE = "== 计算器 ==";

    public static void main(String[] args) {
        System.out.println(USAGE);
        Scanner scanner = new Scanner(System.in);
        String input = "";
        final String CLOSE_MARK = "bye";
        System.out.println("请输入表达式(输入bye退出):");
        input = scanner.nextLine();
        while (input.length() != 0
                && !CLOSE_MARK.equals((input))) {
            System.out.print("波兰表达式(PN):");
            try {
                toPolishNotation(input);
            } catch (NumberFormatException e) {
                System.out.println("\n输入错误，不是数字.");
            } catch (IllegalArgumentException e) {
                System.out.println("\n输入错误:" + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n输入错误，无效的表达式.");
            }
            System.out.print("逆波兰表达式(RPN):");
            try {
                toReversePolishNotation(input);
            } catch (NumberFormatException e) {
                System.out.println("\n输入错误，不是数字.");
            } catch (IllegalArgumentException e) {
                System.out.println("\n输入错误:" + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n输入错误，无效的表达式.");
            }
            System.out.println("请输入表达式(输入bye退出):");
            input = scanner.nextLine();
        }
        System.out.println("程序退出");
    }

    /**
     * 解析表达式，并计算
     *
     * @param input
     * @throws IllegalArgumentException
     * @throws NumberFormatException
     */
    private static void toPolishNotation(String input)
            throws IllegalArgumentException, NumberFormatException {
        int len = input.length();
        char c, tempChar;
        Stack<Character> s1 = new Stack<Character>();
        Stack<Double> s2 = new Stack<Double>();
        Stack<Object> expression = new Stack<Object>();
        double number;
        int lastIndex = -1;
        for (int i = len - 1; i >= 0; --i) {
            c = input.charAt(i);
            if (Character.isDigit(c)) {
                lastIndex = readDoubleReverse(input, i);
                number = Double.parseDouble(input.substring(lastIndex, i + 1));
                s2.push(number);
                i = lastIndex;
                if ((int) number == number) {
                    expression.push((int) number);
                } else {
                    expression.push(number);
                }
            } else if (isOperator(c)) {
                while (!s1.isEmpty()
                        && s1.peek() != ')'
                        && priorityCompare(c, s1.peek()) < 0) {
                    expression.push(s1.peek());
                    s2.push(calc(s2.pop(), s2.pop(), s1.pop()));
                }
                s1.push(c);
            } else if (c == ')') {
                s1.push(c);
            } else if (c == '(') {
                while ((tempChar = s1.pop()) != ')') {
                    expression.push(tempChar);
                    s2.push(calc(s2.pop(), s2.pop(), tempChar));
                    if (s1.isEmpty()) {
                        throw new IllegalArgumentException("括号不匹配，缺少右括号 ')'.");
                    }
                }
            } else if (c == ' ') {
                // ignore
            } else {
                throw new IllegalArgumentException(
                        "错误的字符 '" + c + "'");
            }
        }
        while (!s1.isEmpty()) {
            tempChar = s1.pop();
            expression.push(tempChar);
            s2.push(calc(s2.pop(), s2.pop(), tempChar));
        }
        while (!expression.isEmpty()) {
            System.out.print(expression.pop() + " ");
        }
        double result = s2.pop();
        if (!s2.isEmpty()) {
            throw new IllegalArgumentException("表达式错误.");
        }
        System.out.println();
        if ((int) result == result) {
            System.out.println("计算结果为: " + (int) result);
        } else {
            System.out.println("计算结果为: " + result);
        }
    }

    /**
     * 解析表达式并计算
     *
     * @param input
     * @throws IllegalArgumentException
     * @throws NumberFormatException
     */
    private static void toReversePolishNotation(String input)
            throws IllegalArgumentException, NumberFormatException {
        int len = input.length();
        char c, tempChar;
        Stack<Character> s1 = new Stack<Character>();
        Stack<Double> s2 = new Stack<Double>();
        double number;
        int lastIndex = -1;
        for (int i = 0; i < len; ++i) {
            c = input.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                lastIndex = readDouble(input, i);
                number = Double.parseDouble(input.substring(i, lastIndex));
                s2.push(number);
                i = lastIndex - 1;
                if ((int) number == number) {
                    System.out.print((int) number + " ");
                } else {
                    System.out.print(number + " ");
                }
            } else if (isOperator(c)) {
                while (!s1.isEmpty()
                        && s1.peek() != '('
                        && priorityCompare(c, s1.peek()) <= 0) {
                    System.out.print(s1.peek() + " ");
                    double num1 = s2.pop();
                    double num2 = s2.pop();
                    s2.push(calc(num2, num1, s1.pop()));
                }
                s1.push(c);
            } else if (c == '(') {
                s1.push(c);
            } else if (c == ')') {
                while ((tempChar = s1.pop()) != '(') {
                    System.out.print(tempChar + " ");
                    double num1 = s2.pop();
                    double num2 = s2.pop();
                    s2.push(calc(num2, num1, tempChar));
                    if (s1.isEmpty()) {
                        throw new IllegalArgumentException(
                                "括号不匹配，缺少左括号 '('.");
                    }
                }
            } else if (c == ' ') {
                // ignore
            } else {
                throw new IllegalArgumentException(
                        "错误的字符'" + c + "'");
            }
        }
        while (!s1.isEmpty()) {
            tempChar = s1.pop();
            System.out.print(tempChar + " ");
            double num1 = s2.pop();
            double num2 = s2.pop();
            s2.push(calc(num2, num1, tempChar));
        }
        double result = s2.pop();
        if (!s2.isEmpty()) {
            throw new IllegalArgumentException("表达式错误.");
        }
        System.out.println();
        if ((int) result == result) {
            System.out.println("计算结果为: " + (int) result);
        } else {
            System.out.println("计算结果为: " + result);
        }
    }

    /**
     * 计算
     *
     * @param num1
     * @param num2
     * @param op
     * @return
     * @throws IllegalArgumentException
     */
    private static double calc(double num1, double num2, char op)
            throws IllegalArgumentException {
        switch (op) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if (num2 == 0) {
                    throw new IllegalArgumentException("除数不能为 0.");
                }
                return num1 / num2;
            default:
                return 0; // will never catch up here
        }
    }

    /**
     * 比较两个操作符的优先级
     *
     * @param op1
     * @param op2
     * @return
     */
    private static int priorityCompare(char op1, char op2) {
        switch (op1) {
            case '+':
            case '-':
                return (op2 == '*' || op2 == '/' ? -1 : 0);
            case '*':
            case '/':
                return (op2 == '+' || op2 == '-' ? 1 : 0);
        }
        return 1;
    }

    /**
     * 读取下一个数字（反向）
     *
     * @param input
     * @param start
     * @return
     * @throws IllegalArgumentException
     */
    private static int readDoubleReverse(String input, int start)
            throws IllegalArgumentException {
        int dotIndex = -1;
        char c;
        for (int i = start; i >= 0; --i) {
            c = input.charAt(i);
            if (c == '.') {
                if (dotIndex != -1) {
                    throw new IllegalArgumentException(
                            "小数点有误");
                } else {
                    dotIndex = i;
                }
            } else if (!Character.isDigit(c)) {
                return i + 1;
            } else if (i == 0) {
                return 0;
            }
        }
        throw new IllegalArgumentException("不是数字");
    }

    /**
     * 读取下一个数字
     *
     * @param input
     * @param start
     * @return
     * @throws IllegalArgumentException
     */
    private static int readDouble(String input, int start)
            throws IllegalArgumentException {
        int len = input.length();
        int dotIndex = -1;
        char c;
        for (int i = start; i < len; ++i) {
            c = input.charAt(i);
            if (c == '.') {
                if (dotIndex != -1) {
                    throw new IllegalArgumentException(
                            "小数点有误");
                } else if (i == len - 1) {
                    throw new IllegalArgumentException(
                            "不是数字，点不能是数字的最后一部分");
                } else {
                    dotIndex = i;
                }
            } else if (!Character.isDigit(c)) {
                if (dotIndex == -1 || i - dotIndex > 1) {
                    return i;
                } else {
                    throw new IllegalArgumentException(
                            "不是数字，点不能是数字的最后一部分");
                }
            } else if (i == len - 1) {
                return len;
            }
        }

        throw new IllegalArgumentException("不是一个数字");
    }

    /**
     * 如果字符是运算符，则返回 true。
     *
     * @param c
     * @return
     */
    private static boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }

}

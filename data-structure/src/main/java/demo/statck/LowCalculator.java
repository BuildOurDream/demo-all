package demo.statck;

import java.util.Scanner;
import java.util.Stack;

/**
 * <p>可以计算一位数简单运算的低能计算机哈哈😊</p>
 *
 * @Author J.Star
 * @Date 2021-06-23
 */
public class LowCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入表达式:");
            //计算表达式
            String expression = scanner.nextLine();
            //数栈
            Stack<Integer> numStack = new Stack<>();
            //符号栈
            Stack<Character> operatorStack = new Stack<>();
            //扫描下标
            int index = 0;

            //扫描表达式
            //符号栈为空,直接将符号压入栈中
            //符号栈不为空,将当前符号的优先级与栈中符号的优先级进行比较.当前符号优先级低直接将当前符号压入符号栈中,否则先将符号栈栈顶的符合和数栈中栈顶的两个数字进行计算,并将计算结果压入数栈中
            while (index < expression.length()) {
                Character ch = expression.charAt(index);
                if (isOperator(ch)) {
                    if (!operatorStack.empty()) {
                        if (getPriority(ch) <= getPriority(operatorStack.peek())) {
                            doCal(numStack, operatorStack, getPriority(ch));
                        }
                    }
                    operatorStack.push(ch);
                } else {
                    //数字
                    if (49 <= ch && ch <= 58) {
                        numStack.push(ch - 48);
                    } else {
                        throw new RuntimeException("表达式不符合规范");
                    }
                }
                index++;
            }
            //计算栈中剩余数据
            doCal(numStack, operatorStack, 0);
            System.out.printf("%s=%d\n", expression, numStack.pop());
        }
    }

    private static void doCal(Stack<Integer> numStack, Stack<Character> operatorStack, int priority) {
        Integer num1 = numStack.pop();
        Integer num2 = numStack.pop();
        Character operator = operatorStack.pop();
        Integer result = calculate(num1, num2, operator);
        numStack.push(result);
        if (priority != 0 && !operatorStack.isEmpty() && priority <= getPriority(operatorStack.peek())) {
            doCal(numStack, operatorStack, priority);
        }
    }

    private static <E> Stack<E> reverseStack(Stack<E> sourceStack) {
        Stack<E> stack = new Stack<>();
        while (!sourceStack.isEmpty()) {
            stack.push(sourceStack.pop());
        }
        return stack;
    }

    private static boolean isOperator(Character ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static Integer calculate(Integer num1, Integer num2, Character operator) {
        System.out.printf("%d %s %d\n", num2, operator, num1);
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num2 - num1;
            case '*':
                return num1 * num2;
            case '/':
                return num2 / num1;
            default:
        }
        return null;
    }

    public static int getPriority(Character ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                throw new RuntimeException("无法识别的符号:" + ch);
        }
    }
}

package demo.interpreter;

import java.util.HashMap;
import java.util.Stack;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class Calculator {

    private Expression expression;

    public Calculator(String expressionStr) {
        //运算先后顺序
        Stack<Expression> stack = new Stack<>();
        //表达式拆分为字符数组
        char[] charArray = expressionStr.toCharArray();
        Expression left;
        Expression right;
        for (int i = 0; i < charArray.length; i++) {
            switch (charArray[i]) {
                case '+':
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(charArray[++i]));
                    stack.push(new AddExpression(left, right));
                    break;
                case '-':
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(charArray[++i]));
                    stack.push(new SubExpression(left, right));
                    break;
                case '*':
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(charArray[++i]));
                    stack.push(new MultiplyExpression(left, right));
                    break;
                case '/':
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(charArray[++i]));
                    stack.push(new DivideExpression(left, right));
                    break;
                default:
                    //如果是一个变量 就创建一个var对象 并放入栈中
                    stack.push(new VarExpression(String.valueOf(charArray[i])));
            }
        }
        //遍历完整个charArray 就得到最终的表达式
        this.expression = stack.pop();
    }

    public int run(HashMap<String, Integer> var) {
        return this.expression.interpreter(var);
    }
}

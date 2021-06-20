package demo.interpreter;

import java.util.HashMap;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class SymbolExpression extends Expression{

    protected Expression  left;

    protected Expression right;

    public SymbolExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return 0;
    }
}

package demo.interpreter;

import java.util.HashMap;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class MultiplyExpression extends SymbolExpression{

    public MultiplyExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return super.left.interpreter(var) * super.right.interpreter(var);
    }
}

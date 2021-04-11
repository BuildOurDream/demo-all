package test.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-04-08
 */
public class AppExpressionParser {

    public static void main(String[] args) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression1 = parser.parseExpression("'Hello World'");
        String value = expression1.getValue(String.class);
        System.out.println(value);

        Expression expression2 = parser.parseExpression("'Hello World'.length()");
        System.out.println(expression2.getValue());

        Expression expression3 = parser.parseExpression("'Hello World'.length()*10");
        System.out.println(expression3.getValue());

        Expression expression4 = parser.parseExpression("'Hello World'.length()>10");
        System.out.println(expression4.getValue());

        Expression expression5 = parser.parseExpression("'Hello World'.length()>10 and 'Hello World'.length() < 20");
        System.out.println(expression5.getValue());

        Expression expression6 = parser.parseExpression("'Hello World'.concat('!')");
        System.out.println(expression6.getValue());

        Expression expression7 = parser.parseExpression("'Hello World'.toUpperCase()");
        System.out.println(expression7.getValue());

        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("greeting", "Hello World");
        String msg = (String)parser.parseExpression("#greeting.substring(6)").getValue(context);
        System.out.println(msg);

        User user = new User();
        StandardEvaluationContext userContext = new StandardEvaluationContext(user);
        parser.parseExpression("country").setValue(userContext, "China");
        parser.parseExpression("name").setValue(userContext, "JingXin");
        parser.parseExpression("age").setValue(userContext, "29");
        parser.parseExpression("timeZone").setValue(userContext, "GMT+8");
        System.out.println(user);
        System.out.println(parser.parseExpression("age").getValue(user));
        Expression expression8 = parser.parseExpression("name == 'JingX2in'");
        System.out.println(expression8.getValue(userContext, Boolean.class));

        StandardEvaluationContext propsContext = new StandardEvaluationContext(user);
        propsContext.setVariable("system", System.getProperties());
        Expression expression = parser.parseExpression("#system['user.country']");
        parser.parseExpression("language").setValue(userContext, expression.getValue(propsContext));
        System.out.println(user);
    }
}

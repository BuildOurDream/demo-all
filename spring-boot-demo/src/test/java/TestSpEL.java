import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.*;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import test.spel.User;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-04-08
 */
//@SpringBootTest(classes = TestApplication.class)
public class TestSpEL {

    @Resource
    private ApplicationContext applicationContext;

    SpelExpressionParser parser;

    StandardEvaluationContext context;

    @BeforeEach
    void before() {
        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
                this.getClass().getClassLoader(), true, true, Integer.MAX_VALUE);
        parser = new SpelExpressionParser(config);
        User user = new User();
        user.setName("root");
        user.fillObjs(5);
        User user2 = new User();
        user2.setName("guest");
        context = new StandardEvaluationContext(user);
        context.setVariable("random", RandomUtil.class);
        context.setVariable("guest", user2);
        context.setVariable("str", StrUtil.class);
    }

    @Test
    public void test1() {
        // Create and set a calendar
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);

// The constructor arguments are name, birthday, and nationality.
        Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");

        ExpressionParser parser = new SpelExpressionParser();

        EvaluationContext context = new StandardEvaluationContext(tesla);

        Expression exp = parser.parseExpression("name == 'Nikola Tesla'");
        boolean result = exp.getValue(context, Boolean.class); // evaluates to true
        System.out.println(result);
    }

    @Data
    @AllArgsConstructor
    static class Inventor {
        private String name;
        private Date birth;
        private String address;
        private List<String> inventions;

        public Inventor(String name, Date birth, String address) {
            this.name = name;
            this.birth = birth;
            this.address = address;
        }
    }

    class Simple {
        public List<Boolean> booleanList = new ArrayList<Boolean>();
    }

    @Test
    @DisplayName("cc")
    void test2() {
        Simple simple = new Simple();

        simple.booleanList.add(true);

        StandardEvaluationContext simpleContext = new StandardEvaluationContext(simple);

// false is passed in here as a string. SpEL and the conversion service will
// correctly recognize that it needs to be a Boolean and convert it
        parser.parseExpression("booleanList[0]").setValue(simpleContext, "FALsE");

// b will be false
        Boolean b = simple.booleanList.get(0);
        System.out.println(b);
    }

    class Demo {
        public List<String> list = Lists.newArrayList("123");
    }

    @Test
    @DisplayName("dd")
    void test3() {
        // Turn on:
// - auto null reference initialization
// - auto collection growing
        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
                this.getClass().getClassLoader(), true, true, Integer.MAX_VALUE);
        SpelExpressionParser parser = new SpelExpressionParser(config);

        Expression expr = parser.parseExpression("#sys['user.region']");

        Inventor tesla = new Inventor("Nikola Tesla", new Date(), "Serbian");
        Properties properties = System.getProperties();
        System.out.println(properties);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("sys", properties);
        User message = new User();

        Object payload = expr.getValue(context);
        System.out.println(payload);
    }

    @Test
    void test4() {
        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
                this.getClass().getClassLoader(), true, true, Integer.MAX_VALUE);

        ExpressionParser parser = new SpelExpressionParser(config);

        Inventor tesla = new Inventor("Nikola Tesla", new Date(), "Serbian", Lists.newArrayList("aa"));
// Inventions Array
        StandardEvaluationContext teslaContext = new StandardEvaluationContext(tesla);

// evaluates to "Induction motor"
        String invention = parser.parseExpression("inventions[0]").getValue(
                teslaContext, String.class);
        System.out.println(invention);
// Members List
//        StandardEvaluationContext societyContext = new StandardEvaluationContext(ieee);

// evaluates to "Nikola Tesla"
//        String name = parser.parseExpression("Members[0].Name").getValue(
//                societyContext, String.class);

// List and Array navigation
// evaluates to "Wireless communication"
//        String invention = parser.parseExpression("Members[0].Inventions[6]").getValue(
//                societyContext, String.class);
    }

    @Test
    void testList() {
        ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3, 4, 5);
        context = new StandardEvaluationContext();
        List numbers = (List) parser.parseExpression("{1,2,3,6}").getValue(context);
        System.out.println(numbers);
        int[][] numbers1 = (int[][]) parser.parseExpression("new int[4][5]").getValue(context);
        System.out.println(Arrays.toString(numbers1));

        String c = parser.parseExpression("'abc'.substring(2, 3)").getValue(String.class);
        System.out.println(c);

        boolean falseValue = parser.parseExpression("true and true").getValue(Boolean.class);

        // evaluates to false
        System.out.println(falseValue);

        boolean trueValue = parser.parseExpression("true or false").getValue(Boolean.class);
        System.out.println(trueValue);

        Double two = parser.parseExpression("1e4").getValue(Double.class); // 2
        System.out.println(two);

        Class dateClass = parser.parseExpression("T(java.util.Date)").getValue(Class.class);
        System.out.println(dateClass);

        Inventor einstein = parser.parseExpression(
                "new org.spring.samples.spel.inventor.Inventor('Albert Einstein', new java.util.Date(),'German')")
                .getValue(Inventor.class);
        System.out.println(einstein);
    }

    @Test
    void testRoot() {
        // create an array of integers
        List<Integer> primes = new ArrayList<Integer>();
        primes.addAll(Arrays.asList(2, 3, 5, 7, 11, 13, 17));

// create parser and set variable primes as the array of integers
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("primes", primes);

// all prime numbers > 10 from the list (using selection ?{...})
// evaluates to [11, 13, 17]
        List<Integer> primesGreaterThanTen = (List<Integer>) parser.parseExpression(
                "#primes.?[#this>10]").getValue(context);
        System.out.println(primesGreaterThanTen);
    }

    @Test
    void testFunction() throws NoSuchMethodException, AccessException {
        context.registerFunction("ccc", TestSpEL.class.getDeclaredMethod("toUpperCase", String.class));
        System.out.println(parser.parseExpression("#ccc(' niHao ')").getValue(context, String.class));
        CustomBeanResolver customBeanResolver = new CustomBeanResolver();
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        BeanResolver beanResolver = context.getBeanResolver();
        System.out.println(Arrays.toString(applicationContext.getBeanDefinitionNames()));
        System.out.println(beanResolver);
        System.out.println(parser.parseExpression("#systemProperties").getValue(context));
        System.out.println(parser.parseExpression("@user").getValue(context));
        System.out.println(parser.parseExpression("@user.age").getValue(context));
        System.out.println(parser.parseExpression("@user.name").getValue(context));
    }

    public class CustomBeanResolver implements BeanResolver {

        @Override
        public Object resolve(EvaluationContext context, String beanName) throws AccessException {
            return new User();
        }
    }

    @Test
    void testOther() throws NoSuchMethodException {
        System.out.println(parser.parseExpression("language ?: #str.toCamelCase('abc_cd_d')").getValue(context));
        System.out.println(parser.parseExpression("name").getValue(context));
        System.out.println(parser.parseExpression("#guest?.name").getValue(context));
        System.out.println(parser.parseExpression("#guest2?.name").getValue(context));

        System.out.println(parser.parseExpression("#root").getValue(context));
        System.out.println(parser.parseExpression("hobbies.?[#this >= 'ccc']").getValue(context));
        System.out.println(parser.parseExpression("objs.?[#this.o2 >= 3]").getValue(context));
        System.out.println(parser.parseExpression("objs.![#this.o1]").getValue(context));
        System.out.println(parser.parseExpression("objs[12]").getValue(context));

    }

    public static String toUpperCase(String str) {
        if (StrUtil.isBlank(str)) {
            return str;
        }
        return str.trim().toUpperCase();
    }

    @Test
    void testTemplate() {
        System.out.println(parser.parseExpression("hello #{name}", new TemplateParserContext()).getValue(context));
        System.out.println(parser.parseExpression("#random.getRandom()").getValue(context));
        System.out.println(parser.parseExpression("random number is #{T(java.lang.Math).random()}", new TemplateParserContext()).getValue(context));
        System.out.println(parser.parseExpression("T(java.lang.Math).random()").getValue(context));
        System.out.println(parser.parseExpression("random number is %<T(java.lang.Math).random()>", new customParserContext()).getValue(context));
        System.out.println(parser.parseExpression("#random.randomChar()").getValue(context));
    }

    class customParserContext implements ParserContext {

        @Override
        public boolean isTemplate() {
            return true;
        }

        @Override
        public String getExpressionPrefix() {
            return "%<";
        }

        @Override
        public String getExpressionSuffix() {
            return ">";
        }
    }
}

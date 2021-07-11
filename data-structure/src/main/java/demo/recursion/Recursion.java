package demo.recursion;

/**
 * <p>递归</p>
 *
 * @Author J.Star
 * @Date 2021-06-26
 */
public class Recursion {

    public static void main(String[] args) {
        print(16000);
//        System.out.println(factorial(8));
    }

    /**
     * 递归打印
     *
     * @param num
     */
    public static void print(int num) {
        if (num > 1) {
            print(num - 1);
        }
        System.out.println(num);
    }

    /**
     * 阶乘
     *
     * @param seed
     * @return
     */
    public static int factorial(int seed) {
        if (seed == 1) {
            System.out.print(seed + " = ");
            return 1;
        } else {
            System.out.print(seed + " * ");
            return seed * factorial(seed - 1);
        }
    }
}

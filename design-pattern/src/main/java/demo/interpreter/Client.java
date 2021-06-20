package demo.interpreter;

import java.util.HashMap;
import java.util.Scanner;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class Client {

    private static  Scanner scanner;

    public static void main(String[] args) {
        String exp = getExp();
        HashMap<String, Integer> var = getValue(exp);
        Calculator calculator = new Calculator(exp);
        System.out.println(calculator.run(var));
    }

    private static HashMap<String, Integer> getValue(String exp) {
        HashMap<String, Integer> map = new HashMap<>();

        for (Character ch: exp.toCharArray()) {
            if (ch != '+' && ch != '-' && ch != '*' && ch != '/') {
                System.out.println("请输入" + ch + "的值:");
                scanner = new Scanner(System.in);
                int val = scanner.nextInt();
                map.put(ch.toString(), val);
            }
        }
        if (scanner != null) {
            scanner.close();
        }
        return map;
    }

    private static String getExp() {
        System.out.println("请输入表达式:");
        scanner = new Scanner(System.in);
        String exp = scanner.nextLine();
        return exp;
    }
}

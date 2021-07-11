package demo.recursion;

import java.util.Arrays;

/**
 * <p>八皇后问题</p>
 *
 * @Author J.Star
 * @Date 2021-06-27
 */
public class Queue8 {

    private int max = 8;
    private int[] location = new int[max];
    private int count = 0;
    private int checkCount = 0;

    public static void main(String[] args) {
        Queue8 queue8 = new Queue8();
        queue8.locate(0);
        System.out.printf("尝试%d次,共找到%d种方法\n", queue8.checkCount, queue8.count);
    }

    /**
     * 打印位置
     */
    public void print() {
        count++;
        System.out.println(Arrays.toString(location));
    }

    /**
     * 放入皇后
     *
     * @param n 放置第n个皇后
     */
    public void locate(int n) {
        if (n == max) {
            print();
            return;
        }
        //依次放入皇后,并判断是否冲突
        for (int i = 0; i < max; i++) {
            location[n] = i;
            if (isNotConflict(n)) {
                locate(n + 1);
            }
        }
    }

    /**
     * 检查冲突
     */
    public boolean isNotConflict(int n) {
        checkCount++;
        for (int i = 0; i < n; i++) {
            if (location[i] == location[n] || Math.abs(n - i) == Math.abs(location[n] - location[i])) {
                return false;
            }
        }
        return true;
    }

}

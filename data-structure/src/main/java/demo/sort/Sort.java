package demo.sort;

import cn.hutool.core.util.RandomUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-27
 */
public class Sort {

    public static void main(String[] args) {
        int[] array = getArr(10);
        System.out.println("===开始排序===");
        System.out.println("初始顺序:" + Arrays.toString(array));
        Instant instant = Instant.now();
//        BubbleSort.sort(array);
//        SelectSort.sort(array);
//        InsertSort.sort(array);
//        ShellSort.sort(array);
        QuickSort.sort(array, 0, array.length - 1);
        System.out.printf("===排序完成,耗时:%dms===\n", Duration.between(instant, Instant.now()).toMillis());
        System.out.println(Arrays.toString(array));
    }

    private static int[] getArr(int length) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
//            arr[i] = RandomUtil.randomInt(length * 3);
            arr[i] = RandomUtil.randomInt(-100, 100);
        }
        return arr;
    }

    /**
     * 冒泡排序
     */
    public static class BubbleSort {
        public static void sort(int[] array) {
            for (int i = 0; i < array.length - 1; i++) {
                int val = array[i];
                //是否进行过交换 没有进行过交换说明已经拍好序了 直接推出到外层循环
                boolean changed = false;
                for (int j = i + 1; j < array.length; j++) {
                    int temp = array[j];
                    if (val > temp) {
                        array[i] = temp;
                        array[j] = val;
                        val = temp;
                        changed = true;
                    }
                }
                if (!changed) {
                    break;
                }
//            System.out.printf("第%d次排序后结果:%s\n", i + 1, Arrays.toString(array));
            }
        }
    }

    /**
     * 选择排序
     */
    public static class SelectSort {
        public static void sort(int[] array) {
            for (int i = 0; i < array.length - 1; i++) {
                int minVal = array[i];
                int minIndex = i;
                for (int j = i + 1; j < array.length; j++) {
                    int temp = array[j];
                    if (minVal > temp) {
                        minIndex = j;
                        minVal = temp;
                    }
                }
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
//            System.out.printf("第%d次排序后结果:%s\n", i + 1, Arrays.toString(array));
            }
        }
    }

    /**
     * 插入排序
     */
    public static class InsertSort {
        public static void sort(int[] array) {
            for (int i = 1; i < array.length; i++) {
                int preIndex = i - 1;
                int insertVal = array[i];
                while (preIndex >= 0 && array[preIndex] > insertVal) {
                    int preVal = array[preIndex];
                    array[preIndex] = insertVal;
                    array[preIndex + 1] = preVal;
                    preIndex--;
                }
            }
        }
    }

    /**
     * 希尔排序
     */
    public static class ShellSort {
        public static void sort(int[] array) {
            int gap = array.length;
            while (true) {
                gap /= 2;
                for (int i = 0; i < gap; i++) {
                    for (int j = i + gap; j < array.length; j += gap) {
                        int insertVal = array[j];
                        int preIndex = j - gap;
                        while (preIndex >= 0 && array[preIndex] > insertVal) {
                            array[j] = array[preIndex];
                            array[preIndex] = insertVal;
                            preIndex -= gap;
                        }
                    }
                }
                if (gap == 1) {
                    break;
                }
            }
        }
    }

    /**
     * 快速排序
     */
    public static class QuickSort {
        public static void sort(int[] array, int start, int end) {
            int pivot = array[start];
            int left = start;
            int right = end;
            while (left < right) {
                while (left < right && array[right] > pivot) {
                    right--;
                }
                while (left < right && array[left] < pivot) {
                    left++;
                }
                if (array[left] == array[right]) {
                    if (left < right) {
                        left++;
                    } else {
                        break;
                    }
                } else {
                    int temp = array[left];
                    array[left] = array[right];
                    array[right] = temp;
                }
            }
            System.out.println(Arrays.toString(array));
            if (left - 1 > start) {
                sort(array, start, left - 1);
            }
            if (right + 1 < end) {
                sort(array, right + 1, end);
            }
        }
    }
}

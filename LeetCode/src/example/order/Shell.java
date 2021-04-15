package example.order;

import java.util.Arrays;

public class Shell {
    public static void main(String[] args) {
        int[] arr = new int[] {9,4,6,8,3,10,4,6,15,67,4,61,4,6,8,7,86,1};
        shellSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void shellSort(int[] arr) {
    int length = arr.length;
    int temp;
    for (int step = length / 2; step >= 1; step /= 2) {
        for (int i = step; i < length; i++) {
            temp = arr[i];
            int j = i - step;
            while (j >= 0 && arr[j] > temp) { // 在0~i这个范围内开始直接插入排序
                arr[j + step] = arr[j];
                j -= step;
            }
            arr[j + step] = temp;
        }
    }
}
}

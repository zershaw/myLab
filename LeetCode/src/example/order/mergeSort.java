package example.order;

import java.util.Arrays;

public class mergeSort {
    public static void main(String[] args) {
        int[] arr = new int[] {9,4,6,8,3,10,4,6,15,67,4,61,4,6,8,7,86,1};
        int[] res = MergeSort(arr,0,arr.length - 1);
        System.out.println(Arrays.toString(res));
    }
    //merge函数实际上是将两个有序数组合并成一个有序数组
    //因为数组有序，合并很简单，只要维护几个指针就可以了
    public static int[] MergeSort(int[] nums, int l, int r){
        if(l == r) return new int[]{nums[l]};//单个数据直接返回
        int mid =  (r + l) / 2;
        int[] left = MergeSort(nums, l, mid); //前一半数组
        int[] right = MergeSort(nums, mid + 1, r);//
        int[] res = new int[r - l + 1]; //要返回的数组

        int i = 0, j = 0, x = 0;
        while (i < left.length && j < right.length) {
            if(left[i] <= right[j]){
                res[x] = left[i];
                x++;
                i++;
            }else{
                res[x] = right[j];
                x++;
                j++;
            }
        }
        // 对剩余数组的补充遍历
        while(i < left.length){
            res[x++] = left[i++];
        }
        while(j < right.length){
            res[x++] = right[j++];
        }
        return res;
    }
}
package example.order;
import java.util.Arrays;

public class quickSort {
    public static void main(String[] args) {
        int[] arr = new int[] {9,4,6,8,3,10,4,6,15,67,4,61,4,6,8,7,86,1};
        quickSort(arr,0,arr.length - 1);
        System.out.println(Arrays.toString(arr));

    }
    public static void quickSort(int[] arr,int left,int right) {
        int p,i,j,temp;

        if(left >= right) {
            return;
        }
        //p就是基准数,这里就是每个数组的第一个
        p = arr[left];
        i = left;
        j = right;
        while(i < j) {
            //右边当发现小于p的值时停止循环
            while(arr[j] >= p && i < j) {
                j--;
            }

            //这里一定是右边开始，上下这两个循环不能调换（下面有解析，可以先想想）

            //左边当发现大于p的值时停止循环
            while(arr[i] <= p && i < j) {
                i++;
            }
            temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        arr[left] = arr[i];//这里的arr[i]一定是停小于p的，经过i、j交换后i处的值一定是小于p的(j先走)
        arr[i] = p;
        quickSort(arr,left,j-1);  //对左边快排
        quickSort(arr,j+1,right); //对右边快排

    }
}
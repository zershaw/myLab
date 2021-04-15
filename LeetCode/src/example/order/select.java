package example.order;

public class select {
    public static void selectionSort(int[] a){

        for (int i = 0; i < a.length -1; i++) {
            int min = a[i];
            int minIdx = i; //  最小元素的坐标
            for (int j = i; j < a.length; j++) {
                if(a[j] < min){
                    min = a[j];
                    minIdx = j;
                }
            } //for
            if(a[i] != min){
                int temp = a[i];
                a[i] = min;
                a[minIdx] = temp;
            }
        }//for
    }
}

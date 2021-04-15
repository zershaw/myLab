package example.order;

public class insert {
    public  static void insertSort(int[] a){
        int idx,insertElement;

        for (int i = 1; i <a.length ; i++) {
            idx = i - 1;
            insertElement = a[i];
            while(idx >=0 && insertElement < a[idx]){
                a[idx + 1] = a[idx];
                idx--;
            }
            a[idx + 1] = insertElement;
        }
    }

}

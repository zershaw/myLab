package example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MyComparator  implements Comparator<int[]> {
    @Override
    public int compare(int[] grid1,int[] grid2){
        int dis1 = Math.abs(grid1[0]-0) + Math.abs(grid1[1]-0);
        int dis2 = Math.abs(grid2[0]-0) + Math.abs(grid2[1]-0);
        return dis1 - dis2;
    }

}

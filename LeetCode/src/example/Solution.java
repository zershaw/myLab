package example;

import java.util.Arrays;
import java.util.Comparator;

public class Solution {

    public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
        int[][] res = new int[R*C][2];
        int index = 0;
        for(int i = 0; i < R; i++){
            for(int j = 0; j < C; j++){
                int[] xy = {i,j};
                res[index++] = xy;
            }
        }

        Comparator cmp = new MyComparator();
        Arrays.sort(res,cmp);
        // Arrays.sort(res, new Comparator<int[]>(){
        //     @Override
        //     public int compare(int[] grid1,int[] grid2){
        //     int dis1 = Math.abs(grid1[0]-r0) + Math.abs(grid1[1]-c0);
        //     int dis2 = Math.abs(grid2[0]-r0) + Math.abs(grid2[1]-c0);
        //     return dis1 - dis2;
        //     }
        // });
        return res;
    }
}

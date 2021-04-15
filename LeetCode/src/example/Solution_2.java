package example;

import java.util.Scanner;

public class Solution_2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while ( in .hasNext()) {
            int N = in .nextInt(); //和
            int L = in .nextInt(); //最小长度
            boolean flag = false;
            for (int i = L; i <= 100; i++) {
                if ((2 * N + i - i * i) % (2 * i) == 0) { //根据等差序列公式，
                    // 可以整除
                    flag = true;
                    int a1 = (2 * N + i - i * i) / (2 * i);
                    if (a1 < 0) continue;
                    for (int j = 0; j < i - 1; j++) {
                        int a = a1 + j;
                        System.out.print(a + " ");
                    }
                    System.out.println(a1 + i - 1);
                    break;
                }
            }
            if (flag == false)
                System.out.println("No");
        }
    }
}

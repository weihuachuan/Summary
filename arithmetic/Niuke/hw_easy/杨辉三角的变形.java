package Niuke.hw_easy;

import java.util.Scanner;

/*
* 本题是找规律的题，只要往下再写几行就可以看出奇偶的规律，而且每行只需要写前几个就可以了，因为题目问的是第一个偶数的index。
于是我们会发现，只有n为1，2时，没有出现偶数，剩下的按照2 3 2 4的规律每四行循环一次。
* */
public class 杨辉三角的变形 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int num = in.nextInt();
            if (num == 1 || num == 2) {
                System.out.println(-1);
                continue;
            } else if (num % 4 == 1 || num % 4 == 3) {
                System.out.println(2);
                continue;
            } else if (num % 4 == 0) {
                System.out.println(3);
                continue;
            } else if (num % 4 == 2) {
                System.out.println(4);
                continue;
            }
        }
    }
}

package Niuke.acm.acm6;

import java.util.Scanner;

/*
*
*   有一个N个整数的数组
  和一个长度为M的窗口
  窗口从数组内的第一个数开始滑动
  直到窗口不能滑动为止
  每次滑动产生一个窗口  和窗口内所有数的和
  求窗口滑动产生的所有窗口和的最大值

  输入描述
  第一行输入一个正整数N
  表示整数个数  0<N<100000
  第二行输入N个整数
  整数取值范围   [-100,100]
  第三行输入正整数M
  M代表窗口的大小
  M<=100000 并<=N

  输出描述
  窗口滑动产生所有窗口和的最大值

  示例一
  输入
  6
  12 10 20 30 15 23
  3

  输出
  68

* */
public class 窗口和的最大值 {
    // 窗口和的最大值
    // 解题思路：遍历数组，求出所有窗口数的和，取最大值
    public static void test032() {
        Scanner sc = new Scanner(System.in);
        int len = Integer.parseInt(sc.nextLine());
        String[] split = sc.nextLine().split(" ");
        int num = Integer.parseInt(sc.nextLine());
        int res = 0;
        // 遍历数组
        for (int i = 0; i <= len - num; i++) {
            int sum = 0;
            for (int j = 0; j < num; j++) {
                // 窗口所有数之和
                sum = sum + Integer.parseInt(split[i + j]);
            }
            // 取最大值
            res = Math.max(res, sum);
        }
        System.out.println(res);
    }

    public static void main(String[] args) {
        test032();
    }

}

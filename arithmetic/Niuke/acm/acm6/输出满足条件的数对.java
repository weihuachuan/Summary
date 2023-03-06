package Niuke.acm.acm6;

import java.util.Scanner;

/*
*   同一个数轴x有两个点的集合A={A1,A2,...,Am}和B={B1,B2,...,Bm}
  A(i)和B(j)均为正整数
  A、B已经按照从小到大排好序，AB均不为空
  给定一个距离R 正整数，列出同时满足如下条件的
  (A(i),B(j))数对
  1. A(i)<=B(j)
  2. A(i),B(j)之间距离小于等于R
  3. 在满足1，2的情况下每个A(i)只需输出距离最近的B(j)
  4. 输出结果按A(i)从小到大排序

  输入描述
  第一行三个正整数m n R
  第二行m个正整数 表示集合A
  第三行n个正整数 表示集合B

  输入限制 1<=R<=100000
  1<=n,m<=100000
  1<= A(i),B(j) <= 1000000000

  输出描述
  每组数对输出一行 A(i)和B(j)
  以空格隔开

  示例一
  输入
  4 5 5
  1 5 5 10
  1 3 8 8 20

  输出
  1 1
  5 8
  5 8
*/
public class 输出满足条件的数对 {
    // 输出满足条件的数对
    // 解题思路：双层for循环，依次遍历2个数组，找出满足条件的数对
    // 这题没说B数组中的数只能用一次，所以解法应该没问题
    public static void test035() {
        Scanner sc = new Scanner(System.in);
        String[] split = sc.nextLine().split(" ");
        String[] a = sc.nextLine().split(" ");
        String[] b = sc.nextLine().split(" ");
        int aLen = Integer.parseInt(split[0]);
        int bLen = Integer.parseInt(split[1]);
        int r = Integer.parseInt(split[2]);
        // 遍历数组A
        for (int i = 0; i < aLen; i++) {
            // 遍历数组B
            for (int j = 0; j < bLen; j++) {
                int numI = Integer.parseInt(a[i]);
                int numJ = Integer.parseInt(b[j]);
                if (numI <= numJ && (numJ - numI <= r)) {
                    System.out.println(numI + " " + numJ);
                    break;
                }
            }
        }

    }

}

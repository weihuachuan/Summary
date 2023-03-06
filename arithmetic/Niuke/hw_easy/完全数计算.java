package Niuke.hw_easy;

import java.util.Scanner;

/*
* 描述
完全数（Perfect number），又称完美数或完备数，是一些特殊的自然数。

它所有的真因子（即除了自身以外的约数）的和（即因子函数），恰好等于它本身。

例如：28，它有约数1、2、4、7、14、28，除去它本身28外，其余5个数相加，1+2+4+7+14=28。

输入n，请输出n以内(含n)完全数的个数。


输入描述：
输入一个数字n

输出描述：
输出不超过n的完全数的个数

示例1
输入：
1000
复制
输出：
3
* */
public class 完全数计算 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int n = in.nextInt();

            //第一个完全数是6，若小于6则输出0
            if (n < 6) {
                System.out.println(0);
                continue;
            }

            int count = 0;    //计数变量
            for (int t = 6; t <= n; t++) {
                int sum = 0;
                //统计因数的和，计数到该数的1/2即可
                for (int i = 1; i <= t / 2; i++) {
                    if (t % i == 0)
                        sum += i;
                }
                if (sum == t)
                    count++;
            }

            //输出结果
            System.out.println(count);
        }
    }
}

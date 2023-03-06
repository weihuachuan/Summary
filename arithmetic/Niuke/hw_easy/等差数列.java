package Niuke.hw_easy;

import java.util.Scanner;

/*
* 描述
等差数列 2，5，8，11，14。。。。
（从 2 开始的 3 为公差的等差数列）
输出求等差数列前n项和


数据范围：
1
≤
�
≤
1000

1≤n≤1000
输入描述：
输入一个正整数n。

输出描述：
输出一个相加后的整数。

示例1
输入：
2
复制
输出：
7
复制
说明：
2+5=7
示例2
输入：
275
复制
输出：
113575
复制
说明：
2+5+...+821+824=113575
*/
public class 等差数列 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int length = sc.nextInt(); // n
            int first = 2, last = 3 * length - 1; // a1, an
            System.out.println((first + last) * length / 2);
        }
        sc.close();
    }
}

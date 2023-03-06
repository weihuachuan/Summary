package Niuke.acm.acm4;

import java.util.Scanner;

/*
*   小明从糖果盒中随意抓一把糖果
  每次小明会取出一半的糖果分给同学们
  当糖果不能平均分配时
  小明可以从糖果盒中(假设盒中糖果足够)取出一个或放回一个糖果
  小明至少需要多少次(取出放回和平均分配均记一次)能将手中糖果分至只剩一颗

  输入描述：
  抓取糖果数(小于1000000)：15
  输出描述：
  最少分至一颗糖果的次数：5

  示例1：
  输入
  15
  输出
  5
  备注
  解释：(1) 15+1=16;
  (2) 16/2=8;
  (3) 8/2=4;
  (4) 4/2=2;
  (5) 2/2=1;

* */
public class 均分糖果 {
    // 这道题通过率好像只有85%，我也不知道原因
    // 均分糖果
    // 解题思路：当糖果数n不是偶数时，当(n + 1) / 2 % 2 == 0 说明num+1后除以2还是偶数，所以选择n+1，否则n-1
    public static void test050() {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int count = 0;
        while (num != 1) {
            // 当num=3时，不符合一下规则，但是很容易算出是2次，所以直接加2并结束
            if (num == 3) {
                System.out.println(count + 2);
                return;
            }
            // 当num不是偶数
            if (num % 2 != 0) {
                // 当(num + 1) / 2 % 2 == 0 说明num+1后除以2还是偶数，所以选择+1，否则-1
                if ((num + 1) / 2 % 2 == 0) {
                    num++;
                } else {
                    num--;
                }
                // 无论是+1还是-1，count都要累加1
                count++;
            } else { // 当nums是偶数，除以2，count累加1
                num /= 2;
                count++;
            }
        }
        System.out.println(count);
    }

}

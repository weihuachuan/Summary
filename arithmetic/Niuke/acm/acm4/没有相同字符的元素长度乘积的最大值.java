package Niuke.acm.acm4;

import java.util.Scanner;

/*
*   给定一个元素类型为小写字符串的数组
  请计算两个没有相同字符的元素长度乘积的最大值
  如果没有符合条件的两个元素返回0

  输入描述
  输入为一个半角逗号分割的小写字符串数组
  2<= 数组长度 <=100
  0< 字符串长度 <=50
  输出描述
  两个没有相同字符的元素长度乘积的最大值

  示例一
  输入
  iwdvpbn,hk,iuop,iikd,kadgpf
  输出
  14
  说明
  数组中有5个元组  第一个和第二个元素没有相同字符
  满足条件 输出7*2=14

* */
public class 没有相同字符的元素长度乘积的最大值 {
    // 没有相同字符的元素长度乘积的最大值
    // 解题思路：一一比对元素，看一个元素是否包含另一个元素，如果没有则保留这两个元素的长度的乘积，保留最大的
    public static void test048() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] split = line.split(",");
        int sum = 0;
        for (int i = 0; i < split.length; i++) {
            for (int j = i + 1; j < split.length; j++) {
                // 两个元素有没有相同字符标识位
                boolean flag = true;
                String first = split[i];
                String next = split[j];
                // 遍历一个元素的全部字符
                for (int k = 0; k < first.length(); k++) {
                    // 判断是否包含这些字符
                    if (next.contains(first.charAt(k) + "")) {
                        // 有相同的字符
                        flag = false;
                        break;
                    }
                }
                // 没有相同的字符
                if (flag) {
                    // 计算两个元素长度的乘积，保留最大的
                    sum = Math.max(sum, first.length() * next.length());
                }
            }
        }
        System.out.println(sum);
    }

}

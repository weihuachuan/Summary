package Niuke.acm.acm2;

import java.util.Scanner;

/*
*   输入一串字符串
  字符串长度不超过100
  查找字符串中相同字符连续出现的最大次数

  输入描述
    输入只有一行，包含一个长度不超过100的字符串

  输出描述
    输出只有一行，输出相同字符串连续出现的最大次数

   说明：
 输出

   示例1：
 输入
   hello
 输出
   2

示例2：
  输入
   word
  输出
   1

 示例3：
  输入
    aaabbc
   输出
    3

字符串区分大小写

* */
public class 字符连续出现最大次数 {
    // 字符连续出现最大次数
    public static void test075() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] split = line.split("");
        // 出现次数，初始值为1
        int sum = 1;
        // 最大次数
        int res = 0;
        for (int i = 0; i < split.length; i++) {
            // 连续相同字符串
            while (i + 1 < split.length && split[i].equals(split[i + 1])) {
                sum++;
                i++;
            }
            // 比较出最大的
            res = Math.max(res, sum);
            // 初始化出现次数
            sum = 1;
        }
        System.out.println(res);
    }

}

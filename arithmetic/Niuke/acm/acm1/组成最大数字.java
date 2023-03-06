package Niuke.acm.acm1;

import java.util.Scanner;

/*
* 小组中每位都有一张卡片
卡片是6位以内的正整数
将卡片连起来可以组成多种数字
计算组成的最大数字

输入描述：
    ","分割的多个正整数字符串
    不需要考虑非数字异常情况
    小组种最多25个人

输出描述：
     最大数字字符串

示例一
 输入
  22,221
 输出
  22221

示例二
  输入
    4589,101,41425,9999
  输出
    9999458941425101
*/
public class 组成最大数字 {
    // 组成最大数字
    public static void test0843() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] split = line.split(",");
        // 类似于冒泡排序，用双层for循环，将最大的组合往前放
        for (int i = 0; i < split.length - 1; i++) {
            for (int j = i + 1; j < split.length; j++) {
                String res1 = split[i] + split[j];
                String res2 = split[j] + split[i];
                if (Integer.parseInt(res1) < Integer.parseInt(res2)) {
                    String value = split[i];
                    split[i] = split[j];
                    split[j] = value;
                }
            }
        }
        for (int i = 0; i < split.length; i++) {
            System.out.print(split[i]);
        }
    }

}

package Niuke.acm.acm6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
*   给定一个仅包含0和1的n*n二维矩阵
  请计算二维矩阵的最大值
  计算规则如下
  1、每行元素按下标顺序组成一个二进制数(下标越大约排在低位)，
  二进制数的值就是该行的值，矩阵各行之和为矩阵的值
  2、允许通过向左或向右整体循环移动每个元素来改变元素在行中的位置
  比如
  [1,0,1,1,1]   向右整体循环移动两位  [1,1,1,0,1]
  二进制数为11101 值为29
  [1,0,1,1,1]   向左整体循环移动两位  [1,1,1,1,0]
  二进制数为11110 值为30

  输入描述
  1.数据的第一行为正整数，记录了N的大小
  0<N<=20
  2.输入的第2到n+1行为二维矩阵信息
  行内元素边角逗号分割

  输出描述
  矩阵的最大值

  示例1
  输入
  5
  1,0,0,0,1   24
  0,0,0,1,1   24
  0,1,0,1,0   20
  1,0,0,1,1   28
  1,0,1,0,1   26

  输出
  122

  说明第一行向右整体循环移动一位，得到最大值  11000  24

  因此最大122

* */
public class 矩阵的最大值 {
    // 矩阵的最大值
    // 解题思路：矩阵的每一行依次变换位置，保留其最大值，然后将其最大值相加
    public static void test030() {
        Scanner sc = new Scanner(System.in);
        int num = Integer.parseInt(sc.nextLine());
        List<String> list = new ArrayList<>();
        // 将输入的矩阵放进list中
        for (int i = 0; i < num; i++) {
            list.add(sc.nextLine());
        }
        // 结果
        int res = 0;
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i).replaceAll(",", "");
            // 矩阵每行的最大值
            int sum = 0;
            // 依次变换矩阵的位置，每次换一位
            for (int j = 0; j < num; j++) {
                sum = Math.max(sum, Integer.parseInt(str, 2));
                str = str.substring(num - 1) + str.substring(0, num - 1);
            }
            // 累加结果
            res += sum;
        }
        System.out.println(res);
    }

}

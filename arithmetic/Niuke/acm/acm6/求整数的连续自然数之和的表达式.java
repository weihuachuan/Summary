package Niuke.acm.acm6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
*  一个整数可以由连续的自然数之和来表示
  给定一个整数
  计算该整数有几种连续自然数之和的表达式
  并打印出每一种表达式

  输入描述
  一个目标整数t  1<= t <=1000

  输出描述
  1.该整数的所有表达式和表达式的个数
  如果有多种表达式，自然数个数最少的表达式优先输出
  2.每个表达式中按自然数递增输出

  具体的格式参见样例
  在每个测试数据结束时，输出一行"Result:X"
  其中X是最终的表达式个数

  输入
  9

  输出
  9=9
  9=4+5
  9=2+3+4
  Result:3

  说明 整数9有三种表达方法：

  示例二
  输入
  10
  输出
  10=10
  10=1+2+3+4
  Result:2

* */
public class 求整数的连续自然数之和的表达式 {
    // 求整数的连续自然数之和的表达式
    // 解题思路：双层for循环，外层从1开始遍历到目标数的一半，内层按顺序累加值，满足条件则结束内循环
    public static void test031() {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        // 10=10 这种是每个数都有的，先输出
        System.out.println(num + "=" + num);
        // 存放结果
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= num / 2; i++) {
            int sum = 0;
            StringBuilder sb = new StringBuilder();
            for (int j = i; j <= num; j++) {
                // 累加值
                sum += j;
                sb = sb.append(j).append("+");
                if (sum == num) {
                    // 存放结果
                    list.add(num + "=" + sb.substring(0, sb.length() - 1));
                    break;
                } else if (sum > num) {
                    break;
                }
            }
        }
        // 从短到长，所以反过来遍历
        for (int i = list.size() - 1; i >= 0; i--) {
            System.out.println(list.get(i));
        }
        System.out.println("Result:" + (list.size() + 1));
    }

}

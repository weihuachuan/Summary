package Niuke.acm.acm7;

import java.util.*;
/*
* 给定一个数组
 编写一个函数
 来计算他的最大N个数和最小N个数的和
 需要对数组进行去重

 说明
 第一行输入M
 M表示数组大小
 第二行输入M个数
 表示数组内容
 第三行输入N表示需要计算的最大最小N的个数

 输出描述
 输出最大N个数和最小N个数的和

 例一：
 输入
 5
 95 88 83 64 100
 2

 输出
 342

 说明
 最大2个数[100 95] 最小2个数[83 64]
 输出342

 例二
 输入
 5
 3 2 3 4 2
 2

 输出
 -1
 说明
 最大两个数是[4 3]最小2个数是[3 2]
 有重叠输出为-1

* */
public class 最大N个数和最小N个数的和 {
    // 最大N个数和最小N个数的和
    // 解题思路：对数组进行去重且排序，当满足条件时取最后两个与最前两个相加
    public static void test028(){
        Scanner sc = new Scanner(System.in);
        int len = Integer.parseInt(sc.nextLine());
        String[] split = sc.nextLine().split(" ");
        int num = Integer.parseInt(sc.nextLine());
        // 去重且排序
        Set<Integer> set = new TreeSet<>();
        for (int i = 0; i < len; i++) {
            set.add(Integer.parseInt(split[i]));
        }
        // 去重后长度不是num的两倍
        if (set.size() < 2 * num) {
            System.out.println(-1);
            return;
        }
        int sum = 0;
        // 将set转为list
        List<Integer> list = new ArrayList<>(set);
        for (int i = 0; i < num; i++) {
            sum += list.get(i) + list.get(list.size() - 1 - i);
        }
        System.out.println(sum);
    }

}

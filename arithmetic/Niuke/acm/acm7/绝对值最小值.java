package Niuke.acm.acm7;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 给定一个随机的整数数组(可能存在正整数和负整数)nums,
 请你在该数组中找出两个数，其和的绝对值(|nums[x]+nums[y]|)为最小值
 并返回这两个数(按从小到大返回)以及绝对值。
 每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 <p>
 输入描述：
 一个通过空格空格分割的有序整数序列字符串，最多1000个整数，
 且整数数值范围是[-65535,65535]
 <p>
 输出描述：
 两个数和两数之和绝对值
 <p>
 示例一：
 输入
 -1 -3 7 5 11 15
 输出
 -3 5 2
 <p>
 说明：
 因为|nums[0]+nums[2]|=|-3+5|=2最小，
 所以返回-3 5 2

* */
public class 绝对值最小值 {
    // 绝对值最小值
    // 解题思路：双层for循环，遍历出所有组合，记录最小值
    public static void test025(){
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] split = line.split(" ");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            int parseInt = Integer.parseInt(split[i]);
            // 数组中同一个元素不能使用两遍
            if (!list.contains(parseInt)) {
                list.add(parseInt);
            }
        }

        int res = Integer.MAX_VALUE;
        int a = 0;
        int b = 0;
        for (int i = 0; i < list.size(); i++){
            for (int j = i + 1; j < list.size(); j++){
                int sum = Math.abs(list.get(i) + list.get(j));
                if (sum < res) {
                    a = list.get(i);
                    b = list.get(j);
                    res = sum;
                }
            }
        }
        if (a < b) {
            System.out.println(a + " " + b + " " + res);
        } else {
            System.out.println(b + " " + a + " " + res);
        }
    }

}

package Niuke.acm.acm5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
*  给定一个正整数数组
 检查数组中是否存在满足规则的数组组合
 规则：
 A=B+2C
 输入描述
 第一行输出数组的元素个数
 接下来一行输出所有数组元素  用空格隔开
 输出描述
 如果存在满足要求的数
 在同一行里依次输出 规则里 A/B/C的取值 用空格隔开
 如果不存在输出0

 示例1：
 输入
 4
 2 7 3 0
 输出
 7 3 2
 说明：
 7=3+2*2
 示例2：
 输入
 3
 1 1 1
 输出
 0
 说明找不到满足条件的组合

 备注：
 数组长度在3~100之间
 数组成员为0~65535
 数组成员可以重复
 但每个成员只能在结果算式中使用一次
 如 数组成员为 [0,0,1,5]
 0出现两次允许，但结果0=0+2*0不允许  因为算式中使用了3个0

 用例保证每组数字里最多只有一组符合要求的解
*/
public class 满足规则的数组组合 {
    // 满足规则的数组组合
    // 解题思路：对输入的数字进行排序，a从后往前取，取最大的，b、c取比a小的，且b!=c
    public static void test045(){
        Scanner sc = new Scanner(System.in);
        int len = Integer.parseInt(sc.nextLine());
        String line = sc.nextLine();
        String[] split = line.split(" ");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(Integer.parseInt(split[i]));
        }
        // 对list进行排序
        Collections.sort(list);
        // 三重循环，虽然效率低，但是简单
        for (int a = len - 1; a >= 0; a--) {
            // A的值肯定是大于等于B、C的，所以B、C的取值范围要在a前
            for (int b = 0; b < a; b++) {
                // c!=b 是因为每个成员只能在结果算式中使用一次
                for (int c = 0; c < a && c != b; c++) {
                    if (list.get(a) == list.get(b) + 2*list.get(c)) {
                        System.out.println(list.get(a) + " " + list.get(b) + " " + list.get(c));
                        return;
                    }
                }
            }
        }
        System.out.println(0);
    }

}

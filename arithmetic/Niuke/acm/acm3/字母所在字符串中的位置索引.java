package Niuke.acm.acm3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
*   输入一个由N个大小写字母组成的字符串
  按照ASCII码值从小到大进行排序
  查找字符串中第K个最小ASCII码值的字母(k>=1)
  输出该字母所在字符串中的位置索引(字符串的第一个位置索引为0)
  k如果大于字符串长度则输出最大ASCII码值的字母所在字符串的位置索引
  如果有重复字母则输出字母的最小位置索引

  输入描述
  第一行输入一个由大小写字母组成的字符串
  第二行输入k k必须大于0 k可以大于输入字符串的长度

  输出描述
  输出字符串中第k个最小ASCII码值的字母所在字符串的位置索引
  k如果大于字符串长度则输出最大ASCII码值的字母所在字符串的位置索引
  如果第k个最小ASCII码值的字母存在重复  则输出该字母的最小位置索引

  示例一
  输入
  AbCdeFG
  3
  输出
  5
  说明
  根据ASCII码值排序，第三个ASCII码值的字母为F
  F在字符串中位置索引为5(0为字符串的第一个字母位置索引)

  示例二
  输入
  fAdDAkBbBq
  4
  输出
  6
  说明
  根据ASCII码值排序前4个字母为AABB由于B重复则只取B的第一个最小位置索引6
  而不是第二个B的位置索引8

* */
public class 字母所在字符串中的位置索引 {
    // 字母所在字符串中的位置索引
    // 解题思路：将字符串转为集合，对集合进行排序，找到指定位置的值，在字符串中找该字符所在位置索引
    public static void test065() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        char[] chars = line.toCharArray();
        int index = sc.nextInt();
        int length = chars.length;
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(chars[i]);
        }
        // 对集合进行排序
        Collections.sort(list);
        // 大于字符长度
        if (index > length) {
            // 输出排序后最后一个字符所在字符串的索引位置
            System.out.println(line.indexOf(list.get(length - 1)));
        } else {
            // 输出指定位置字符所在字符串的索引位置
            System.out.println(line.indexOf(list.get(index - 1)));
        }
    }

}

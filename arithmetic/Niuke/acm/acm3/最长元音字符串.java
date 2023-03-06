package Niuke.acm.acm3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
*   定义当一个字符串只有元音字母(a,e,i,o,u,A,E,I,O,U)组成,
  称为元音字符串，现给定一个字符串，请找出其中最长的元音字符串，
  并返回其长度，如果找不到请返回0，
  字符串中任意一个连续字符组成的子序列称为该字符串的子串
  <p>
  输入描述：
  一个字符串其长度 0<length ,字符串仅由字符a-z或A-Z组成
  输出描述：
  一个整数，表示最长的元音字符子串的长度
  <p>
  示例1：
  输入
  asdbuiodevauufgh
  输出
  3
  说明：
  最长的元音字符子串为uio和auu长度都为3，因此输出3

* */
public class 最长元音字符串 {
    // 最长元音字符串
    // 解题思路：定义一个元音字符数组，判断字符是否在数组中，且是连续的，定义一个临时长度来记录连续且元音字符长度，保留最长的
    public static void test066() {
        Scanner sc = new Scanner(System.in);
        char[] chars = sc.nextLine().toCharArray();
        List<Character> list = new ArrayList<>();
        list.add('a');
        list.add('e');
        list.add('i');
        list.add('o');
        list.add('u');
        list.add('A');
        list.add('E');
        list.add('I');
        list.add('O');
        list.add('U');
        // 最大长度
        int maxLen = 0;
        // 临时长度
        int tempLen = 0;
        for (int i = 0; i < chars.length; i++) {
            // 元音且连续
            while (i < chars.length && list.contains(chars[i])) {
                i++;
                tempLen++;
            }
            // 保留最长串
            maxLen = Math.max(maxLen, tempLen);
            // 初始化
            tempLen = 0;
        }
        System.out.println(maxLen);
    }

}

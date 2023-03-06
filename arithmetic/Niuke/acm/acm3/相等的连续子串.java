package Niuke.acm.acm3;

import java.util.Scanner;

/*
*   给你两个字符串t和p
  要求从t中找到一个和p相同的连续子串
  并输出该子串第一个字符的下标
  输入描述
  输入文件包括两行 分别表示字符串t和p
  保证t的长度不小于p
  且t的长度不超过1000000
  p的长度不超过10000
  输出描述
  如果能从t中找到一个和p相等的连续子串,
  则输出该子串第一个字符在t中的下标
  下标从左到右依次为1,2,3,...；
  如果不能则输出 "No"
  如果含有多个这样的子串
  则输出第一个字符下标最小的

  示例一：
  输入：
  AVERDXIVYERDIAN
  RDXI
  输出
  4
*/
public class 相等的连续子串 {
    // 相等的连续子串
    // 解题思路：双层循环，遍历每个字符，依次判断是否与子串相同，当相同长度与子串相等时，结束并输出；否则进入下一个字符进行判断
    public static void test061() {
        Scanner sc = new Scanner(System.in);
        String t = sc.nextLine();
        String p = sc.nextLine();
        for (int i = 0; i < t.length(); i++) {
            // 开始字符位置
            int start = i;
            // 与子串相同字符个数
            int j = 0;
            // 与子串进行比对
            while (start < t.length() && j < p.length() && t.charAt(start) == p.charAt(j)) {
                // 索引位置向前
                start++;
                // 个数加1
                j++;
            }
            // 当子串相同字符个数 == 子串长度，满足条件，输入并结束
            if (j == p.length()) {
                System.out.println(i + 1);
                return;
            }
        }
        System.out.println("No");
    }

}

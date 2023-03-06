package Niuke.acm.acm3;

import java.util.Scanner;

/*
*   题目描述：
  给定一个字符串s，最多只能进行一次变换，返回变换后能得到的最小字符串（按照字典序进行比较）。
  变换规则：交换字符串中任意两个不同位置的字符。
  输入描述：
  一串小写字母组成的字符串s。
  输出描述：
  按照要求进行变换得到的最小字符串。
  备注：
  s是都是小写字符组成
  1<=s.length<=1000
  示例
  输入：abcdef
  输出：abcdef
  说明：abcdef已经是最小字符串，不需要交换

  输入：bcdefa
  输出：acdefb
  说明：a和b进行位置交换，可以得到最小字符串

* */
public class 最小字符串 {
    // 最小字符串
    // 解题思路：遍历每一个元素，找到最小的，与第一个元素交换，如果第一个元素是最小的，则找第二个元素与第二小的元素交换，以此类推
    public static void test067() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        char[] chars = line.toCharArray();
        // 遍历每一个元素
        for (int j = 0; j < chars.length; j++) {
            // 存放最小的元素
            char minValue = chars[j];
            // 存放最小元素的索引
            int minIndex = j;
            // 遍历往后的所有元素
            for (int i = j + 1; i < chars.length; i++) {
                // 使用<=是因为要拿到排在最后的最小元素
                if (chars[i] <= minValue) {
                    minValue = chars[i];
                    minIndex = i;
                }
            }
            // 当最小元素不是第一个时，则交换第一个元素与最小元素的位置，输出后结束
            if (minIndex != j && chars[j] != chars[minIndex]) {
                chars[minIndex] = chars[j];
                chars[j] = minValue;
                //将字符数组转化为字符串
                System.out.println(new String(chars));
                return;
            }
        }
        // 当元素是按顺序排的，则直接输出
        System.out.println(line);
    }

}

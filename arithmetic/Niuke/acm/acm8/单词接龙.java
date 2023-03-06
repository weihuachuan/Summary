package Niuke.acm.acm8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
*  题目描述：
  单词接龙的规则是:
  可用于接龙的单词,首字母必须要与前一个单词的尾字母相同；
  当存在多个首字母相同的单词时，取长度最长的单词；
  如果长度也相等，则取字典序最小的单词；
  已经参与接龙的单词不能重复使用；
  现给定一组全部由小写字母组成的单词数组，
  并指定其中一个单词为起始单词，进行单词接龙，
  请输出最长的单词串。
  单词串是单词拼接而成的，中间没有空格。
  单词个数 1 < N < 20
  单个单词的长度 1 ~ 30

  输入描述
  输入第一行为一个非负整数
  表示起始单词在数组中的索引k
  0 <= k < N
  输入的第二行为非负整数N
  接下来的N行分别表示单词数组中的单词

  输出描述
  输出一个字符串表示最终拼接的单词串

  示例一
  输入
  0
  6
  word
  dd
  da
  dc
  dword
  d
  输出
  worddwordda
  示例二
  输入
  4
  6
  word
  dd
  da
  dc
  dword
  d
  输出
  dwordda
* */
public class 单词接龙 {
    // PS：如果给出的单词中同样的单词出现两次，能用两次还是用一次？我这里是用了两次，如果只能用一次的话，可以用一个list来存储用过的单词，如果在里面，就不用
    // 单词接龙
    public static void test055_2() {
        Scanner sc = new Scanner(System.in);
        int start = Integer.parseInt(sc.nextLine());
        int len = Integer.parseInt(sc.nextLine());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(sc.nextLine());
        }
        String startWord = list.get(start);
        // 移除起始元素
        list.remove(startWord);
        list.sort((s1, s2) -> {
            int length1 = s1.length();
            int length2 = s2.length();
            if (length1 != length2) {
                return length2 - length1;
            }
            // 按照字典排序，遍历每一个字符，返回不相同字符的差值
            return s1.compareTo(s2);
        });
        String result = startWord;
        for (int i = 0; i < list.size(); i++) {
            String value = list.get(i);
            if (getStartOrEnd(startWord, 100).equals(getStartOrEnd(value, 1))) {
                result = result + value;
                // 找到的单词作为起始单词
                startWord = value;
                // 移除用过的单词
                list.remove(value);
                // 找到了归零，下一个从头开始找
                i = 0;
            }
        }
        System.out.println(result);
    }

    private static String getStartOrEnd(String key, int num) {
        // 求字符串首字母
        if (num == 1) {
            return key.substring(0, 1);
        } else { // 求字符串尾字幕
            return key.substring(key.length() - 1, key.length());
        }
    }

}

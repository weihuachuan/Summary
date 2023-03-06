package Niuke.acm.acm8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
*   删除字符串中出现次数最少的字符
如果多个字符出现次数一样则都删除

例子：
输入
  abcdd
  字符串中只
 输出
  dd

输入
  aabbccdd

输出
  empty

  如果都被删除  则换为empty
*/
public class 删除出现次数最少的字符 {
    //删除出现次数最少的字符
    public static void test19(){
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        if (line.length() <= 1) {
            System.out.println("empty");
            return;
        }

        char[] chars = line.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (map.containsKey(c)){
                map.put(c, map.get(c) + 1);
            }else {
                map.put(c, 1);
            }
        }
        Integer[] array = new Integer[map.size()];
        Integer[] array1 = map.values().toArray(array);
        Arrays.sort(array1);
        int minLen = array1[0];
        for (Character s : map.keySet()) {
            if (map.get(s) == minLen) {
                line = line.replace(s + "", "");
            }
        }
        if (line.length() <= 0) {
            System.out.println("empty");
        } else {
            System.out.println(line);
        }
    }

    public static void main(String[] args) {
        test19();
    }
}

package Niuke.acm.acm8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

/*
*
* 给定一个字符串
 只包含大写字母
 求在包含同一字母的子串中
 长度第K长的子串
 相同字母只取最长的子串

 输入
 第一行 一个子串 1<len<=100
 只包含大写字母
 第二行为k的值

 输出
 输出连续出现次数第k多的字母的次数

 例子：
 输入
 AABAAA
 2
 输出
 1
 同一字母连续出现最多的A 3次
 第二多2次  但A出现连续3次

 输入
 AAAAHHHBBCDHHHH
 3
 输出
 2
 //如果子串中只包含同一字母的子串数小于k
 则输出-1
*/
public class 输出连续出现次数第k多的字母的次数 {
    // 输出连续出现次数第k多的字母的次数
    public static void test17() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        int num = sc.nextInt();
        char[] chars = line.toCharArray();
        Map<Character, Integer> map = new HashMap<>();

        int count = 1;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            while (i + 1 < chars.length && c == chars[i + 1]) {
                count++;
                i++;
            }
            if (map.containsKey(c)) {
                if (count > map.get(c)) {
                    map.put(c, count);
                }
            } else {
                map.put(c, count);
            }
            count = 1;
        }
        if (map.size() < num) {
            System.out.println(-1);
            return;
        }
        Object[] array = map.values().toArray();
        Arrays.sort(array, (a1, a2)->{
            return (Integer)a2 - (Integer) a1;
        });

        System.out.println(array[num - 1]);
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        q.offer(2);
        q.add(3);
        q.add(4);
    }
}

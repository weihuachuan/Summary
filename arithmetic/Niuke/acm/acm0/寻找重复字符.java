package Niuke.acm.acm0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
/*题目描述：
        给定两个字符串，
        从字符串2中找出字符串1中的所有字符，
        去重并按照ASCII码值从小到大排列，

        输入描述
        字符范围满足ASCII编码要求，
        输入字符串1长度不超过1024，
        字符串2长度不超过100

        输出描述
        按照ASCII由小到大排序

        示例一
        输入
        bach
        bbaaccddfg

        输出
        abc

        示例二
        输入
        fach
        bbaaccedfg

        输出
        acf*/
public class 寻找重复字符 {
    public static void test009() {
        Scanner scanner = new Scanner(System.in);
        String line1 = scanner.nextLine();
        String line2 = scanner.nextLine();
        List<Character> list = new ArrayList<>();
        // 将输入的字符串转为字符数组
        for (int i = 0; i < line2.length(); i++) {
            list.add(line2.charAt(i));
        }
        // 用来存第一第二次输入都有的字符
        List<Character> resList = new ArrayList<>();
        for (int i = 0; i < line1.length(); i++) {
            char c = line1.charAt(i);
            // &&后的条件是防止重复
            if (list.contains(c) && !resList.contains(c)) {
                resList.add(c);
            }
        }
        // 对集合进行排序，默认是按ASCII码值排序
        Collections.sort(resList);
        for (Character c : resList) {
            System.out.print(c);
        }
    }
}

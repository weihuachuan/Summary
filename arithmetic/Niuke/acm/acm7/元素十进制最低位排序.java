package Niuke.acm.acm7;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
*  给定一个非空数组(列表)
 起元素数据类型为整型
 请按照数组元素十进制最低位从小到大进行排序
 十进制最低位相同的元素，相对位置保持不变
 当数组元素为负值时，十进制最低为等同于去除符号位后对应十进制值最低位

 输入描述
 给定一个非空数组(列表)
 其元素数据类型为32位有符号整数
 数组长度为[1,1000]
 输出排序后的数组

 输入
 1,2,5,-21,22,11,55,-101,42,8,7,32
 输出
 1,-21,11,-101,2,22,42,32,5,55,7,8

* */
public class 元素十进制最低位排序 {
    // 元素十进制最低位排序
    // 解题思路：取元素最后一位进行比较排序
    public static void test027(){
        Scanner sc = new Scanner(System.in);
        String[] split = sc.nextLine().split(",");
        List<String> list = Arrays.asList(split);
        list.sort((s1, s2) -> {
            // 取元素最后一个进行比较排序
            return s1.charAt(s1.length() - 1) - s2.charAt(s2.length() - 1);
        });
        StringBuilder sb = new StringBuilder();
        for (String s : list){
            sb.append(s + ",");
        }
        System.out.println(sb.substring(0, sb.length() - 1));
    }

    public static void main(String[] args) {
        test027();
    }
}

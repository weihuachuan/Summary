package Niuke.acm.acm0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
/* 给定两个整数数组，arr1、arr2，数组元素按升序排列；
         假设从arr1、arr2中分别取出一个元素，可构成一对元素；
         现在需要取出k对元素，并对取出的所有元素求和，计算和的最小值；
         注意：两对元素对应arr1、arr2的下标是相同的，视为同一对元素。

         输入描述
         输入两行数组arr1、arr2
         每行首个数字为数组大小size， 0 < size <= 100
         arr1，arr2中的每个元素e， 0< e <1000
         接下来一行，正整数k 0 < k <= arr1.size * arr2.size

         输出描述
         满足要求的最小值

         示例一
         输入

         1 1 2
         1 2 3
         2
         输出
         4
         说明：
         用例中需要取两个元素，取第一个数组第0个元素与第二个数组第0个元素组成一个元素[1,1];
         取第一个数组第1个元素与第二个数组第0个元素组成一个元素[1,1];
         求和为1+1+1+1=4 ,满足要求最小*/
// 指定元素对最小和
// 思路：双层for循环，求出两个数组所有值一一相加的值，放进另一个数组中，并进行升序排序，求出指定前几个的和
public class 指定元素组和 {
    public static void test002() {
        Scanner sc = new Scanner(System.in);
        String line1 = sc.nextLine();
        String line2 = sc.nextLine();
        int[] array1 = stringToArray(line1);
        int[] array2 = stringToArray(line2);
        int len1 = array1.length;
        int len2 = array2.length;
        int groupCount = sc.nextInt();
        List<Integer> list = new ArrayList();
        // 双层for循环，求出两个数组所有值一一相加的值，放进另一个数组中
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                list.add(array1[i] + array2[j]);
            }
        }
        // 对集合进行排序（升序）
        Collections.sort(list);
        int res = 0;
        // 求出指定前几个的和
        for (int i = 0; i < groupCount; i++) {
            res += list.get(i);
        }
        System.out.println(res);
    }
    private static int[] stringToArray(String line) {
        String[] strings = line.split(" ");
        int[] array = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            array[i] = Integer.parseInt(strings[i]);
        }
        return array;
    }
}

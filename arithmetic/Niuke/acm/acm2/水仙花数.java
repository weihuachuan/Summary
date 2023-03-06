package Niuke.acm.acm2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
* 所谓的水仙花数是指一个n位的正整数其各位数字的n次方的和等于该数本身，
例如153=1^3+5^3+3^3,153是一个三位数
输入描述
    第一行输入一个整数N，
    表示N位的正整数N在3-7之间包含3,7
    第二行输入一个正整数M，
    表示需要返回第M个水仙花数
输出描述
    返回长度是N的第M个水仙花数，
    个数从0开始编号，
    若M大于水仙花数的个数返回最后一个水仙花数和M的乘积，
    若输入不合法返回-1

示例一：

    输入
     3
     0
    输出
     153
    说明：153是第一个水仙花数
 示例二：
    输入
    9
    1
    输出
    -1
*/
public class 水仙花数 {
    // 水仙花数
    public static void test070() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        if (n < 3 || n > 7) {
            System.out.println(-1);
            return;
        }
        // 起始数
        int start = (int) Math.pow(10, n - 1);
        // 终止数
        int end = (int) Math.pow(10, n);
        List<Integer> reslist = new ArrayList<>();
        for (int i = start; i < end; i++) {
            int sum = 0;
            // 求每位数的简单解法
            // String[] split = (i + "").split("");
            // for (String s : split) {
            //     sum += Math.pow(Integer.parseInt(s), n);
            // }
            // 算出每位数
            List<Integer> list1 = singleNum(i);
            for (Integer s : list1) {
                // 每位数进行n次方并累加
                sum += Math.pow(s, n);
            }
            // 满足要求的放进结果集中
            if (sum == i) {
                reslist.add(i);
            }
        }
        if (m >= reslist.size()) {
            System.out.println(reslist.get(reslist.size() - 1) * m);
        } else {
            System.out.println(reslist.get(m));
        }
    }

    /**
     * 将一个整型数从后往前输出单个数字
     * @param num
     * @return
     */
    public static List<Integer> singleNum(int num){
        List<Integer> list = new ArrayList<>();
        while (num > 0) {
            int singleNum  = num % 10;
            list.add(singleNum);
            num = num /10;
        }
        return list;
    }

}

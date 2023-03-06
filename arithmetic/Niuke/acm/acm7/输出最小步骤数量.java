package Niuke.acm.acm7;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
*  一个正整数数组 设为nums
 最大为100个成员
 求从第一个成员开始正好走到数组最后一个成员所使用的最小步骤数
 3 5 9 4 2 6 8 3 5 4 3 9
 要求：
 1. 第一步 必须从第一元素起  且 1<=第一步步长<len/2  (len为数组长度)
 2. 从第二步开始只能以所在成员的数字走相应的步数，不能多不能少，
 如果目标不可达返回-1
 只输出最小的步骤数量
 3. 只能向数组的尾部走不能向回走

 输入描述：
 有正整数数组 空格分割
 数组长度<100

 输出描述 ：
 正整数  最小步数
 不存在输出-1

 例子：
 输入
 7 5 9 4 2 6 8 3 5 4 3 9
 输出
 2
 第一个可选步长选择2
 从第一个成员7开始走两步到9
 第二步：从9经过9个成员到最后

 例子：
 输入
 1 2 3 7 1 5 9 3 2 1
 输出
 -1

* */
public class 输出最小步骤数量 {
    // 输出最小步骤数量
    // 解题思路：遍历符合条件的第一步，拿到第二步的值，判断是否符合要求，不符合则继续，拿到最小的值
    public static void test020(){
        Scanner sc = new Scanner(System.in);
        String[] split = sc.nextLine().split(" ");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++){
            list.add(Integer.parseInt(split[i]));
        }

        // 结果
        int res = Integer.MAX_VALUE;
        // 条件：必须从第一元素起  且 1<=第一步步长<len/2  (len为数组长度)
        for (int i = 1; i <= list.size()/2; i++) {
            int count = 1;
            // 第一步所在位置的值
            Integer nextNum = list.get(i);
            while (true){
                // i + nextNum 是第一次走的长度加当前位置的长度
                // 如果是终点则结束
                if (i + nextNum == list.size() - 1) {
                    count++;
                    break;
                } else if (i + nextNum <= list.size() / 2) { // 第一次后没有过数组一半，肯定不是最优解
                    count = Integer.MAX_VALUE;
                    break;
                } else if (i + nextNum < list.size() - 1) { // 超过数组一半且不到终点
                    // 还没到终点则在向前走当前位置的步数
                    nextNum = nextNum + list.get(i + nextNum);
                    count++;
                } else { // 超过终点则结束
                    count = Integer.MAX_VALUE;
                    break;
                }
            }
            // 取最小值
            res = Math.min(res, count);
        }
        if (res == Integer.MAX_VALUE){
            System.out.println(-1);
        } else {
            System.out.println(res);
        }
    }

}

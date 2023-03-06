package Niuke.acm.acm7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
*  疫情过后希望小学终于又重新开学了
 3年2班开学第一天的任务是
 将后面的黑板报重新制作
 黑板上已经写上了N个正整数
 同学们需要给这每个数分别上一种颜色
 为了让黑板报既美观又有学习意义
 老师要求同种颜色的所有数都可以被这个颜色中最小的那个数整除
 现在帮小朋友们算算最少需要多少种颜色，给这N个数进行上色

 输入描述
 第一行有一个正整数N
 其中 1 <= n <=100
 第二行有N个int型数，保证输入数据在[1,100]范围中
 表示黑板上各个正整数的值

 输出描述
 输出只有一个整数，为最少需要的颜色种数

 输入
 3
 2 4 6
 输出
 1
 说明：
 所有数都能被2整除

 输入
 4
 2 3 4 9
 输出
 2
 说明：
 2与4涂一种颜色，4能被2整除
 3与9涂另一种颜色，9能被3整除
 不能涂同一种颜色
*/
public class 最少颜色种数 {
    // 最少颜色种数
    // 解题思路：维护一个新集合，用于存储不能整除最小数的，遍历输入的数，每个数都去与新集合的每个数比较是否整除，不能整除则加入新数组
    public static void test026(){
        Scanner sc = new Scanner(System.in);
        int len = Integer.parseInt(sc.nextLine());
        String[] split = sc.nextLine().split(" ");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++){
            list.add(Integer.parseInt(split[i]));
        }
        // 对数组进行升序排序
        Collections.sort(list);
        // 存储不能整除最小数的数
        List<Integer> resList = new ArrayList<>();
        // 最少需要1中颜色，所以最小的数是基数
        resList.add(list.get(0));
        for (int i = 1; i < len; i++){
            int num = list.get(i);
            for (int j = 0; j < resList.size(); j++) {
                // 符合条件，则退出
                if (num % resList.get(j) == 0) {
                    break;
                }
                // 到最后一个都不符合条件，则加入不能整除数组
                if (j == resList.size() - 1) {
                    resList.add(num);
                }
            }
        }
        System.out.println(resList.size());

    }

}

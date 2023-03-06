package Niuke.acm.acm2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
* 特定大小的停车场 数组cars表示
其中1表示有车  0表示没车
车辆大小不一，小车占一个车位(长度1)
货车占两个车位(长度2)
卡车占三个车位(长度3)
统计停车场最少可以停多少辆车
返回具体的数目

输入描述：
  整型字符串数组cars
  其中1表示有车0表示没车
  数组长度<1000

输出描述：
  整型数字字符串
  表示最少停车数

示例1：
  输入
    1,0,1
  输出
    2
  说明：
    一个小车占第一个车位
    第二个车位空，一个小车占第三个车位
    最少有两辆车

 示例2:
   输入：
     1,1,0,0,1,1,1,0,1
   输出：
     3
   说明：
     一个货车占第1,2个车位
     第3,4个车位空
     一个卡车占第5,6,7个车位
     第8个车位空
     一个小车占第9个车位
     最少3俩个车
*/
public class 最少停车数 {
    // 最少停车数
    public static void test074() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] split = line.split(",");
        // 连续1的次数
        int count = 0;
        // 找出所有的连续1的次数，存在list中
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            // 找出连续1的次数
            while (i < split.length && "1".equals(split[i])) {
                count++;
                i++;
            }
            // 连续1的次数大于0，存入list中
            if (count != 0) {
                list.add(count);
            }
            // 初始化
            count = 0;
        }
        int res = 0;
        for (int i = 0; i < list.size(); i++) {
            // 累加结果，算出能停几辆卡车
            res += list.get(i) / 3;
            // 如果%3有剩余的，则再加一辆，不用管余1还是余2，最小都是一辆
            if (list.get(i) % 3 != 0) {
                res++;
            }
        }
        System.out.println(res);
    }

}

package Niuke.acm.acm7;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
*  在通信系统中有一个常见的问题是对用户进行不同策略的调度
 会得到不同系统消耗的性能
 假设由N个待串行用户，每个用户可以使用A/B/C三种不同的调度策略
 不同的策略会消耗不同的系统资源
 请你根据如下规则进行用户调度
 并返回总的消耗资源数
 规则是：相邻的用户不能使用相同的调度策略
 例如：
 第一个用户使用A策略 则第二个用户只能使用B和C策略
 对单的用户而言，不同的调度策略对系统资源的消耗可以规划后抽象为数值
 例如
 某用户分别使用ABC策略的系统消耗，分别为15 8 17
 每个用户依次选择当前所能选择的对系统资源消耗最少的策略,局部最优
 如果有多个满足要求的策略，选最后一个

 输入描述：
 第一行表示用户个数N
 接下来表示每一行表示一个用户分别使用三个策略的资源消耗
 resA resB resC

 输出描述：
 最优策略组合下的总的系统消耗资源数

 示例一：
 输入：
 3
 15 8 17
 12 20 9
 11 7 5
 输出：
 24
 说明:
 1号用户使用B策略
 2号用户使用C策略
 3号用户使用B策略
 系统资源消耗8+9+7

* */
public class 最优组合策略 {
    // 最优组合策略
    // 解题思路：遍历每一个数组，找到最小值，记录最小值的索引，相邻的数组不使用上次记录的索引所在的值
    public static void test021(){
        Scanner sc = new Scanner(System.in);
        int num = Integer.parseInt(sc.nextLine());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++){
            list.add(sc.nextLine());
        }
        // 结果
        int sum = 0;
        // 存储最终最小值的索引
        int minIndex = 1111;
        for (int i = 0; i < num; i++){
            String[] split = list.get(i).split(" ");
            // 用来存储临时的最小值的索引
            int tempIndex = 9999;
            // 默认最小值
            int minNum = Integer.MAX_VALUE;
            for (int j = 0; j < split.length; j++){
                int temp = Integer.parseInt(split[j]);
                // 最小的 且 不与相邻的用户使用相同的调度策略
                if (temp <= minNum && j != minIndex) {
                    minNum = temp;
                    tempIndex = j;
                }
            }
            sum += minNum;
            // 上一个数组最小值的位置
            minIndex = tempIndex;
        }
        System.out.println(sum);
    }

}

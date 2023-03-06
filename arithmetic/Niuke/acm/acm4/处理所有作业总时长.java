package Niuke.acm.acm4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
*   一个工厂有m条流水线
  来并行完成n个独立的作业
  该工厂设置了一个调度系统
  在安排作业时，总是优先执行处理时间最短的作业
  现给定流水线个数m
  需要完成的作业数n
  每个作业的处理时间分别为 t1,t2...tn
  请你编程计算处理完所有作业的耗时为多少
  当n>m时 首先处理时间短的m个作业进入流水线
  其他的等待
  当某个作业完成时，
  依次从剩余作业中取处理时间最短的
  进入处理

  输入描述：
  第一行为两个整数(采取空格分隔)
  分别表示流水线个数m和作业数n
  第二行输入n个整数(采取空格分隔)
  表示每个作业的处理时长 t1,t2...tn
  0<m,n<100
  0<t1,t2...tn<100

  输出描述
  输出处理完所有作业的总时长

  案例
  输入
  3 5
  8 4 3 2 10
  输出
  13
  说明
  先安排时间为2,3,4的三个作业
  第一条流水线先完成作业
  调度剩余时间最短的作业8
  第二条流水线完成作业
  调度剩余时间最短的作业10
  总共耗时 就是二条流水线完成作业时间13(3+10)

  输入
  3 9
  1 1 1 2 3 4 6 7 8
  输出
  13

* */
public class 处理所有作业总时长 {
    // 处理所有作业的总时长
    // 解题思路：对数组进行排序，累加每条流水线分配的任务的时长，输出最大的
    public static void test058() {
        Scanner sc = new Scanner(System.in);
        String line1 = sc.nextLine();
        String line2 = sc.nextLine();
        String[] split1 = line1.split(" ");
        // 流水线个数m和作业数n
        int m = Integer.parseInt(split1[0]);
        int n = Integer.parseInt(split1[1]);
        String[] split2 = line2.split(" ");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < split2.length; i++) {
            list.add(Integer.parseInt(split2[i]));
        }
        // 对数组进行排序
        Collections.sort(list);
        // 作业数n小于等于流水线个数m，则结果为排序后的最后一个值
        if (n <= m) {
            System.out.println(list.get(list.size() - 1));
            return;
        }
        // 结果
        int res = 0;
        // 遍历每一条流水线
        for (int k = 0; k < m; k++) {
            // 每条流水线获取到的任务时长总和
            int allTime = 0;
            // 累加每条流水线获取到的任务时长
            for (int i = k; i < list.size(); i += m) {
                allTime += list.get(i);
            }
            // 取最大的
            res = Math.max(allTime, res);
        }
        System.out.println(res);
    }

}

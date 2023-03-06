package Niuke.acm.acm8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
*  题目描述：
 一辆运送快递的货车，
  运送的快递放在大小不等的长方体快递盒中，
  为了能够装载更多的快递同时不能让货车超载，
  需要计算最多能装多少个快递。
  注：快递的体积不受限制。
  快递数最多1000个，货车载重最大50000。
  输入描述
  第一行输入每个快递的重量
  用英文逗号隔开
  如 5,10,2,11
  第二行输入货车的载重量
  如 20

  输出描述
  输出最多能装多少个快递
  如 3

  示例一
  输入
  5,10,2,11
  20
  输出
  3

* */
public class 货车最大载货量 {
    // 货车最大载货量
    public static void test012() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        int capacity = sc.nextInt();
        String[] split = line.split(",");
        // 将字符串数组转为Integer型集合
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(Integer.parseInt(split[i]));
        }
        // 对集合进行排序
        Collections.sort(list);
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (sum + list.get(i) < capacity) {
                sum = sum + list.get(i);
            } else {
                System.out.println(i);
                return;
            }
        }
    }

}

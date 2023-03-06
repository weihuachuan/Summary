package Niuke.acm.acm4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
*  停车场有一横排车位0代表没有停车,1代表有车.
 至少停了一辆车在车位上,也至少有一个空位没有停车.
 为防止刮蹭,需为停车人找到一个车位
 使得停车人的车最近的车辆的距离是最大的
 返回此时的最大距离

 输入描述:
 1. 一个用半角逗号分割的停车标识字符串,停车标识为0或1,
 0为空位,1为已停车
 2. 停车位最多有100个

 输出描述
 1. 输出一个整数记录最大距离

 示例一:
 输入
 1,0,0,0,0,1,0,0,1,0,1

 输出
 2

 说明
 当车停在第三个位置上时,离其最近的车距离为2(1~3)
 当车停在第四个位置上时,离其最近的车距离为2(4~6)
 其他位置距离为1
 因此最大距离为2

* */
public class 最大停车距离 {
    // 最大停车距离
    // 解题思路：取出所有已停车的位置的索引，计算其与停车场头的距离、停车场尾的距离、两车之间的距离/2，输出最大的值
    public static void test053(){
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] split = line.split(",");
        // 存放停车位置的索引
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            int num = Integer.parseInt(split[i]);
            if (1 == num) {
                list.add(i);
            }
        }
        // 第一辆车与停车场头的距离
        int startLen = list.get(0);
        // 最后一辆车与停车场尾的距离
        int endLen = split.length - 1 - list.get(list.size() - 1);
        int res = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            // 停车后与左右车的最小距离
            int len = (list.get(i + 1) - list.get(i)) / 2;
            res = Math.max(res, len);
        }
        // 最大距离
        System.out.println(Math.max(res,Math.max(startLen, endLen)));
    }

}

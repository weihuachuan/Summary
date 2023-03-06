package Niuke.acm.acm0;

import java.util.Scanner;
/*为了充分发挥Gpu算力，
        需要尽可能多的将任务交给GPU执行，
        现在有一个任务数组，
        数组元素表示在这1s内新增的任务个数，
        且每秒都有新增任务，
        假设GPU最多一次执行n个任务，
        一次执行耗时1s，
        在保证Gpu不空闲的情况下，最少需要多长时间执行完成。

        输入描述
        第一个参数为gpu最多执行的任务个数
        取值范围1~10000
        第二个参数为任务数组的长度
        取值范围1~10000
        第三个参数为任务数组
        数字范围1~10000

        输出描述
        执行完所有任务需要多少秒

        示例一
        输入
        3
        5
        1 2 3 4 5
        输出
        6
        说明
        一次最多执行3个任务，最少耗时6s

        示例二
        输入
        4
        5
        5 4 1 1 1
        输出
        5
        说明
        一次最多执行4个任务，最少耗时5s*/
public class CPU最少执行时间 {
    public static void test005() {
        Scanner sc = new Scanner(System.in);
        // 每秒执行任务数
        int qps = Integer.parseInt(sc.nextLine());
        int arrayLen = Integer.parseInt(sc.nextLine());
        String line = sc.nextLine();
        int[] array = new int[arrayLen];
        String[] strings = line.split(" ");
        for (int i = 0; i < arrayLen; i++) {
            array[i] = Integer.parseInt(strings[i]);
        }
        // 使用的时间
        int time = 0;
        // 剩余任务，初始为0
        int leaveTask = 0;
        for (int i = 0; i < arrayLen; i++) {
            // 若当前任务+剩余任务大于每秒执行任务，则剩余任务 = 之前剩余任务 + 当前任务 - 每秒执行任务
            if (array[i] + leaveTask > qps) {
                leaveTask = array[i] + leaveTask - qps;
            } else {
                leaveTask = 0;
            }
            // 累加时间
            time++;
        }
        // 当数组走完后还有剩余任务，用剩余任务/每秒执行任务算出需要的时间
        if (leaveTask > 0) {
            int res = leaveTask / qps;
            time = time + res;
            if (leaveTask % qps != 0) {
                time++;
            }
        }
        System.out.println(time);
    }
}

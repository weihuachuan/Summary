package Niuke.acm.acm0;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*小明今年升学到了小学一年级，
        来到新班级后，发现其他小朋友身高参差不齐，
        然后就想基于各小朋友和自己的身高差，对他们进行排序，
        请帮他实现排序

        输入描述
        第一行为正整数 H和N
        0 < H < 200 为小明的身高
        0 < H < 50 为新班级其他小朋友个数
        第二行为N个正整数
        H1 ~ Hn分别是其他小朋友的身高
        取值范围0 < Hi < 200
        且N个正整数各不相同

        输出描述
        输出排序结果，各正整数以空格分割
        和小明身高差绝对值最小的小朋友排在前面
        和小明身高差绝对值最大的小朋友排在后面
        如果两个小朋友和小明身高差一样
        则个子较小的小朋友排在前面
        示例一
        输入
        100 10
        95 96 97 98 99 101 102 103 104 105
        输出
        99 101 98 102 97 103 96 104 95 105*/
public class 小朋友身高排序 {
    public static void test006() {
        Scanner sc = new Scanner(System.in);
        String line1 = sc.nextLine();
        String line2 = sc.nextLine();
        String[] input1 = line1.split(" ");
        String[] input2 = line2.split(" ");
        // 基准数
        int height = Integer.parseInt(input1[0]);
        // 人数
        int num = Integer.parseInt(input1[1]);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(Integer.parseInt(input2[i]));
        }

        // list.sort() 自定义排序规则
        // 在 list.sort() 底层代码中，以 compare(list [ i - 1 ], list [ i ]) 形式调用该方法，
        // 当其的返回值大于0时，list [ i - 1 ] 将与 list [ i ] 交换位置
        list.sort((h1, h2) -> {
            // 与基准数的绝对值
            int diff1 = Math.abs(h1 - height);
            int diff2 = Math.abs(h2 - height);
            // 若与基准数的绝对值相等，则高的排在后，否则基准数大的排在后
            return diff1 == diff2 ? h1 - h2 : diff1 - diff2;
        });
        for (Integer h : list) {
            System.out.print(h + " ");
        }
    }
}

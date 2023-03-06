package Niuke.acm.acm7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
*  现在有多组整数数组
 需要将他们合并成一个新的数组
 合并规则从每个数组里按顺序取出固定长度的内容
 合并到新的数组
 取完的内容会删除掉
 如果该行不足固定长度，或者已经为空
 则直接取出剩余部分的内容放到新的数组中继续下一行

 输入描述
 第一 行每次读取的固定长度
 长度0<len<10
 第二行是整数数组的数目
 数目 0<num<10000
 第3~n行是需要合并的数组
 不同的数组用换行分割
 元素之间用逗号分割
 最大不超过100个元素

 输出描述
 输出一个新的数组，用逗号分割

 示例1
 输入
 3
 2
 2,5,6,7,9,5,7
 1,7,4,3,4
 输出
 2,5,6,1,7,4,7,9,5,3,4,7

 说明  获得长度3和数组数目2
 先遍历第一行 获得2,5,6
 再遍历第二行 获得1,7,4
 再循环回到第一行获得7,9,5
 再遍历第二行获得3,4
 再回到第一行获得7

 示例2
 输入
 4
 3
 1,2,3,4,5,6
 1,2,3
 1,2,3,4
 输出
 1,2,3,4,1,2,3,1,2,3,4,5,6

* */
public class 从多个数组中按顺序取出固定长度的内容组成新数组 {
    // 从多个数组中按顺序取出固定长度的内容组成新数组
    // 解题思路：在循环内遍历每个数组，将符合长度要求的数放进新数组，当新数组长度 == 多个数组长度之和时，循环结束
    public static void test022(){
        Scanner sc = new Scanner(System.in);
        // 每次切割长度
        int num = Integer.parseInt(sc.nextLine());
        // 数组数量
        int acount = Integer.parseInt(sc.nextLine());
        List<List<String>> list = new ArrayList<>();
        // 全部数组总长度
        int sumLen = 0;
        for (int i = 0; i < acount; i++) {
            List<String> e = Arrays.asList(sc.nextLine().split(","));
            sumLen += e.size();
            list.add(e);
        }
        // 存放结果
        List<String> resList = new ArrayList<>();
        int index = 0;
        // 当结果数组长度 大于等于 全部数组总长度 时，运算结束退出循环
        while (resList.size() < sumLen) {
            // 遍历每个数组
            for (int i = 0; i < acount; i++) {
                List<String> list1 = list.get(i);
                if (index > list1.size()) { // 数组用完了，退出此次循环
                    continue;
                } else if (index + num < list1.size()){ // 数组元素还充足
                    for (int j = index; j < index + num; j++) {
                        resList.add(list1.get(j));
                    }
                } else { // 来到最后，取数组剩余元素
                    for (int j = index; j < list1.size(); j++) {
                        resList.add(list1.get(j));
                    }
                }
            }
            // 更换起点
            index += num;
        }
        for (int i = 0; i < resList.size(); i++){
            if (i == resList.size() - 1) {
                System.out.print(resList.get(i));
            }else {
                System.out.print(resList.get(i) + ",");
            }
        }
    }

}

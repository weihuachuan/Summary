package Niuke.hw_easy;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/*
* 描述
请实现一个计票统计系统。你会收到很多投票，其中有合法的也有不合法的，请统计每个候选人得票的数量以及不合法的票数。
（注：不合法的投票指的是投票的名字不存在n个候选人的名字中！！）

数据范围：每组输入中候选人数量满足
1
≤
�
≤
100

1≤n≤100  ，总票数量满足
1
≤
�
≤
100

1≤n≤100
输入描述：
第一行输入候选人的人数n，第二行输入n个候选人的名字（均为大写字母的字符串），第三行输入投票人的人数，第四行输入投票。

输出描述：
按照输入的顺序，每行输出候选人的名字和得票数量（以" : "隔开，注：英文冒号左右两边都有一个空格！），最后一行输出不合法的票数，格式为"Invalid : "+不合法的票数。

示例1
输入：
4
A B C D
8
A D E CF A GG A B
复制
输出：
A : 3
B : 1
C : 0
D : 1
Invalid : 3
复制
说明：
E CF GG三张票是无效的，所以Invalid的数量是3.
* */
public class 记票统计 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            //存储候选人以及票数
            Map<String, Integer> map = new LinkedHashMap<>();
            //候选人数
            int n = sc.nextInt();
            //无效票数
            int invalid = 0;
            for (int i = 0; i < n; i++) {
                map.put(sc.next(), 0);
            }
            //投票人数
            int m = sc.nextInt();
            for (int i = 0; i < m; i++) {
                String key = sc.next();
                if (map.containsKey(key)) {
                    map.put(key, map.get(key) + 1);
                } else {
                    invalid++;
                }
            }
            for (String key : map.keySet()) {
                System.out.println(key + " : " + map.get(key));
            }
            System.out.println("Invalid" + " : " + invalid);
        }
        sc.close();
    }
}

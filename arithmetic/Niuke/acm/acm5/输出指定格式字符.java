package Niuke.acm.acm5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
*
*   Vlan是一种为局域网设备进行逻辑划分的技术
  为了标识不同的vlan 引入了vlan id 1~4094之间的整数
  定义一个vlan id 的资源池
  资源池中连续的vlan用开始vlan-结束vlan表示，
  不连续的用单个整数表示
  所有的vlan用英文逗号连接起来
  现有一个vlan资源池，业务需要从资源池中申请一个vlan
  需要你输出从vlan资源池中移除申请的vlan后的资源池

  输入描述
  第一行为字符串格式的vlan资源池
  第二行为业务要申请的vlan vlan的取值范围1~4094

  输出描述
  从输入vlan资源池中移除申请的vlan后
  字符串格式的vlan资源池
  输出要求满足题目中要求的格式，
  并且要求从小到大升序输出
  如果申请的vlan不在原资源池，输出升序排序的原资源池的字符串即可

  示例一
  输入
  1-5
  2
  输出
  1,3-5
  说明：原vlan资源池中有1 2 3 4 5 移除2后
  剩下的1 3 4 5按照升序排列的方式为 1 3-5

  示例二
  输入
  20-21,15,18,30,5-10
  15
  输出
  5-10,18,20-21,30
  说明：
  原vlan资源池中有5 6 7 8 9 10 15 18 20 21 30
  移除15后 剩下的为 5 6 7 8 9 10 18 20 21 30
  按照题目描述格式并升序后的结果为5-10,18,20-21,30

  示例三
  输入
  5,1-3
  10
  输出
  1-3,5
  资源池中有1 2 3 5
  申请的资源不在资源池中
  将原池升序输出为1-3,5

  输入池中vlan数量范围为2~2094的整数
  资源池中vlan不重复且合法1~2094的整数
  输入是乱序的

* */
public class 输出指定格式字符 {
    // 输出指定格式字符
    // 解题思路：用一个集合，存储每一个拆开后的每一个数字，移除目标数字后进行排序，遍历集合，组装连续的数字
    public static void test042() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        int num = sc.nextInt();
        // 将输入的字符串根据,分割成数组
        String[] split = line.split(",");
        List<Integer> list = new ArrayList<>();
        // 遍历数组
        for (int i = 0; i < split.length; i++) {
            String str = split[i];
            // 将数组每一个字符拆成数组存进新集合中
            if (str.contains("-")) {
                String[] split1 = str.split("-");
                int start = Integer.parseInt(split1[0]);
                int end = Integer.parseInt(split1[1]);
                for (int j = start; j <= end; j++) {
                    list.add(j);
                }
            } else {
                list.add(Integer.parseInt(str));
            }
        }
        // 移除目标数字
        list.remove((Integer) num);
        // 对数组进行排序
        Collections.sort(list);
        // 存放结果
        StringBuilder sb = new StringBuilder();
        // 遍历集合，组装连续的数字
        for (int i = 0; i < list.size(); i++) {
            int start = i;
            while (i + 1 < list.size() && list.get(i) + 1 == list.get(i + 1)) {
                i++;
            }
            // 单个数字
            if (start == i) {
                sb.append(list.get(start) + ",");
            } else { // 连续的数字
                sb.append(list.get(start) + "-" + list.get(i) + ",");
            }
        }
        System.out.println(sb.substring(0, sb.length() - 1));

    }


}

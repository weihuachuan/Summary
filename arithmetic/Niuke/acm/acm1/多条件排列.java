package Niuke.acm.acm1;

import java.util.*;

/*
* 某学校举行运动会,学生们按编号（1、2、3.....n)进行标识,
现需要按照身高由低到高排列，
对身高相同的人，按体重由轻到重排列，
对于身高体重都相同的人，维持原有的编号顺序关系。
请输出排列后的学生编号
   输入描述：
      两个序列，每个序列由N个正整数组成，(0<n<=100)。
      第一个序列中的数值代表身高，第二个序列中的数值代表体重，
   输出描述：
      排列结果，每个数据都是原始序列中的学生编号，编号从1开始，
   实例一：
      输入:
       4
       100 100 120 130
       40 30 60 50
      输出:
       2134
*/
public class 多条件排列 {
    // 多条件排列
    public static void test079() {
        Scanner sc = new Scanner(System.in);
        int len = Integer.parseInt(sc.nextLine());
        // 身高
        String line1 = sc.nextLine();
        // 体重
        String line2 = sc.nextLine();
        String[] split1 = line1.split(" ");
        String[] split2 = line2.split(" ");
        // map的key为编号，value为学生的身高、体重组成的list
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 1; i <= len; i++) {
            List<Integer> list = new ArrayList<>();
            // 身高
            list.add(Integer.parseInt(split1[i - 1]));
            // 体重
            list.add(Integer.parseInt(split2[i - 1]));
            map.put(i, list);
        }

        Set<Map.Entry<Integer, List<Integer>>> entrySet = map.entrySet();
        // 通过stream流的形式进行排序     sorted：对集合进行排序  s1与s2比较，结果大于0互换位置 小于等于0不变
        entrySet.stream().sorted((s1, s2) -> {
            List<Integer> value1 = s1.getValue();
            List<Integer> value2 = s2.getValue();
            // 身高相等比体重
            return value1.get(0) == value2.get(0) ? value1.get(1) - value2.get(1) : value1.get(0) - value2.get(0);
        }).forEach((s) -> {
            System.out.print(s.getKey());
        });
    }

}

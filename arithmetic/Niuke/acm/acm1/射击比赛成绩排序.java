package Niuke.acm.acm1;

import java.util.*;

/*
* 给定一个射击比赛成绩单
包含多个选手若干次射击的成绩分数
请对每个选手按其最高三个分数之和进行降序排名
输出降序排名后的选手id序列
条件如下
1. 一个选手可以有多个射击成绩的分数，且次序不固定
2. 如果一个选手成绩少于3个，则认为选手的所有成绩无效，排名忽略该选手
3. 如果选手的成绩之和相等，则相等的选手按照其id降序排列

输入描述:
 输入第一行
     一个整数N
     表示该场比赛总共进行了N次射击
     产生N个成绩分数
     2<=N<=100

 输入第二行
   一个长度为N整数序列
   表示参与每次射击的选手id
   0<=id<=99

 输入第三行
    一个长度为N整数序列
    表示参与每次射击选手对应的成绩
    0<=成绩<=100

输出描述:
  符合题设条件的降序排名后的选手ID序列

示例一
  输入:
    13
    3,3,7,4,4,4,4,7,7,3,5,5,5
    53,80,68,24,39,76,66,16,100,55,53,80,55
  输出:
    5,3,7,4
  说明:
    该场射击比赛进行了13次
    参赛的选手为{3,4,5,7}
    3号选手成绩53,80,55 最高三个成绩的和为188
    4号选手成绩24,39,76,66  最高三个成绩的和为181
    5号选手成绩53,80,55  最高三个成绩的和为188
    7号选手成绩68,16,100  最高三个成绩的和为184
    比较各个选手最高3个成绩的和
    有3号=5号>7号>4号
    由于3号和5号成绩相等  且id 5>3
    所以输出5,3,7,4
*/
public class 射击比赛成绩排序 {
    // 射击比赛成绩排序
    public static void test086() {
        Scanner sc = new Scanner(System.in);
        String line1 = sc.nextLine();
        String line2 = sc.nextLine();
        String line3 = sc.nextLine();
        String[] strings2 = line2.split(",");
        String[] strings3 = line3.split(",");
        // 用Map存储，key存选手号，value用list存选手所有的成绩
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < Integer.parseInt(line1); i++) {
            if (map.containsKey(Integer.parseInt(strings2[i]))) {
                List<Integer> list = map.get(Integer.parseInt(strings2[i]));
                list.add(Integer.parseInt(strings3[i]));
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(Integer.parseInt(strings3[i]));
                map.put(Integer.parseInt(strings2[i]), list);
            }
        }
        Map<Integer, Integer> map2 = new HashMap();
        // 遍历Map，对选手成绩进行筛选，用一个Map<Integer, Integer>来存选手号和最大总成绩
        for (Integer key : map.keySet()) {
            List<Integer> list = map.get(key);
            int listSize = list.size();
            if (listSize >= 3) {
                Collections.sort(list);
                // 最大总成绩
                int listAll = list.get(listSize - 1) + list.get(listSize - 2) + list.get(listSize - 3);
                map2.put(key, listAll);
            }
        }
        // 用于拼接输出结果
        StringBuilder builder = new StringBuilder();
        // 通过stream流的形式进行排序     sorted：对集合进行排序  e1与e2比较，结果大于0互换位置 小于等于0不变
        Set<Map.Entry<Integer, Integer>> entries = map2.entrySet();
        entries.stream().sorted((e1, e2) -> {
            if (e1.getValue().equals(e2.getValue())) {
                // 分数相等，选手号大的在前
                return e2.getKey() - e1.getKey();
            } else {
                // 分数大的在前
                return e2.getValue() - e1.getValue();
            }
        }).forEach((e) -> {
            builder.append(e.getKey() + ",");
        });
        System.out.println(builder.substring(0, builder.length() - 1));
    }

}

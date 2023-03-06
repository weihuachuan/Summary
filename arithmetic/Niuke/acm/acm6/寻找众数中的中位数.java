package Niuke.acm.acm6;

import java.util.*;

/*
*  1.众数是指一组数据中出现次数多的数
  众数可以是多个
  2.中位数是指把一组数据从小到大排列，最中间的那个数，
  如果这组数据的个数是奇数，那最中间那个就是中位数
  如果这组数据的个数为偶数，那就把中间的两个数之和除以2就是中位数
  3.查找整型数组中元素的众数并组成一个新的数组
  求新数组的中位数

  输入描述
  输入一个一维整型数组，数组大小取值范围   0<n<1000
  数组中每个元素取值范围，  0<e<1000

  输出描述
  输出众数组成的新数组的中位数

  示例一
  输入：
  10 11 21 19 21 17 21 16 21 18 16
  输出
  21

  示例二
  输入
  2 1 5 4 3 3 9 2 7 4 6 2 15 4 2 4
  输出
  3

  示例三
  输入
  5 1 5 3 5 2 5 5 7 6 7 3 7 11 7 55 7 9 98 9 17 9 15 9 9 1 39
  输出
  7

* */
public class 寻找众数中的中位数 {
    // 寻找众数中的中位数
    // 解题思路：遍历输入的数组，用一个map存储各个数组以及出现的次数，找出出现次数最多的数存在一个集合中，对集合进行排序，获取中位数
    public static void test034() {
        Scanner sc = new Scanner(System.in);
        String[] split = sc.nextLine().split(" ");
        // key：数字 value：出现次数
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < split.length; i++) {
            int num = Integer.parseInt(split[i]);
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        // 最大的出现次数
        int count = 0;
        for (Integer m : map.keySet()) {
            // 获取最大的出现次数
            count = Math.max(count, map.get(m));
        }

        // 存储众数
        List<Integer> list = new ArrayList<>();
        // 获取众数
        for (Integer m : map.keySet()) {
            if (map.get(m) >= count) {
                list.add(m);
            }
        }
        // 对数组进行排序
        Collections.sort(list);
        int size = list.size();
        // 获取中位数
        if (size % 2 == 0) {
            System.out.println((list.get(size / 2 - 1) + list.get(size / 2)) / 2);
        } else {
            System.out.println(list.get(size / 2));
        }
    }

}

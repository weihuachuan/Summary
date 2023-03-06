package Niuke.acm.acm3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
*   公司用一个字符串来标识员工的出勤信息
  absent:    缺勤
  late:      迟到
  leaveearly:早退
  present:   正常上班

  现需根据员工出勤信息,判断本次是否能获得出勤奖,
  能获得出勤奖的条件如下：
  1.缺勤不超过1次
  2.没有连续的迟到/早退
  3.任意连续7次考勤 缺勤/迟到/早退 不超过3次

  输入描述：
  用户的考勤数据字符串记录条数  >=1
  输入字符串长度 <10000 ;
  不存在非法输入
  如：
  2
  present
  present absent present present leaveearly present absent

  输出描述：
  根据考勤数据字符串
  如果能得到考勤奖输出true否则输出false
  对于输出示例的结果应为
  true false

  示例一：
  输入：
  2
  present
  present present
  输出：
  true true

  示例二
  输入：
  2
  present
  present absent present present leaveearly present absent
  输出：
  true false
*/
public class 员工出勤奖 {
    // 员工出勤奖
    // 解题思路：将输入的内容放在集合中，遍历集合，按条件依次判断
    public static void test059() {
        Scanner sc = new Scanner(System.in);
        int num = Integer.parseInt(sc.nextLine());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            String line = sc.nextLine();
            list.add(line);
        }
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            String[] split = str.split(" ");
            // 1.缺勤不超过1次
            int absentCount = 0;
            for (int j = 0; j < split.length; j++) {
                if ("absent".equals(split[j])) {
                    absentCount++;
                }
            }
            if (absentCount > 1) {
                System.out.print(false + " ");
                // 退出此次循环
                continue;
            }
            // 2.没有连续的迟到/早退
            if (str.contains("late leaveearly") || str.contains("leaveearly late") || str.contains("leaveearly leaveearly") || str.contains("late late")) {
                System.out.print(false + " ");
                // 退出此次循环
                continue;
            }
            // 3.任意连续7次考勤 缺勤/迟到/早退 不超过3次
            // 用于存放 缺勤/迟到/早退 最大次数
            int count = 0;
            // 用于存放 缺勤/迟到/早退 临时次数
            int noPresent = 0;
            if (split.length > 7) {
                for (int j = 0; j + 7 <= split.length; j++) {
                    for (int k = j; k < 7 + j; k++) {
                        if ("absent".equals(split[k]) || "late".equals(split[k]) || "leaveearly".equals(split[k])) {
                            noPresent++;
                        }
                    }
                    count = Math.max(count, noPresent);
                    noPresent = 0;
                }
            } else {
                for (int j = 0; j < split.length; j++) {
                    if ("absent".equals(split[j]) || "late".equals(split[j]) || "leaveearly".equals(split[j])) {
                        count++;
                    }
                }
            }
            if (count > 3) {
                System.out.print(false + " ");
                // 退出此次循环
                continue;
            }
            // 都符合，则输出true
            System.out.print(true + " ");
        }
    }

}

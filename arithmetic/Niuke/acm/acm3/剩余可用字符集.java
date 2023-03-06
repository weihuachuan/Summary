package Niuke.acm.acm3;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/*
*   给定两个字符集合
  一个是全量字符集
  一个是已占用字符集
  已占用字符集中的字符不能再使用
  要求输出剩余可用字符集

  输入描述
  1. 输入一个字符串 一定包含@

  @前为全量字符集 @后的为已占用字符集
  2. 已占用字符集中的字符
  一定是全量字符集中的字符
  字符集中的字符跟字符之间使用英文逗号隔开
  3. 每个字符都表示为字符+数字的形式
  用英文冒号分隔
  比如a:1标识一个a字符
  4. 字符只考虑英文字母，区分大小写
  数字只考虑正整型 不超过100
  5. 如果一个字符都没被占用 @标识仍存在
  例如 a:3,b:5,c:2@

  输出描述：
  输出可用字符集
  不同的输出字符集之间用回车换行
  注意 输出的字符顺序要跟输入的一致
  不能输出b:3,a:2,c:2
  如果某个字符已全部占用 则不需要再输出

  示例一：
  输入
  a:3,b:5,c:2@a:1,b:2
  输出
  a:2,b:3,c:2
  说明：
  全量字符集为三个a，5个b，2个c
  已占用字符集为1个a，2个b
  由于已占用字符不能再使用
  因此剩余可用字符为2个a，3个b，2个c
  因此输出a:2,b:3,c:2
*/
public class 剩余可用字符集 {
    // 如果全量字符中有重复的，是相加还是取最后一个？？ 我做的是相加，如果取最后一个则先remove，在put，就能保证插入顺序
    // 剩余可用字符集
    // 解题思路：使用按插入顺序排列的LinkedHashMap来存储全量字符集，在减掉已用字符集
    public static void test063() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] split = line.split("@");
        // 一个字符都没被占用
        if (line.charAt(line.length() - 1) == '@') {
            System.out.println(line.substring(0, line.length() - 1));
            return;
        }
        String[] allString = split[0].split(",");
        String[] userString = split[1].split(",");
        // LinkedHashMap 按插入顺序排列
        Map<String, Integer> map = new LinkedHashMap<>();
        // 遍历全量字符串，存进LinkedHashMap中
        for (int i = 0; i < allString.length; i++) {
            String[] strAndNum = allString[i].split(":");
            String str = strAndNum[0];
            int num = Integer.parseInt(strAndNum[1]);
            if (map.containsKey(str)) {
                map.put(str, map.get(str) + num);
            } else {
                map.put(str, num);
            }
        }
        // 遍历用过的字符串，在LinkedHashMap中减掉使用的次数
        for (int i = 0; i < userString.length; i++) {
            String[] strAndNum = userString[i].split(":");
            String str = strAndNum[0];
            int num = Integer.parseInt(strAndNum[1]);
            map.put(str, map.get(str) - num);
        }
        // 拼接输出的字符串
        StringBuilder sb = new StringBuilder();
        for (String s : map.keySet()) {
            // 只输出有剩余的字符串
            if (map.get(s) > 0) {
                sb.append(s + ":" + map.get(s) + ",");
            }
        }
        System.out.println(sb.substring(0, sb.length() - 1));
    }

}

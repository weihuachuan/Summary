package Niuke;

import java.util.*;

public class main {
    public static void main(String[] args) {
        test078();
    }
    // 时间排序
    public static void test078() {
        Scanner sc = new Scanner(System.in);
        // 输入的个数
        int len = Integer.parseInt(sc.nextLine());
        List<String> list = new ArrayList<>();
        int i = 0;
        while (i < len) {
            list.add(sc.nextLine());
            i++;
        }
        sc.close();
        // 对集合进行排序  a1与a2比较，结果大于0互换位置 小于等于0不变
        list.stream().sorted((a1, a2) -> {
            // 根据规则切分出时 分 秒 毫秒
            String[] time1 = a1.split(":");
            String[] time2 = a2.split(":");

            // 用.进行分割时需要用到转义字符 \\
            String[] s_n1 = time1[2].split("\\.");
            String[] s_n2 = time2[2].split("\\.");
            int h1 = Integer.parseInt(time1[0]);
            int h2 = Integer.parseInt(time2[0]);
            int m1 = Integer.parseInt(time1[1]);
            int m2 = Integer.parseInt(time2[1]);
            int s1 = Integer.parseInt(s_n1[0]);
            int s2 = Integer.parseInt(s_n2[0]);
            int n1 = Integer.parseInt(s_n1[1]);
            int n2 = Integer.parseInt(s_n2[1]);

            // 小时不相等比较小时
            if (h1 != h2) {
                return h1 - h2;
            }
            // 分钟不相等比较分钟
            if (m1 != m2) {
                return m1 - m2;
            }
            // 秒不相等比较秒
            if (s1 != s2) {
                return s1 - s2;
            }
            // 毫秒不相等比较毫秒
            if (n1 != n2) {
                return n1 - n2;
            }
            // 都相等按原来顺序
            return 0;
        }).forEach((a) -> {
            System.out.println(a);
        });
    }
}

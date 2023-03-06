package Niuke.acm.acm4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
*  磁盘的容量单位有M,G,T这三个等级
 他们之间的换算关系为
 1T=1024G
 1G=1024M
 现在给定N块磁盘的容量
 请对他们按从小到大的顺序进行稳定排序
 例如给定5块盘容量
 1T,20M,3G,10G6T,3M12G9M
 排序后的结果为20M,3G,3M12G9M,1T,10G6T
 注意单位可以重复出现
 上述3M12G9M为 3M+12G+9M和 12M12G相等

 输入描述:
 输入第一行包含一个整数N
 2<=N<=100 ,表示磁盘的个数
 接下来的N行每行一个字符串 长度 (2<l<30)
 表示磁盘的容量
 有一个或多个格式为 mv的子串组成
 其中m表示容量大小 v表示容量单位
 例如
 磁盘容量m的范围 1~1024正整数
 容量单位v的范围包含题目中提到的M,G,T
 输出描述:
 输出N行
 表示N块磁盘容量排序后的结果

 示例1:
 输入
 3
 1G
 2G
 1024M
 输出
 1G
 1024M
 2G
 说明 1G和1024M容量相等,稳定排序要求保留他们原来的相对位置
 故1G在1024M前

 示例二:
 输入
 3
 2G4M
 3M2G
 1T
 输出
 3M2G
 2G4M
 1T
 说明1T大于2G4M大于3M2G

* */
public class 磁盘容量排序 {
    // 磁盘容量排序
    // 解题思路：将输入的值放在集合中，将每个元素都转换为M为单位，在进行比较排序
    public static void test052_2() {
        Scanner sc = new Scanner(System.in);
        int len = Integer.parseInt(sc.nextLine());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(sc.nextLine());
        }
        list.sort((s1, s2) -> {
            // 复杂的写法，使用双层循环分别求出每个数字和字母
            char[] chars1 = s1.toCharArray();
            char[] chars2 = s2.toCharArray();
            // 使用long型防止int型不够用
            long allNum1 = 0;
            long allNum2 = 0;
            for (int i = 0; i < chars1.length; i++) {
                String num = "";
                // 判断是否是数字->获得字母前的数字
                while (i < chars1.length && Character.isDigit(chars1[i])) {
                    // 拼接数字
                    num = num + chars1[i];
                    i++;
                }
                if (chars1[i] == 'M') {
                    allNum1 += Integer.parseInt(num);
                } else if (chars1[i] == 'G') {
                    allNum1 += Integer.parseInt(num) * 1024;
                } else {
                    allNum1 += Integer.parseInt(num) * 1024 * 1024;
                }
            }
            for (int i = 0; i < chars2.length; i++) {
                String num = "";
                // 判断是否是数字->获得字母前的数字
                while (i < chars2.length && Character.isDigit(chars2[i])) {
                    // 拼接数字
                    num = num + chars2[i];
                    i++;
                }
                if (chars2[i] == 'M') {
                    allNum2 += Integer.parseInt(num);
                } else if (chars2[i] == 'G') {
                    allNum2 += Integer.parseInt(num) * 1024;
                } else {
                    allNum2 += Integer.parseInt(num) * 1024 * 1024;
                }
            }
            // 比较两个long型数据，x > y 返回1，x < y返回-1，x = y返回0
            return Long.compare(allNum1, allNum2);
        });
        for (String s : list) {
            System.out.println(s);
        }
    }

    // 巧妙的解法
    public static void test052() {
        Scanner sc = new Scanner(System.in);
        int len = Integer.parseInt(sc.nextLine());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            list.add(sc.nextLine());
        }
        // 对数组进行排序
        list.sort((s1, s2) -> {
            long num1 = toNumber(s1);
            long num2 = toNumber(s2);
            // 比较两个long型数据，x > y 返回1，x < y返回-1，x = y返回0
            return Long.compare(num1, num2);
        });
        for (String s : list) {
            System.out.println(s);
        }
    }

    /**
     * 将字符串转为具体多少M，返回一个long型数字
     *
     * @return
     */
    private static long toNumber(String s) {
        // 正则表达式：根据字母M或G或T进行分割
        String[] numArray = s.split("[M|G|T]"); // 结果[1,2,3]
        // 正则表达式：根据1个或多个数字进行分割
        String[] letterArray = s.split("[0-9]+"); // 结果[,M,T,G] 因为字符串以数字开头，所以数组第一个是空
        // 返回的结果
        long sum = 0;
        for (int i = 1; i < letterArray.length; i++) {
            String letter = letterArray[i];
            long num = Long.parseLong(numArray[i - 1]);
            if ("M".equals(letter)) {
                sum += num;
            } else if ("G".equals(letter)) {
                sum += 1024 * num;
            } else if ("T".equals(letter)) {
                sum += 1024 * 1024 * num;
            }
        }
        return sum;
    }

}

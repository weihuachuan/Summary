package Niuke.acm.acm5;

import java.util.Scanner;

/*
*   有一个数列A[n]
  从A[0]开始每一项都是一个数字
  数列中A[n+1]都是A[n]的描述
  其中A[0]=1
  规则如下
  A[0]:1
  A[1]:11 含义其中A[0]=1是1个1 即11
  表示A[0]从左到右连续出现了1次1
  A[2]:21 含义其中A[1]=11是2个1 即21
  表示A[1]从左到右连续出现了2次1
  A[3]:1211 含义其中A[2]从左到右是由一个2和一个1组成 即1211
  表示A[2]从左到右连续出现了一次2又连续出现了一次1
  A[4]:111221  含义A[3]=1211 从左到右是由一个1和一个2两个1 即111221
  表示A[3]从左到右连续出现了一次1又连续出现了一次2又连续出现了2次1

  输出第n项的结果
  0<= n <=59
  输入描述：
  数列第n项   0<= n <=59
  4
  输出描述
  数列内容
  111221

* */
public class 数列解析 {
    // 数列解析
    // 解题思路：根据A[0]循环求出A[1]、A[2]....
    public static void test039() {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        if (num == 0) {
            System.out.println(1);
            return;
        }
        // 初始值A[0]
        String s = "1";
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= num; i++) {
            char[] chars = s.toCharArray();
            // 遍历字符串的每个字符
            for (int j = 0; j < chars.length; j++) {
                int start = j;
                // 相同字符
                while (j + 1 < chars.length && chars[j] == chars[j + 1]) {
                    j++;
                }
                // 只有一个
                if (start == j) {
                    sb.append(1).append(chars[j]);
                } else { // 有多个
                    sb.append(j - start + 1).append(chars[j]);
                }
            }
            // 替换初始值
            s = sb.toString();
            // 置空
            sb = new StringBuilder();
        }
        System.out.println(s);
    }

}

package Niuke.acm.acm1;

import java.util.Scanner;

/*
* 1.输入字符串s输出s中包含所有整数的最小和，
  说明：1字符串s只包含a~z,A~Z,+,-，
2.合法的整数包括正整数，一个或者多个0-9组成，如：0,2,3,002,102
3.负整数，负号开头，数字部分由一个或者多个0-9组成，如-2,-012,-23,-00023
  输入描述：包含数字的字符串
  输出描述：所有整数的最小和
示例：
    输入：
      bb1234aa
    输出
      10
    输入：
      bb12-34aa
    输出：
      -31
    说明：1+2-(34)=-31
*/
public class 输出字符串中最小数字 {
    public static void main(String[] args) {
        test082();
    }
    // 输出字符串中最小数字
    public static void test082() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        // 将字符串转为字符数组
        char[] chars = line.toCharArray();
        // 累加结果
        int sum = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '-') {
                int start = i;
                i++;
                while (i < chars.length && Character.isDigit(chars[i])) {
                    i++;
                }
                // 截取负数
                String substring = line.substring(start, i);
                // 当substring = 1时只有一个负号
                if (substring.length() > 1) {
                    sum += Integer.parseInt(substring);
                }
            }

            // 判断字符是不是数字
            if (Character.isDigit(c)) {
                // 将字符数字转为数字,当字符数字小于第二个参数时，返回-1
                sum += Character.digit(c, 10);
            }
        }

        System.out.println(sum);
    }

}

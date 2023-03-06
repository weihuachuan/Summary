package Niuke.hw_easy;

import java.util.Scanner;

/*
* 描述
求一个int类型数字对应的二进制数字中1的最大连续数，例如3的二进制为00000011，最大连续2个1

输入描述：
输入一个int类型数字

输出描述：
输出转成二进制之后连续1的个数

示例1
输入：
200
复制
输出：
2
复制
说明：
200的二进制表示是11001000，最多有2个连续的1。
* */
public class 求最大连续bit数 {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            int num = scanner.nextInt();
            // 转二进制
            String binaryStr = Integer.toBinaryString(num);
            // 用0 分割
            String[] strArray = binaryStr.split("0");
            // 字符串长度
            int result = 0;
            for (int i = 0; i < strArray.length; i++) {
                if (strArray[i].length() > result) {
                    result = strArray[i].length();
                }
            }
            System.out.println(result);
        }
    }
}

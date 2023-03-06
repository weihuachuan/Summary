package Niuke.hw1;

import java.util.Scanner;

/*
* 描述
输入两个用字符串 str 表示的整数，求它们所表示的数之和。

数据范围： 1 \le len(str) \le 10000 \1≤len(str)≤10000
输入描述：
输入两个字符串。保证字符串只含有'0'~'9'字符

输出描述：
输出求和后的结果

示例1
输入：
9876543210
1234567890
复制
输出：
11111111100
* */
public class 高精度整数加法 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String s1 = scan.next();
            String s2 = scan.next(); //输入两个数
            String res = add(s1, s2); //输出
            System.out.println(res);
        }
    }

    private static String add(String s1, String s2) { //两个字符串整数相加
        StringBuilder res = new StringBuilder();
        int n = s1.length() - 1;
        int m = s2.length() - 1;
        int carry = 0; //进位
        while (n >= 0 || m >= 0) { //从两个人字符串最后一位开始相加
            char c1 = n >= 0 ? s1.charAt(n--) : '0'; //没有了就用0代替
            char c2 = m >= 0 ? s2.charAt(m--) : '0';
            int sum = (c1 - '0') + (c2 - '0') + carry; //两个数子与进位相加
            res.append(sum % 10); //余数添加进结果
            carry = sum / 10;  //进位
        }

        if (carry == 1) { //最后的进位
            res.append(carry);
        }
        return res.reverse().toString(); //反转后转成字符串
    }
//    public static void main(String[] args) {
//        Scanner scan = new Scanner(System.in);
//        while (scan.hasNext()) {
//            String s1 = scan.next();
//            String s2 = scan.next(); //输入两个数
//            BigInteger a = new BigInteger(s1); //将字符串转成大整数
//            BigInteger b = new BigInteger(s2);
//            System.out.println(a.add(b)); //大整数相加
//        }
//    }
}

package Niuke.acm.acm3;

import java.util.Scanner;

/*
*  有一种简易压缩算法：针对全部为小写英文字母组成的字符串，
 将其中连续超过两个相同字母的部分压缩为连续个数加该字母
 其他部分保持原样不变.
 例如字符串aaabbccccd  经过压缩变成字符串 3abb4cd
 请您编写解压函数,根据输入的字符串,
 判断其是否为合法压缩过的字符串
 若输入合法则输出解压缩后的字符串
 否则输出字符串"!error"来报告错误

 输入描述
 输入一行，为一个ASCII字符串
 长度不超过100字符
 用例保证输出的字符串长度也不会超过100字符串

 输出描述
 若判断输入为合法的经过压缩后的字符串
 则输出压缩前的字符串
 若输入不合法 则输出字符串"!error"

 示例一：
 输入
 4dff
 输出
 ddddff
 说明
 4d扩展为4个d ，故解压后的字符串为ddddff

 示例二
 输入
 2dff
 输出
 !error
 说明
 2个d不需要压缩 故输入不合法

 示例三
 输入
 4d@A
 输出
 !error
 说明
 全部由小写英文字母做成的字符串，压缩后不会出现特殊字符@和大写字母A
 故输入不合法
*/
public class 字符串解压缩 {
    // 字符串解压缩
    // 解题思路：遍历每个字符，取出字符前的数字进行解压缩
    public static void test062(){
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        // 替换掉合法字符
        String replaceAll = line.replaceAll("[1-9]|[a-z]", "");
        // 替换掉合法字符后，长度不为0，则不合法 || 最后一个字符是数字，不合法
        if (replaceAll.length() > 0 || Character.isDigit(line.charAt(line.length() - 1))) {
            System.out.println("!error");
            return;
        }
        // 存放结果
        String resLine = line;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            // 获取字符前的数字
            if (Character.isDigit(c)){
                int start = i;
                while (Character.isDigit(line.charAt(i+1))){
                    i++;
                }
                int num = Integer.parseInt(line.substring(start, i+1));
                // 2不需要压缩，不合法
                if (num <= 2) {
                    System.out.println("!error");
                    return;
                }
                // 解压缩
                resLine = resLine.replaceFirst(num+"", getString(num, line.charAt(i + 1)));
            }
        }
        System.out.println(resLine);
    }

    private static String getString(int num, char c) {
        String str =String.valueOf(c);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<num;i++){
            sb.append(str);
        }
        return sb.toString();
    }

}

package Niuke.hw_easy;

import java.util.Scanner;
/*
* 描述
判断短字符串S中的所有字符是否在长字符串T中全部出现。
请注意本题有多组样例输入。

进阶：时间复杂度：

O(n) ，空间复杂度：


O(n)
输入描述：
输入两个字符串。第一个为短字符串，第二个为长字符串。两个字符串均由小写字母组成。

输出描述：
如果短字符串的所有字符均在长字符串中出现过，则输出字符串"true"。否则输出字符串"false"。

示例1
输入：
bc
abc
复制
输出：
true
复制
说明：
其中abc含有bc，输出"true"
* */
public class 字符串字符匹配 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String sub = sc.nextLine();
            String str = sc.nextLine();
            boolean flag = true;
            for (int i = 0; i < sub.length(); i++) {
                if (!str.contains(String.valueOf(sub.charAt(i)))) {
                    flag = false;
                }
            }
            System.out.println(flag);

        }
        sc.close();
    }
}

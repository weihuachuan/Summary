package Niuke.acm.acm2;

import java.util.Scanner;

/*
*  游戏规则：
  输入一个只包含英文字母的字符串,
  字符串中的两个字母如果相邻且相同,就可以消除。
  在字符串上反复执行消除的动作,
  直到无法继续消除为止,此时游戏结束。
  输出最终得到的字符串长度.
  输入描述：
  输入原始字符串str
  只能包含大小写英文字母,字母的大小写敏感,
  str长度不超过100

  输出描述
  输出游戏结束后,最终得到的字符串长度

  示例一：
  输入
  gg
  输出
  0
  说明 gg可以直接消除 得到空串 长度为0

  示例2
  输入：
  mMbccbc
  输出
  3
  说明mMbccbc中 可以先消除cc 此时变为mMbbc
  再消除 bb 此时变成mMc
  此时没有相同且相邻的字符 无法继续消除
  最终得到字符串mMc  长度为3

  备注：
  输入中包含非大小写英文字母时
  均为异常输入
  直接返回0
*/
public class 消除相邻且相同字母 {
    // PS:字符串中的<两个>字母如果相邻且相同,就可以消除,相同的字符只能两两删除，比如有3个只能删2个
    // 消除相邻且相同字母
    public static void test069() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        // replaceAll 可以用正则表达式； [a-zA-Z]:匹配a-zA-Z其中一个
        //String newLine = line.replaceAll("[a-z]|[A-Z]", "");
        String newLine = line.replaceAll("[a-zA-Z]", "");
        // 异常输入
        if (newLine.length() > 0) {
            System.out.println(0);
            return;
        }
        for (int i = 0; i < line.length() - 1; i++){
            if (line.charAt(i) == line.charAt(i+1)) {
                // 两个字符相等，则消除
                line = line.replaceFirst(line.substring(i, i+2), "");
                // 需要把指针回到前一个字符
                if (i == 0){
                    i = i - 1; // 因为是开头，只需要-1抵消后面的i++
                } else {
                    i = i - 2; // -1回退到前一个字符，再-1抵消后面的i++
                }
            }
        }
//        System.out.println(line);
        System.out.println(line.length());

    }

}

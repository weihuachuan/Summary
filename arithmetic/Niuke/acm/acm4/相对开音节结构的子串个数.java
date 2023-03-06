package Niuke.acm.acm4;

import java.util.Scanner;
/*
  相对开音节构成的结构为辅音+元音(aeiou)+辅音(r除外)+e
  常见的单词有bike cake
  给定一个字符串，以空格为分隔符
  反转每个单词的字母
  若单词中包含如数字等其他非字母时不进行反转
  反转后计算其中含有相对开音节结构的子串个数
  (连续子串中部分字符可以重复)

  输入描述
  字符串 以空格分割的多个单词
  长度<10000 字母只考虑小写

  输出描述
  含有相对开音节结构的子串个数

  示例1：
  输入
  ekam a ekac
  输出
  2
  说明：
  反转后为  make a cake 其中make和cake为相对开音节子串
  返回2

  示例2：
  输入
  !ekam a ekekac
  输出
  2
  说明
  反转后为 !ekam a cakeke
  因为!ekam含有非英文字母，所以未反转
  其中 cake和keke 为相对开音节子串 返回2

* */
public class 相对开音节结构的子串个数 {
    // 疑问：不进行反转的单词要不要算相对开音节子串？我这里是算的，如果不算的话，则将反转后的单词单独放到集合里，在进行处理
    // 相对开音节结构的子串个数
    // 解题思路：遍历所有单词，根据条件进行反转，反转后在计算相对开音节结构的子串个数
    public static void main(String[] args) {
        test047();
    }
    public static void test047() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] split = line.split(" ");
        // 相对开音节结构的子串个数
        int count = 0;
        for (int i = 0; i < split.length; i++) {
            String str = split[i];
            // 若单词中包含如数字等其他非字母时不进行反转
            if (str.replaceAll("[a-z]", "").length() == 0) {
                String newStr = "";
                for (int j = 0; j < str.length(); j++) {
                    newStr = str.charAt(j) + newStr;
                }
                str = newStr;
            }

            // 计算相对开音节结构的子串个数
            for (int k = 0; k < str.length() - 3; k++) {
                // 辅音+元音(aeiou)+辅音(r除外)+e
                if (!"aeiou".contains(str.charAt(k) + "")
                        && "aeiou".contains(str.charAt(k + 1) + "")
                        && !"aeiour".contains(str.charAt(k + 2) + "")
                        && 'e' == str.charAt(k + 3)) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

}

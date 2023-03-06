package Niuke.acm.acm4;

import java.util.Scanner;

/*
*   输入一个字符串仅包含大小写字母和数字
  求字符串中包含的最长的非严格递增连续数字序列长度
  比如：
  12234属于非严格递增数字序列
  示例：
  输入
  abc2234019A334bc
  输出
  4
  说明：
  2234为最长的非严格递增连续数字序列，所以长度为4

  输入
  aaaaaa44ko543j123j7345677781
  输出
  8

  输入
  aaaaa34567778a44ko543j123j71
  输出
  8

  输入
  345678a44ko543j123j7134567778aa
  输出
  9

  输入
  fwefksoSKJF12S45DS3DSAJKSldsf565441345sd1f87151234657812154341543
  输出
  5

* */
public class 非严格递增连续数字序列长度 {
    // 非严格递增连续数字序列长度
    // 解题思路：遍历每一个字符，按照规则计算非严格递增连续数字序列长度，保留最大长度
    public static void test051() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        char[] chars = line.toCharArray();
        // 当前非严格递增长度
        int currentLen = 0;
        // 非严格递增最大长度
        int maxLen = 0;
        // 上一个字符，默认一个比数字大的
        char last = 'a';
        for (char c : chars) {
            if (Character.isDigit(c)) { // 当前字符是数字
                if (currentLen == 0) { // 当数字是开头时
                    currentLen++;
                } else if (c >= last) { // 符合非严格递增，当前非严格递增长度长度
                    currentLen++;
                } else { // 不符合非严格递增，保留最大的非严格递增长度
                    maxLen = Math.max(currentLen, maxLen);
                    // 初始化当前严格递增长度,因为当前是数字，所以其本身长度是1
                    currentLen = 1;
                }
                // 将当前数字置为上一个，用于比较
                last = c;
            } else { // 当前字符不是数字
                // 保留最大的非严格递增长度
                maxLen = Math.max(maxLen, currentLen);
                // 初始化当前严格递增长度
                currentLen = 0;
            }
        }
        System.out.println(maxLen);
    }

}

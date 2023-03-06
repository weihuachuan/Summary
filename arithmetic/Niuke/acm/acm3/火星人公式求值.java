package Niuke.acm.acm3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
*   已知火星人使用的运算符号为#;$
  其与地球人的等价公式如下
  x#y=2*x+3*y+4
  x$y=3*x+y+2
  x y是无符号整数
  地球人公式按照c语言规则进行计算
  火星人公式中$符优先级高于# 相同的运算符按从左到右的顺序运算

  输入描述：
  火星人字符串表达式结尾不带回车换行
  输入的字符串说明是 字符串为仅有无符号整数和操作符组成的计算表达式

  1.用例保证字符串中操作数与操作符之间没有任何分隔符
  2.用例保证操作数取值范围为32位无符号整数，
  3.保证输入以及计算结果不会出现整型溢出
  4.保证输入的字符串为合法的求值报文
  例如: 123#4$5#76$78
  5.保证不会出现非法的求值报文
  例如: #4$5 这种缺少操作数
  4$5#  这种缺少操作数
  4#$5  这种缺少操作数
  4 $5  有空格
  3+4-5*6/7 有其他操作符
  12345678987654321$54321 32位整数溢出

  输出描述：
  根据火星人字符串输出计算结果
  结尾不带回车换行

  案例1：
  输入：
  7#6$5#12
  输出：
  226

  说明 示例7#6$5#12=7#(3*6+5+2)#12
  =7#25#12
  =(2*7+3*25+4)#12
  =93#12
  =2*93+3*12+4
  =226

* */
public class 火星人公式求值 {
    // 火星人公式求值
    // 解题思路：根据数字、#$ 将字符串分割成两个数组，先计算$，在计算#
    public static void test046() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] sign = line.split("[0-9]+"); // { ,#,$,$}
        List<String> signList = new ArrayList<>();
        // 数字前面是空串，所以从1开始
        for (int i = 1; i < sign.length; i++) {
            signList.add(sign[i]);
        }
        String[] nums = line.split("[$|#]+"); // {1,2,23,34}
        List<Integer> numsList = new ArrayList();
        for (int i = 0; i < nums.length; i++) {
            numsList.add(Integer.parseInt(nums[i]));
        }

        // 找出所有的$
        while (signList.indexOf("$") != -1) {
            int index$ = signList.indexOf("$");
            // 计算$表达式
            int sum = calculation$(numsList.get(index$), numsList.get(index$ + 1));
            // 替换 $前后的数字 为 $表达式的值
            numsList.set(index$, sum);
            numsList.remove(index$ + 1);
            // 移除$符号
            signList.remove(index$);
        }
        // 计算#表达式的值
        int res = numsList.get(0);
        for (int i = 1; i < numsList.size(); i++) {
            res = calculation(res, numsList.get(i));
        }
        System.out.println(res);
    }

    /**
     * x$y=3*x+y+2
     * @param num1
     * @param num2
     * @return
     */
    private static int calculation$(int num1, int num2) {
        return 3 * num1 + num2 + 2;
    }

    /**
     * x#y=2*x+3*y+4
     * @param num1
     * @param num2
     * @return
     */
    private static int calculation(int num1, int num2) {
        return 2 * num1 + 3 * num2 + 4;
    }

}

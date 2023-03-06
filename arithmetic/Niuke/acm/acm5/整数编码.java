package Niuke.acm.acm5;

import java.util.Scanner;

/*
*   实现一个整数编码方法
  使得待编码的数字越小
  编码后所占用的字节数越小
  编码规则如下
  1.编码时7位一组，每个字节的低7位用于存储待编码数字的补码
  2.字节的最高位表示后续是否还有字节，置1表示后面还有更多的字节，
  置0表示当前字节为最后一个字节
  3.采用小端序编码，低位和低字节放在低地址上
  4.编码结果按16进制数的字符格式进行输出，小写字母需要转化为大写字母

  输入描述
  输入的为一个字符串表示的非负整数
  输出描述
  输出一个字符串表示整数编码的16进制码流

  示例一
  输入
  0
  输出
  00
  说明：输出的16进制字符不足两位的前面补零

  示例二
  输入
  100
  输出
  64
  说明:100的二进制表示为0110 0100只需一个字节进行编码
  字节的最高位0，剩余7位存储数字100的低7位(1100100)所以编码后的输出为64

  示例三
  输入
  1000
  输出
  E807
  说明
  1000的二进制表示为 0011 1110 1000 至少需要两个字节进行编码
  第一个字节最高位是1 剩余7位存储数字 1000的低7位(1101000)
  所以第一个字节的二进制位(1110 1000)即E8
  第二个字节最高位置0 剩余的7位存储数字 1000的第二个低7位(0000111)
  所以第一个字节的二进制为(0000 0111)即07
  采用小端序编码 所以低字节E8输出在前面
  高字节07输出在后面

  备注
  代编码数字取值范围为 [0,1<<64-1]

* */
public class 整数编码 {
    // 整数编码
    // 解题思路：将输入的数转为二进制数，从后往前依次截取7位，根据要求拼接0或1，在把每个结果拼接起来
    // 小端序编码 大概意思就是原来低位放在高位，高位放在低位，就是颠倒过来。在这里的意思就是低位转的放在前面，越高位转的放在越后面(可能理解有误)
    // 代码通过率没有100%，自己的测试数据都能过，暂不知道原因，能看出异常的请告诉我！！！
    public static void test043() {
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        // 将输入的数字转为二进制
        String binaryValue = Integer.toBinaryString(input);
        int len = binaryValue.length();
        if (len <= 7) {
            int parseInt = Integer.parseInt("0" + binaryValue, 2);
            System.out.println(tenTo16(parseInt));
            return;
        }
        int count = len / 7;
        // 存放结果
        String res = "";
        for (int i = 0; i < count; i++) {
            // 存储每7位的十进制数
            int num = 0;
            // 从后往前截取7位
            String lastStr = binaryValue.substring(len - 7 * (i + 1), len - 7 * i);
            // 前几位
            if (i == count - 1) {
                // len % 7 == 0 说明没有了，所以补0
                if (len % 7 == 0) {
                    // 拼接0
                    num = Integer.parseInt("0" + lastStr, 2);
                } else {
                    // 拼接1
                    num = Integer.parseInt("1" + lastStr, 2);
                }

            } else { // 其他的补1
                // 拼接1
                num = Integer.parseInt("1" + lastStr, 2);
            }
            // 小端序
            res = res + tenTo16(num);
        }
        // 取最前的几位
        if (len % 7 != 0) {
            int numTen = Integer.parseInt(binaryValue.substring(0, len - 7 * count), 2);
            String s = tenTo16(numTen);
            res = res + s;
        }
        System.out.println(res);
    }

    /**
     * 十进制转十六进制
     *
     * @return
     */
    private static String tenTo16(int num) {
        String s = "";
        do {
            int res = num % 16;
            num = num / 16;
            if (res == 15) {
                s = "F" + s;
            } else if (res == 14) {
                s = "E" + s;
            } else if (res == 13) {
                s = "D" + s;
            } else if (res == 12) {
                s = "C" + s;
            } else if (res == 11) {
                s = "B" + s;
            } else if (res == 10) {
                s = "A" + s;
            } else {
                s = res + s;
            }
        } while (num != 0);
        if (s.length() == 1) {
            return "0" + s;
        }
        return s;
    }

    /**
     * 任何进制转10进制
     * 十进制转二进制
     * 十进制转十六进制
     */
    public void study() {
        // 任何进制数转10进制，第一个参数：要转的字符串； 第二个参数：要转的字符串的进制数
        int parseInt = Integer.parseInt("110", 2);
        // 十进制转二进制
        String s = Integer.toBinaryString(123);
        // 十进制转十六进制,十六进制的字母是小写的
        String s1 = Integer.toHexString(123);
        // 字符串小写字母转大写
        String upperCase = s1.toUpperCase();
        // 字符串大写字母转小写
        String lowerCase = s1.toLowerCase();
    }

}

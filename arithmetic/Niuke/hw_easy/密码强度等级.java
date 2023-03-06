package Niuke.hw_easy;

import java.util.Scanner;

/*
* 描述
密码按如下规则进行计分，并根据不同的得分为密码进行安全等级划分。

一、密码长度:
5 分: 小于等于4 个字符
10 分: 5 到7 字符
25 分: 大于等于8 个字符

二、字母:
0 分: 没有字母
10 分: 密码里的字母全都是小（大）写字母
20 分: 密码里的字母符合”大小写混合“

三、数字:
0 分: 没有数字
10 分: 1 个数字
20 分: 大于1 个数字

四、符号:
0 分: 没有符号
10 分: 1 个符号
25 分: 大于1 个符号

五、奖励（只能选符合最多的那一种奖励）:
2 分: 字母和数字
3 分: 字母、数字和符号
5 分: 大小写字母、数字和符号

最后的评分标准:
>= 90: 非常安全
>= 80: 安全（Secure）
>= 70: 非常强
>= 60: 强（Strong）
>= 50: 一般（Average）
>= 25: 弱（Weak）
>= 0:  非常弱（Very_Weak）

对应输出为：

VERY_SECURE
SECURE
VERY_STRONG
STRONG
AVERAGE
WEAK
VERY_WEAK

请根据输入的密码字符串，进行安全评定。

注：
字母：a-z, A-Z
数字：0-9
符号包含如下： (ASCII码表可以在UltraEdit的菜单view->ASCII Table查看)
!"#$%&'()*+,-./     (ASCII码：0x21~0x2F)
:;<=>?@             (ASCII码：0x3A~0x40)
[\]^_`              (ASCII码：0x5B~0x60)
{|}~                (ASCII码：0x7B~0x7E)

提示:
1 <= 字符串的长度<= 300
输入描述：
输入一个string的密码

输出描述：
输出密码等级

示例1
输入：
38$@NoNoN
复制
输出：
VERY_SECURE
复制
说明：
样例的密码长度大于等于8个字符，得25分；大小写字母都有所以得20分；有两个数字，所以得20分；包含大于1符号，所以得25分；由于该密码包含大小写字母、数字和符号，所以奖励部分得5分，经统计得该密码的密码强度为25+20+20+25+5=95分。

示例2
输入：
Jl)M:+
复制
输出：
AVERAGE
复制
说明：
示例2的密码强度为10+20+0+25+0=55分。    */
public class 密码强度等级 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while(in.hasNextLine()){
            char[] ch = in.nextLine().toCharArray();
            int score = 0;

            //统计长度
            int len = ch.length;
            if(len <= 4) score += 5;
            else if(len >4 && len < 8) score += 10;
            else score += 25;

            //遍历获取大小写字母、数字、符号数目
            int upCount = 0;
            int lowCount = 0;
            int numCount = 0;
            int sigCount = 0;

            for (int i = 0; i < len; i++) {
                if(Character.isUpperCase(ch[i])) ++upCount;
                else if(Character.isLowerCase(ch[i])) ++lowCount;
                else if(Character.isDigit(ch[i])) ++numCount;
                else ++sigCount;
            }

            //字母分数
            if((upCount > 0 && lowCount == 0) || (upCount == 0 && lowCount > 0)) score += 10;
            else if(upCount > 0 && lowCount > 0) score += 20;
            else score += 0;

            //数字分数
            if(numCount == 1) score += 10;
            else if(numCount > 1) score += 20;
            else score += 0;

            //符号分数
            if(sigCount == 1) score += 10;
            else if(sigCount > 1) score += 25;
            else score += 0;

            //奖励分数
            if (numCount > 0 && upCount > 0 && lowCount > 0 && sigCount > 0) score += 5;
            else if(numCount > 0 && sigCount > 0 &&(upCount >0 || lowCount >0)) score += 3;
            else if(numCount > 0 &&(upCount >0 || lowCount >0)) score += 2;

            //评分
            if(score >= 90) System.out.println("VERY_SECURE");
            else if(score >= 80) System.out.println("SECURE");
            else if(score >= 70) System.out.println("VERY_STRONG");
            else if(score >= 60) System.out.println("STRONG");
            else if(score >= 50) System.out.println("AVERAGE");
            else if(score >= 25) System.out.println("WEAK");
            else System.out.println("VERY_WEAK");
        }
    }

}

package Niuke.hw;

import java.util.Scanner;

/*
* 描述
对输入的字符串进行加解密，并输出。

加密方法为：

当内容是英文字母时则用该英文字母的后一个字母替换，同时字母变换大小写,如字母a时则替换为B；字母Z时则替换为a；

当内容是数字时则把该数字加1，如0替换1，1替换2，9替换0；

其他字符不做变化。

解密方法为加密的逆过程。
数据范围：输入的两个字符串长度满足 1 \le n \le 1000 \1≤n≤1000  ，保证输入的字符串都是只由大小写字母或者数字组成
输入描述：
第一行输入一串要加密的密码
第二行输入一串加过密的密码
输出描述：
第一行输出加密后的字符
第二行输出解密后的字符
示例1
输入：
abcdefg
BCDEFGH
复制
输出：
BCDEFGH
abcdefg

* */
public class 字符串加解密 {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while(in.hasNext()){
            System.out.println(encode(in.nextLine()));
            System.out.println(decode(in.nextLine()));
        }
    }

    //加密函数
    private static String encode(String code){
        char[] t = code.toCharArray();    //将String对象转换为字符数组
        for(int i=0; i < t.length; i++){
            if(t[i]>='a' && t[i]<'z')
                t[i] = (char)(t[i] - 'a' + 'A' + 1);
            else if(t[i] == 'z')
                t[i] = 'A';
            else if(t[i]>='A' && t[i]<'Z')
                t[i] = (char)(t[i] - 'A' + 'a' + 1);
            else if(t[i] == 'Z')
                t[i] = 'a';
            else if(t[i]>='0' && t[i]<'9')
                t[i] = (char)(t[i]+1);
            else if(t[i] == '9')
                t[i] = '0';
        }
        return String.valueOf(t);
    }

    //解密函数
    private static String decode(String password){
        char[] t = password.toCharArray();
        for(int i=0; i < t.length; i++){
            if(t[i]>'a' && t[i]<='z')
                t[i] = (char)(t[i] - 'a' + 'A' - 1);
            else if(t[i] == 'a')
                t[i] = 'Z';
            else if(t[i]>'A' && t[i]<='Z')
                t[i] = (char)(t[i] - 'A' + 'a' - 1);
            else if(t[i] == 'A')
                t[i] = 'z';
            else if(t[i]>'0' && t[i]<='9')
                t[i] = (char)(t[i]-1);
            else if(t[i] == '0')
                t[i] = '9';
        }
        return String.valueOf(t);
    }
}

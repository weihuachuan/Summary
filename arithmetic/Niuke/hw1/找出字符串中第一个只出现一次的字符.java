package Niuke.hw1;

import java.util.Scanner;

/*
* 描述
找出字符串中第一个只出现一次的字符
数据范围：输入的字符串长度满足 1 \le n \le 1000 \1≤n≤1000
输入描述：
输入一个非空字符串
输出描述：
输出第一个只出现一次的字符，如果不存在输出-1
示例1
输入：
asdfasdfo
复制
输出：
o
* */
public class 找出字符串中第一个只出现一次的字符 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        //设置信号量
        int single = 0;
        for(int i=0;i<str.length();i++){
            if(str.indexOf(str.charAt(i))==str.lastIndexOf(str.charAt(i))){
                System.out.print(str.charAt(i));
                single = 1;
                break;
            }
        }
        if(single ==0){
            System.out.println(-1);
        }
        //每读一行输出一个回车
        System.out.println();
    }
}

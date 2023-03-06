package Niuke.hw_easy;

import java.util.Calendar;
import java.util.Scanner;

/*
* 描述
根据输入的日期，计算是这一年的第几天。
保证年份为4位数且日期合法。
进阶：时间复杂度：
输入描述：
输入一行，每行空格分割，分别是年，月，日

输出描述：
输出是这一年的第几天
示例1
输入：
2012 12 31
复制
输出：
366
复制
示例2
输入：
1982 3 4
复制
输出：
63
* */
public class 计算日期到天数转换 {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner in=new Scanner(System.in);
        int y=in.nextInt();
        int m=in.nextInt();
        int d=in.nextInt();
        Calendar c1=Calendar.getInstance();//实例化
        c1.set(y, m-1, d);//注意月份从0开始
        System.out.println(c1.get(Calendar.DAY_OF_YEAR));
    }
}

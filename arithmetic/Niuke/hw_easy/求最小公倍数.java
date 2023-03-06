package Niuke.hw_easy;

import java.util.Scanner;

/*
* 描述
正整数A和正整数B 的最小公倍数是指 能被A和B整除的最小的正整数值，设计一个算法，求输入A和B的最小公倍数。

数据范围：
1
≤
�
,
�
≤
100000

1≤a,b≤100000
输入描述：
输入两个正整数A和B。

输出描述：
输出A和B的最小公倍数。

示例1
输入：
5 7
复制
输出：
35
复制
示例2
输入：
2 4
复制
输出：
4*/
public class 求最小公倍数 {
    //要清楚公倍数的概念就是同时可以整除两个数。所以只要一个数逐渐累加自身到可以整除另一个数时就是既可以整除自己也可以整除另一个数，此时结果就是要得到的公倍数
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = a;                     //存储a的原始值，递归过程中使用。
        System.out.println(gcb(a,b,c));
    }
    public static int gcb(int a,int b,int c){
        if (a%b== 0){                    //a累加过程中永远可以整除自身，所以可以整除b时就是最小公倍数！
            return a;
        }
        return gcb(a+c,b,c);            //a累加自身原始值，例如a=4。  a=4,8,12,16....
    }
}

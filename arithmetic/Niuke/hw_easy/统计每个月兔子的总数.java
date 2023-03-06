package Niuke.hw_easy;

import java.util.Scanner;

/*
* 有一种兔子，从出生后第3个月起每个月都生一只兔子，小兔子长到第三个月后每个月又生一只兔子。
例子：假设一只兔子第3个月出生，那么它第5个月开始会每个月生一只兔子。
一月的时候有一只兔子，假如兔子都不死，问第n个月的兔子总数为多少？
数据范围：输入满足
1
≤
�
≤
31

1≤n≤31
输入描述：
输入一个int型整数表示第n个月

输出描述：
输出对应的兔子总数

示例1
输入：
3
复制
输出：
2
* */
public class 统计每个月兔子的总数 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            int n = sc.nextInt();
            System.out.println(f(n));
        }
    }
    public static int f(int n){
        if(n == 1 || n == 2){
            return 1;
        }
        return  f(n-1)+f(n-2);
    }
}

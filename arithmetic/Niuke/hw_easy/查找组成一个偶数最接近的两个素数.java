package Niuke.hw_easy;

import java.util.Scanner;
/*
* 描述
任意一个偶数（大于2）都可以由2个素数组成，组成偶数的2个素数有很多种情况，本题目要求输出组成指定偶数的两个素数差值最小的素数对。
输入描述：
输入一个大于2的偶数

输出描述：
从小到大输出两个素数

示例1
输入：
20
复制
输出：
7
13
复制
示例2
输入：
4
复制
输出：
2
2
* */
public class 查找组成一个偶数最接近的两个素数 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            int num = scanner.nextInt();
            solution(num);
        }
    }

    private static void solution(int num) {
        //如num=10, 遍历:5,6,7,8
        // 从最接近的两个中位数开始处理判断
        for(int i = num / 2; i < num - 1; i++) {
            if(isPrime(i) && isPrime(num - i)) {
                System.out.println((num - i) + "\n" + i);
                return;
            }
        }
    }
    // 判断是否素数
    private static boolean isPrime(int num) {
        for(int i = 2; i <= Math.sqrt(num); i++) {
            if(num % i == 0) {
                return false;
            }
        }
        return true;
    }
}

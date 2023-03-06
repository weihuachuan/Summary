package Niuke.hw_easy;

import java.util.Scanner;

/*
* 描述
功能:输入一个正整数，按照从小到大的顺序输出它的所有质因子（重复的也要列举）（如180的质因子为2 2 3 3 5 ）


数据范围：
1
≤
�
≤
2
×
1
0
9
+
14

1≤n≤2×10
9
 +14
输入描述：
输入一个整数

输出描述：
按照从小到大的顺序输出它的所有质数的因子，以空格隔开。

示例1
输入：
180
复制
输出：
2 2 3 3 5*/
public class 质数因子 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long num = sc.nextLong();
        long k = (long)Math.sqrt(num);
        for(long i=2;i<=k;i++){
            while(num%i==0){
                System.out.print(i + " ");
                num /=i;
            }
        }
        System.out.println(num==1?"":num + " ");
    }
}

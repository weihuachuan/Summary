package Niuke.hw_easy;

import java.util.Scanner;

/*
* 描述
请计算n*m的棋盘格子（n为横向的格子数，m为竖向的格子数）从棋盘左上角出发沿着边缘线从左上角走到右下角，总共有多少种走法，要求不能走回头路，即：只能往右和往下走，不能往左和往上走。

注：沿棋盘格之间的边缘线行走

数据范围：
1
≤
�
,
�
≤
8

1≤n,m≤8


输入描述：
输入两个正整数n和m，用空格隔开。(1≤n,m≤8)

输出描述：
输出一行结果

示例1
输入：
2 2
复制
输出：
6*/
public class 走方格的方案数 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int n = sc.nextInt();
            int m = sc.nextInt();
            //n m 其实表示的就是行和列数 因为是用线相连,实际就是下标多1
            int[][] dp = new int[n+1][m+1];
            for(int i=0;i<=n;i++){
                for(int j=0;j<=m;j++){
                    if(i==0 || j==0){
                        //那实际就是一条横线或者一条竖线
                        dp[i][j]=1;
                    }else{
                        //不为0的话,那其实到右下角的方法=左边前一个节点+上面一个节点
                        dp[i][j]=dp[i][j-1]+dp[i-1][j];
                    }
                }
            }
            System.out.println(dp[n][m]);
        }
    }
}

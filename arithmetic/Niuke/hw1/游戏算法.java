package Niuke.hw1;

import java.util.Scanner;

/*
*描述
给出4个1-10的数字，通过加减乘除运算，得到数字为24就算胜利,除法指实数除法运算,运算符仅允许出现在两个数字之间,本题对数字选取顺序无要求，但每个数字仅允许使用一次，且需考虑括号运算
此题允许数字重复，如3 3 4 4为合法输入，此输入一共有两个3，但是每个数字只允许使用一次，则运算过程中两个3都被选取并进行对应的计算操作。
输入描述：
读入4个[1,10]的整数，数字允许重复，测试用例保证无异常数字。

输出描述：
对于每组案例，输出一行表示能否得到24点，能输出true，不能输出false

示例1
输入：
7 2 1 10
复制
输出：
true */
public class 游戏算法 {
    public static boolean[] visited = new boolean[4];
    public static int[] nums = new int[4];
    public static boolean flag = false;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            for(int i=0;i<4;i++){
                nums[i]=sc.nextInt();
            }
            dfs(0,0);
            System.out.println(flag);
        }
    }
    public static void dfs(int start,double sum){
        //递归终止条件, start表示当前的位置 如果四个数字都遍历完了 最后start=4
        if(start==4){
            if(sum==24){
                flag=true;
            }
        }else{
            start++;
            for(int i=0;i<4;i++){
                if(!visited[i]){
                    visited[i]=true;//标记这轮循环中这个值已经访问过
                    dfs(start,sum+nums[i]);
                    dfs(start,sum-nums[i]);
                    dfs(start,sum*nums[i]);
                    dfs(start,sum/nums[i]);
                    visited[i]=false;
                }
            }
        }
    }
}

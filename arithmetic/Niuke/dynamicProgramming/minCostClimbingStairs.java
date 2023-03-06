package Niuke.dynamicProgramming;
//最小花费上楼梯
public class minCostClimbingStairs {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param cost int整型一维数组
     * @return int整型
     */
    public int minCostClimbingStairs (int[] cost) {
        // write code here
        //dp[i]表示爬到第i阶楼梯的最小花费
        int [] dp = new int[cost.length + 1];
        for(int i =2;i<=cost.length; i++){
            //每次选取最小的方案
            dp[i] = Math.min(dp[i-1] +cost[i-1],dp[i-2]+cost[i-2]);
        }
        return dp[cost.length];
    }
}

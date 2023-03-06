package Niuke.dynamicProgramming;

public class maxProfit2 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * 计算最大收益
     * @param prices int整型一维数组 股票每一天的价格
     * @return int整型
     */
    public int maxProfit (int[] prices) {
        // write code here
        //dp 记录第几天是否持有股票的收益
        int n = prices.length;
        int dp[][] = new int [n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for(int i=1;i<n;i++){
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i-1][1],dp[i-1][0] - prices[i]);
        }
        return dp[n-1][0];
    }
}

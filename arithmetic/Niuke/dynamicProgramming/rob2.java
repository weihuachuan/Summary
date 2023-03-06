package Niuke.dynamicProgramming;

import java.util.Arrays;

public class rob2 {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param nums int整型一维数组
     * @return int整型
     */
    public int rob (int[] nums) {
        // write code here
        //dp[i] 代表长度为i的数组，到底能偷多少钱
        int[] dp = new int[nums.length + 1];
        //选择偷第一家 不偷最后一家
        dp[1] = nums[0];
        for(int i =2;i < nums.length;i++){
            //对于每家选择偷
            dp[i] = Math.max(dp[i-1], nums[i-1] + dp[i-2]);
        }
        //记录下最大收益
        int res = dp[nums.length - 1];
        //清空数组
        Arrays.fill(dp,0);
        //第二次选择不偷第一家 偷最后一家
        dp[1] = 0;
        for(int i = 2;i<=nums.length;i++){
            dp[i]=Math.max(dp[i-1],nums[i-1] + dp[i-2]);
        }
        return Math.max(res,dp[nums.length]);
    }
}

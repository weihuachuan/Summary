package Niuke.dynamicProgramming;

public class rob {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param nums int整型一维数组
     * @return int整型
     */
    public int rob (int[] nums) {
        // write code here
        //dp[i] 表示长度为I的数组，最多能偷多少钱
        int [] dp =new int[nums.length+1];
        //长度为1的只能偷一家
        dp[1] = nums[0];
        for(int i =2; i<=nums.length;i++){
            //对于每家选择偷或不投
            dp[i] = Math.max(dp[i-1],nums[i-1] + dp[i -2]);
        }
        return dp[nums.length];
    }
}

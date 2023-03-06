package Niuke.dynamicProgramming;
// 凑齐 i元 要用到的最少货币数
public class minMoney {
    /**
     * 最少货币数
     * @param arr int整型一维数组 the array
     * @param aim int整型 the target
     * @return int整型
     */
    public int minMoney (int[] arr, int aim) {
        // write code here
        //小于1都返回0
        if(aim < 1){
            return 0;
        }
        //dp[i]表示凑齐i元最好需要多少货币
        int[]dp = new int[aim+1];
        dp[0] = 0;
        //遍历1-aim元
        for(int i =1;i<=aim;i++){
            //每种面值的货币都要枚举
            for(int j=0;j<arr.length;j++){
                //如果面值不超过要凑的钱数才能用
                if(arr[j] < i){
                    //维护最小值
                    dp[i] = Math.min(dp[i],dp[i-arr[j]]+ 1);
                }
            }
        }
        //如果能找到答案大于aim代表无解
        return dp[aim] > aim?-1:dp[aim];
    }
}

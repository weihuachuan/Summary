package Niuke.dynamicProgramming;

public class jumpFloor {
    public int jumpFloor(int target) {
        if(target==1){
            return 1;
        }
        int [] dp = new int[target];
        dp[0] = 1;
        dp[1] = 2;
        for(int i=2;i<target;i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[target-1];
    }
}

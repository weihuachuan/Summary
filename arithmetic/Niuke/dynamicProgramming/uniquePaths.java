package Niuke.dynamicProgramming;
//不同路径
public class uniquePaths {
    /**
     *
     * @param m int整型
     * @param n int整型
     * @return int整型
     */
    public int uniquePaths (int m, int n) {
        // write code here
        //dp[i][j]表示大小为ij的矩阵的路径数量
        int[][] dp = new int[m+1][n+1];
        for(int i=1;i<=m;i++){
            for(int j =1;j<=n;j++){
                //只有一行的时候只有一种路径
                if(i==1){
                    dp[i][j] = 1;
                    continue;
                }
                //只有一列的时候，只有一列的路径
                if(j==1){
                    dp[i][j] = 1;
                    continue;
                }
                //路径数等于左方格子的路径数加上上方格子的路径数
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m][n];
    }
}

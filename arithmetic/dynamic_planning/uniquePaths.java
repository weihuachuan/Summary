package dynamic_planning;
//不同路径
public class uniquePaths {
    public int uniquePaths(int m, int n) {
        //1.从左上角走到右下角走法和取决于左一格和上一格的走法和
        int[][] dp = new int[m][n];
        for(int i=0;i<m;i++){
            dp[i][0] = 1;
        }
        for(int j=0;j<n;j++){
            dp[0][j] = 1;
        }
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }
}

package Niuke.dynamicProgramming;
//矩阵的最短路径和
public class minPathSum {
    /**
     *
     * @param matrix int整型二维数组 the matrix
     * @return int整型
     */
    public int minPathSum (int[][] matrix) {
        // write code here
        int n = matrix.length;
        int m = matrix[0].length;
        //dp[i][j] 表示以当前i，j位置为终点的最短路径长度
        int[][] dp = new int[n+1][m+1];
        dp[0][0] = matrix[0][0];
        //处理第一列
        for(int i=1;i<n;i++){
            dp[i][0] = matrix[i][0] + dp[i-1][0];
        }
        //处理第一行
        for(int j=1;j<m;j++){
            dp[0][j] = matrix[0][j] + dp[0][j-1];
        }
        //按照公式
        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                dp[i][j] = matrix[i][j] + (dp[i-1][j]> dp[i][j-1]?dp[i][j-1]:dp[i-1][j]);
            }
        }
        return dp[n-1][m-1];
    }
}

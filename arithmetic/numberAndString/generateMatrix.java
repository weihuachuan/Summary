package numberAndString;

public class generateMatrix {
    //螺旋矩阵II
    public int[][] generateMatrix(int n) {
        //定义tar作为循环退出条件
        int tar =n*n;
        int t=0,d=n-1;
        int l=0,r=n-1;
        int num = 1;
        int [] [] ant = new int [n][n];
        while(num<=tar){
            for(int i=l;i<=r;i++) ant[t][i]=num++;
            t++;
            for(int i=t;i<=d;i++) ant[i][r]=num++;
            r--;
            for(int i=r;i>=l;i--) ant[d][i]=num++;
            d--;
            for(int i=d;i>=t;i--) ant[i][l]=num++;
            l++;
        }
        return ant;
    }
}

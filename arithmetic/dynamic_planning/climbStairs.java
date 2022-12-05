package dynamic_planning;
//爬楼梯
public class climbStairs {
    public int climbStairs(int n) {
        //1.爬楼梯 第一层 1种方法 第二层 2种方法
        if(n==1){
            return 1;
        }
        int [] ant = new int[n];
        ant[0]=1;
        ant[1]=2;
        for(int i= 2;i<n;i++){
            ant[i] = ant[i-1] + ant[i-2];
        }
        return ant[n-1];
    }
}

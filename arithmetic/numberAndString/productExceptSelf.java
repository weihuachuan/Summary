package numberAndString;

public class productExceptSelf {
    //除自身以外数据的乘积
    public int[] productExceptSelf(int[] nums) {
        int len =nums.length;
        //定义左右数组和结果数组
        int[] R =new int[len];
        int[] L =new int[len];
        int[] ans =new int[len];
        //右边数组处理
        R[0]=1;
        for(int i=1;i<len;i++){
            R[i] = nums[i-1] * R[i-1];
        }
        //左边数组处理
        L[len-1]=1;
        for(int j=len-2;j>=0;j--){
            L[j] = nums[j+1] * L[j+1];
        }
        //结果数组插入结果数据
        for(int i=0;i<len;i++){
            ans[i] = R[i] * L[i];
        }
        return ans;
    }
}

package dynamic_planning;
//最大子序和
public class maxSubArray {
    public int maxSubArray(int[] nums) {
        int pre =0,maxNum=nums[0];
        for(int num :nums){
            pre = Math.max(pre+num, num);
            maxNum = Math.max(maxNum,pre);
        }
        return maxNum;
    }
}

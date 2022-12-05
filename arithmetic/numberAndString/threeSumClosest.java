package numberAndString;

import java.util.Arrays;

public class threeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        //1.数组排序处理
        Arrays.sort(nums);
        //2.定义一个接近target的数
        int min = nums[0] + nums[1] + nums[2];
        for(int i=0;i<nums.length-2;i++){
            //3.i去重
            if(i!=0)while(nums[i] ==nums[i-1] && i< nums.length -2) i++;
            for(int j=i+1,k=nums.length-1;j<k;){
                int He = nums[i] + nums[j] + nums[k];
                if(He < target){
                    if(target - He < Math.abs(min -target))min=He;
                    j++;
                }else if(He > target){
                    if(He - target < Math.abs(min -target))min=He;
                    k--;
                }else{
                    return target;
                }
            }
        }
        return min;
    }
}

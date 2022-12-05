package numberAndString;

public class removeDuplicates {
    //删除排序数组的重复项
    public int removeDuplicates(int[] nums) {
        //要保证不用其他空间
        int i = 0;
        for(int j=1;j< nums.length;j++){
            if(nums[i]!=nums[j]){
                i++;
                nums[i] = nums[j];
            }
        }
        return i+1;
    }
}

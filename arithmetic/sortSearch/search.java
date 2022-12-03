package sortSearch;
// 搜索旋转排序数组
public class search {
    public int search(int[] nums, int target) {
        //1.定义变量N 记录数组长度
        int n = nums.length;
        if(n==0){
            return -1;
        }
        if(n==1){
            return nums[0]==target?0:-1;
        }
        int l=0,r=n-1;
        while(l<=r){
            int mid = (r+l)/2;
            if(nums[mid] ==target){
                return mid;
            }
            if(nums[0] <= nums[mid]){
                if(nums[0]<= target && target <= nums[mid]){
                    r= mid -1;
                }else{
                    l =mid+1;
                }
            }else{
                if(nums[mid] <= target && target <= nums[n-1]){
                    l=mid+1;
                }else{
                    r=mid-1;
                }
            }
        }
        return -1;
    }
}

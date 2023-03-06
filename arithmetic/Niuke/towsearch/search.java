package Niuke.towsearch;

public class search {
    public int search (int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        //从数组首尾开始，直到二者相遇
        while(l <= r){
            //每次检查中点的值
            int m = (l + r) / 2;
            if(nums[m] == target)
                return m;
            //进入左的区间
            if(nums[m] > target)
                r = m - 1;
                //进入右区间
            else
                l = m + 1;
        }
        //未找到
        return -1;
    }
}

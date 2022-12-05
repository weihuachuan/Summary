package numberAndString;

import java.util.HashSet;

public class containsDuplicate {
    //存在重复数字
    public boolean containsDuplicate(int[] nums) {
        HashSet res = new HashSet();
        for(int num:nums){
            if(!res.add(num)){
                return true;
            }
        }
        return false;
    }
}

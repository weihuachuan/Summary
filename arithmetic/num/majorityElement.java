package num;
//多数元素
public class majorityElement {
    public int majorityElement(int[] nums) {
        int count =0;
        int candidate=0;
        for(int num:nums){
            if(count==0){
                candidate=num;
            }
            count+=(candidate==num)?1:-1;

        }
        return candidate;
    }
}

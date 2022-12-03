package num;
// 只出现一次的数字
public class singleNumber {
    public int singleNumber(int[] nums) {
        int sign =0;
        for(int num:nums){
            sign ^=num;
        }
        return sign;
    }
}

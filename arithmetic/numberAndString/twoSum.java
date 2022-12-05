package numberAndString;

import java.util.HashMap;
import java.util.Map;

public class twoSum {
    //两数之和
    public int[] twoSum(int[] nums, int target) {
        /***
         * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
         *
         * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
         *
         * 你可以按任意顺序返回答案。
         * ***/
        //定义一个字典来存放值和位置
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for(int i=0;i<nums.length;i++){
            //找到当前值在字典中是否有配对
            int complete = target - nums[i];
            //判断 有配对返回
            if(map.containsKey(complete)){
                return new int[] {map.get(complete),i};
            }
            //每次循环将值和位置放进字典中
            map.put(nums[i],i);
        }
        //for循环结束后表示没有与target配对的值
        return null;
    }
}

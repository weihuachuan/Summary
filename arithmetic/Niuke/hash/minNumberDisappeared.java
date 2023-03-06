package Niuke.hash;

import java.util.HashMap;

//缺失的第一个正整数
public class minNumberDisappeared {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param nums int整型一维数组
     * @return int整型
     */
    public int minNumberDisappeared (int[] nums) {
        // write code here
        HashMap<Integer,Integer> map = new HashMap<>();
        //遍历数组
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],1);
        }
        int res =1;
        //从一开支找到哈希表第一个没有出现的正整数
        while(map.containsKey(map.get(res))){
            res++;
        }
        return res;
    }
}

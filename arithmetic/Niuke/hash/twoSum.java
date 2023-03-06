package Niuke.hash;

import java.util.HashMap;
import java.util.Map;
//两数之和
public class twoSum {
    /**
     *
     * @param numbers int整型一维数组
     * @param target int整型
     * @return int整型一维数组
     */
    public int[] twoSum (int[] numbers, int target) {
        //定义一个字典来存放值和位置
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for(int i=0;i<numbers.length;i++){
            //找到当前值在字典中是否有配对
            int complete = target - numbers[i];
            //判断 有配对返回
            if(map.containsKey(complete)){
                return new int[] {map.get(complete)+1,i+1};
            }
            //每次循环将值和位置放进字典中
            map.put(numbers[i],i);
        }
        //for循环结束后表示没有与target配对的值
        return null;
    }
}

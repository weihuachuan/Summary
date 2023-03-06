package Niuke.hash;

import java.util.HashMap;
import java.util.Map;
// 超过半数的数
public class MoreThanHalfNum_Solution {
    public int MoreThanHalfNum_Solution(int [] array) {
        //通过Hash表
        Map<Integer,Integer> map = new HashMap<>();
        //遍历数组
        for(int i=0;i<array.length;i++){
            if(!map.containsKey(array[i])){
                map.put(array[i],1);
            }else{
                map.put(array[i],map.get(array[i])+1);
            }
            if(map.get(array[i]) >array.length/2){
                return array[i];
            }
        }
        return 0;
    }
}

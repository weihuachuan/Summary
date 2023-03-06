package Niuke.hash;

import java.util.ArrayList;
import java.util.HashMap;
//数组中只出现过一次的数
public class FindNumsAppearOnce {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param array int整型一维数组
     * @return int整型一维数组
     */
    public int[] FindNumsAppearOnce (int[] array) {
        // write code here
        HashMap<Integer,Integer> map =new HashMap<>();
        ArrayList<Integer> res = new ArrayList<>();
        //遍历数组
        for(int i=0;i < array.length;i++){
            if(!map.containsKey(array[i])){
                map.put(array[i],1);
            }else{
                map.put(array[i],map.get(array[i])+1);
            }
        }
        for(int i=0;i<array.length;i++){
            //找到频率为1的两个数
            if(map.get(array[i])==1){
                res.add(array[i]);
            }
        }
        if(res.get(0) < res.get(1)){
            return new int[] {res.get(0),res.get(1)};
        }else{
            return new int[] {res.get(1),res.get(0)};
        }
    }
}

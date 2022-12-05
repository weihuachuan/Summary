package numberAndString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class threeSum {
    //三数和
    public List<List<Integer>> threeSum(int[] nums) {
        //1.定义一个外部存储变量
        List<List<Integer>> resultList = new ArrayList<List<Integer>>();
        if(nums.length < 3){
            return resultList;
        }
        //2.将数据进行排序
        Arrays.sort(nums);
        //3. 通过i , j ,k来找到最合适的三个数
        for(int i =0;i<nums.length -2;i++){
            //4.判断i !=0 并且 i 和 i+1的值不相同
            if(i!=0)while(nums[i]==nums[i-1] && i < nums.length-2)i++;
            for(int j =i+1,k=nums.length -1;j<k;){
                //5.ijk 坐标数去重
                int He =nums[i] + nums[j] + nums[k];
                if(He == 0){
                    resultList.add(Arrays.asList(nums[i],nums[j],nums[k]));
                    while(j<k&& nums[j]==nums[j+1]) j++;
                    while(j<k&& nums[k]==nums[k-1]) k--;
                    j++;
                    k--;
                }else if (He < 0){
                    j=j+1;
                }else{
                    k=k-1;
                }
            }
        }
        return resultList;
    }
}

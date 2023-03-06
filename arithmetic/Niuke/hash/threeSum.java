package Niuke.hash;

import java.util.ArrayList;
import java.util.Arrays;
//三数和
public class threeSum {
    public ArrayList<ArrayList<Integer>> threeSum(int[] num) {
        ArrayList<ArrayList<Integer>> resultList = new ArrayList<ArrayList<Integer>>();
        if(num.length < 3){
            return resultList;
        }
        //2.将数据进行排序
        Arrays.sort(num);
        for(int i =0;i<num.length -2;i++){
            //4.判断i !=0 并且 i 和 i+1的值不相同
            if(i!=0)while(num[i]==num[i-1] && i < num.length-2)i++;
            for(int j =i+1,k=num.length -1;j<k;){
                //5.ijk 坐标数去重
                int He =num[i] + num[j] + num[k];
                if(He == 0){
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(num[i]);
                    temp.add(num[j]);
                    temp.add(num[k]);
                    resultList.add(temp);
                    //去重
                    while(j<k&& num[j]==num[j+1]) j++;
                    while(j<k&& num[k]==num[k-1]) k--;
                    //向中间收缩
                    j++;
                    k--;
                }else if(He < 0){
                    j++;
                }else{
                    k--;
                }
            }
        }
        return resultList;
    }
}

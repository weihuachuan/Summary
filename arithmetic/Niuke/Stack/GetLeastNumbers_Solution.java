package Niuke.Stack;

import java.util.ArrayList;
import java.util.PriorityQueue;

//获取数组中最小k个数
public class GetLeastNumbers_Solution {
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        //排除特殊情况
        if(k==0 || input.length ==0){
            return res;
        }
        //大堆跟
        PriorityQueue<Integer> q = new PriorityQueue<Integer>((o1, o2)->o2.compareTo(o1));
        //构建一个K个大小的堆
        for(int i=0;i<k;i++){
            q.offer(input[i]);
        }
        //将较小的元素入堆
        for(int i=k;i<input.length;i++){
            if(q.peek() > input[i]){
                q.poll();
                q.offer(input[i]);
            }
        }
        for(int i =0;i<k;i++){
            res.add(q.poll());
        }
        return res;
    }
}

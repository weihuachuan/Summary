package backtrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//全排列
public class permute {
    ArrayList<Integer> output = new ArrayList<Integer>();
    ArrayList<List<Integer>> res = new ArrayList<List<Integer>>();
    public List<List<Integer>> permute(int[] nums) {
        int n = nums.length;
        for(int num : nums){
            output.add(num);
        }
        dfs(n,res,output,0);
        return res;
    }

    public void dfs(int n,ArrayList<List<Integer>> res,ArrayList<Integer> output,int first){
        if(first == n){
            res.add(new ArrayList(output));
            return;
        }
        for(int i =first;i<n;i++){
            Collections.swap(output,first,i);
            dfs(n,res,output,first+1);
            Collections.swap(output,first,i);
        }
    }
}

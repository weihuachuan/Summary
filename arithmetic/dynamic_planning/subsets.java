package dynamic_planning;

import java.util.ArrayList;
import java.util.List;
//子集
public class subsets {
    ArrayList<Integer> t =new ArrayList<Integer>();
    ArrayList<List<Integer>> res = new ArrayList<List<Integer>>();
    public List<List<Integer>> subsets(int[] nums) {
        dfs(0,nums);
        return res;
    }
    public void dfs(int cur,int[] nums){
        if(cur == nums.length){
            res.add(new ArrayList(t));
            return;
        }
        t.add(nums[cur]);
        dfs(cur+1,nums);
        t.remove(t.size()-1);
        dfs(cur+1,nums);
    }
}

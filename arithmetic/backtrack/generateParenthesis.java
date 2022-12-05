package backtrack;

import java.util.ArrayList;
import java.util.List;
//括号生成
public class generateParenthesis {
    ArrayList<String> res = new ArrayList<String>();
    public List<String> generateParenthesis(int n) {
        dfs("",0,0,n);
        return res;
    }

    private void dfs(String paths,int left,int right,int n){
        if(left> n || right > left) return;
        if(paths.length() == 2*n){
            res.add(paths);
            return;
        }
        dfs(paths+"(",left+1,right,n);
        dfs(paths+")",left,right+1,n);
    }
}

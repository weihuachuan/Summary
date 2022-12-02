package sortSearch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
//二叉树的最近公共祖先
public class lowestCommonAncestor2 {
    //这里要设置两个数据结构来记录 父节点 和 路径
    Map<Integer,TreeNode> parent = new HashMap<Integer,TreeNode>();
    Set<Integer> visited = new HashSet<Integer>();
    public TreeNode lowestCommonAncestor(TreeNode  root, TreeNode p, TreeNode q) {
        dfs(root);
        while(p != null){
            visited.add(p.val);
            p=parent.get(p.val);
        }

        while(q != null){
            if(visited.contains(q.val)){
                return q;
            }
            q =parent.get(q.val);
        }

        return null;
    }

    private void dfs(TreeNode root){
        if(root.left != null){
            parent.put(root.left.val,root);
            dfs(root.left);
        }
        if(root.right !=null){
            parent.put(root.right.val,root);
            dfs(root.right);
        }
    }
}

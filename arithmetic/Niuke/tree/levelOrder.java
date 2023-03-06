package Niuke.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class levelOrder {
    /**
     *
     * @param root TreeNode类
     * @return int整型ArrayList<ArrayList<>>
     */
    public ArrayList<ArrayList<Integer>> levelOrder (TreeNode root) {
        // write code here
        // 定义结果集
        ArrayList<ArrayList<Integer>> res = new ArrayList();
        //如果为空直接返回空数组
        if(root == null) return res;
        //队列辅助进行层序遍历
        Queue<TreeNode> q =new ArrayDeque<TreeNode>();
        q.add(root);
        while(!q.isEmpty()){
            //记录二叉树的一行
            ArrayList<Integer> row = new ArrayList<Integer>();
            int n = q.size();
            //因为先进入的是根节点，故每层节点多少，队列大小为多少
            for(int i = 0; i<n;i++){
                TreeNode cur = q.poll();
                row.add(cur.val);
                //若左右孩子还在，则存入下一层
                if(cur.left !=null){
                    q.add(cur.left);
                }
                if(cur.right != null){
                    q.add(cur.right);
                }
            }
            res.add(row);
        }
        return res;
    }
}

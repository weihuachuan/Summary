package Niuke.tree;

import java.util.ArrayList;
import java.util.List;

public class postorderTraversal {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param root TreeNode类
     * @return int整型一维数组
     */
    public int[] postorderTraversal (TreeNode root) {
        // write code here
        //定义遍历数组
        List<Integer> list = new ArrayList();
        //后序递归遍历
        postorder(list, root);
        //返回结果集
        int [] res = new int[list.size()];
        for(int i=0;i<list.size();i++){
            res[i] = list.get(i);
        }
        return res;
    }
    public void postorder(List<Integer> list, TreeNode root){
        //1.遇空则返回
        if(root == null){
            return;
        }
        //先遍历左子树
        postorder(list,root.left);
        //遍历左子树
        postorder(list,root.right);
        list.add(root.val);
    }
}

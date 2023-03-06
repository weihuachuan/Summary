package Niuke.tree;

import java.util.ArrayList;
import java.util.List;

public class inorderTraversal {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param root TreeNode类
     * @return int整型一维数组
     */
    public int[] inorderTraversal (TreeNode root) {
        // write code here
        //添加遍历结果数组
        List<Integer> list = new ArrayList();
        //中序遍历递归
        inorder(list, root);
        //返回结果集
        int [] res = new int[list.size()];
        for(int i =0;i<list.size();i++){
            res[i] = list.get(i);
        }
        return res;
    }
    public void inorder(List<Integer> list,TreeNode root){
        //遇空则赶回
        if(root == null){
            return;
        }
        //先遍历左子树
        inorder(list, root.left);
        list.add(root.val);
        inorder(list, root.right);
    }
}

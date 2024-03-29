package Niuke.tree;

import java.util.ArrayList;
import java.util.List;

public class preorderTraversal {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param root TreeNode类
     * @return int整型一维数组
     */
    public int[] preorderTraversal (TreeNode root) {
        // write code here
        //添加遍历结果数组
        List<Integer> list = new ArrayList();
        //递归前序遍历
        preorder(list,root);
        //返回结果
        int [] res = new int[list.size()];
        for(int i = 0;i< list.size(); i++){
            res[i] = list.get(i);
        }
        return res;
    }
    public void preorder(List<Integer> list, TreeNode root){
        //遇到空节点则返回
        if(root == null){
            return;
        }
        //先遍历根节点
        list.add(root.val);
        //再去左子树
        preorder(list,root.left);
        //再去右子树
        preorder(list,root.right);
    }
}

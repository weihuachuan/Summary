package Niuke.tree;

import java.util.Arrays;
//根据前序遍历和中序遍历的结果还原二叉树
public class reConstructBinaryTree {
    public TreeNode reConstructBinaryTree(int [] pre,int [] vin) {
        //根据前序遍历和中序遍历的结果还原二叉树
        int n =pre.length;
        int m = vin.length;
        //每个遍历都不能为0
        if(n == 0 || m==0){
            return null;
        }
        //构建根节点
        TreeNode root = new TreeNode(pre[0]);
        for(int i = 0; i < vin.length;i++ ){
            //找到中序遍历中前序遍历的第一个元素
            if(pre[0] == vin[i]){
                //构建左子树
                root.left = reConstructBinaryTree(Arrays.copyOfRange(pre,1,i+1),Arrays.copyOfRange(vin,0,i));
                //构建右子树
                root.right = reConstructBinaryTree(Arrays.copyOfRange(pre,i+1,pre.length),Arrays.copyOfRange(vin,i+1,vin.length));
                break;
            }
        }
        return root;
    }
}

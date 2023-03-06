package Niuke.tree;
// 对称二叉树
public class isSymmetrical {
    boolean isSymmetrical(TreeNode pRoot) {
        //使用递归遍历
        return recursion(pRoot,pRoot);
    }
    boolean recursion(TreeNode root1, TreeNode root2){
        //可以两个为空
        if(root1 == null && root2 == null){
            return true;
        }
        //只有一个节点为空 或者节点值不一样，必定部队称
        if(root1 == null || root2 == null || root1.val != root2.val){
            return false;
        }
        return recursion(root1.left,root2.right) && recursion(root1.right,root2.left);
    }
}

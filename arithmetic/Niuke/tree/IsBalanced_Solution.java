package Niuke.tree;
//判断是否为平衡二叉树
public class IsBalanced_Solution {
    public boolean IsBalanced_Solution(TreeNode root) {
        //空树为平衡二叉树 左右子树的深度不能相差1
        if(root == null){
            return true;
        }
        //递归求二叉左右树的深度
        int left = deepth(root.left);
        int right = deepth(root.right);
        if(left - right > 1 || left -right < -1){
            return false;
        }
        //同时左右子树也是平衡二叉树
        return IsBalanced_Solution(root.left) && IsBalanced_Solution(root.right);
    }
    public int deepth(TreeNode root){
        if(root == null){
            return 0;
        }
        int left = deepth(root.left);
        int right = deepth(root.right);
        //子树最大深度大于自己
        return (left > right)? left+1:right+1;
    }
}

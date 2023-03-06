package Niuke.tree;
//合并二叉树
public class mergeTrees {
    /**
     *
     * @param t1 TreeNode类
     * @param t2 TreeNode类
     * @return TreeNode类
     */
    public TreeNode mergeTrees (TreeNode t1, TreeNode t2) {
        // write code here
        //若只有一个节点返回另一个，两个都为null自然返回null
        if(t1 == null){
            return t2;
        }
        if(t2 == null){
            return t1;
        }
        //根左右方式递归
        TreeNode head = new TreeNode(t1.val + t2.val);
        head.left = mergeTrees(t1.left,t2.left);
        head.right = mergeTrees(t1.right,t2.right);
        return head;
    }
}

package Niuke.tree;
//二叉树的最近公共先祖

public class lowestCommonAncestor2 {
    /**
     *
     * @param root TreeNode类
     * @param o1 int整型
     * @param o2 int整型
     * @return int整型
     */
    public int lowestCommonAncestor (TreeNode root, int o1, int o2) {
        // write code here
        return helper(root,o1,o2).val;
    }
    public TreeNode helper(TreeNode root, int o1,int o2){
        if(root == null || root.val == o1 || root.val == o2){
            return root;
        }
        //前序遍历
        TreeNode left = helper(root.left,o1,o2);
        TreeNode right = helper(root.right,o1,o2);
        if(left == null) return right;
        if(right == null) return left;
        // 如果left 和 right 都不为空说明都在root的两侧
        //直接返回
        return root;
    }
}

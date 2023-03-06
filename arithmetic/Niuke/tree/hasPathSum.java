package Niuke.tree;

//是否有深度和 为sum
public class hasPathSum {
    /**
     *
     * @param root TreeNode类
     * @param sum int整型
     * @return bool布尔型
     */
    public boolean hasPathSum (TreeNode root, int sum) {
        // write code here
        //空节点找不到路径
        if(root == null) return false;
        //叶子节点，且路径和为 sum
        if(root.left == null && root.right == null && sum - root.val == 0){
            return true;
        }
        //递归进入子节点
        return hasPathSum(root.left,sum - root.val) || hasPathSum(root.right,sum - root.val);
    }
}

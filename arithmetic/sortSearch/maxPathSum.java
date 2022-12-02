package sortSearch;
// v二叉树中的最大路径和
public class maxPathSum {
    int maxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }
    public int maxGain(TreeNode root){
        if(root==null){
            return 0;
        }
        int left = Math.max(maxGain(root.left),0);
        int right = Math.max(maxGain(root.right),0);
        int pre = root.val + left + right;
        maxSum = Math.max(pre,maxSum);
        return root.val + Math.max(left,right);
    }
}

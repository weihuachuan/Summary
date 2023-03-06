package Niuke.tree;

public class maxDepth {
    /**
     *
     * @param root TreeNode类
     * @return int整型
     */
    public int maxDepth (TreeNode root) {
        // write code here
        //空节点没有深度
        if(root ==null){
            return 0;
        }
        //返回子树深度
        return Math.max(maxDepth(root.left),maxDepth(root.right)) +1;
    }
}

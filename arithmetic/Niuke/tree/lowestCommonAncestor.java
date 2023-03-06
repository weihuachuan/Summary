package Niuke.tree;
//最近公共先祖
public class lowestCommonAncestor {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param root TreeNode类
     * @param p int整型
     * @param q int整型
     * @return int整型
     */
    public int lowestCommonAncestor (TreeNode root, int p, int q) {
        // write code here
        TreeNode ancestor = root;
        while(true){
            if(p < ancestor.val && q < ancestor.val){
                ancestor = ancestor.left;
            }else if(p > ancestor.val && q > ancestor.val){
                ancestor = ancestor.right;
            }else{
                break;
            }
        }
        return ancestor.val;
    }
}

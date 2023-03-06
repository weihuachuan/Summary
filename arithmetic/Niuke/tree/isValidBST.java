package Niuke.tree;
//是否是二叉搜索树
public class isValidBST {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param root TreeNode类
     * @return bool布尔型
     */
    int pre = Integer.MIN_VALUE;
    public boolean isValidBST (TreeNode root) {
        // write code here
        // 空值返回true
        if(root == null){
            return true;
        }
        //使用中序遍历 root.val < pre  false
        //先遍历左子树
        if(!isValidBST(root.left)){
            return false;
        }
        if(root.val < pre){
            return false;
        }
        //更新最值
        pre = root.val;
        //再进入右子树
        return isValidBST(root.right);
    }
}

package Niuke.tree;

//二叉搜索树与双向链表
public class Convert {
    //返回的第一个指针，即为最小值，限定为null
    public TreeNode head = null;
    //中序遍历当前的值得上一位，初始为最小值，先定为null
    public TreeNode pre = null;
    public TreeNode Convert(TreeNode pRootOfTree) {
        if(pRootOfTree == null){
            //中序递归，叶子节点为空则返回
            return null;
        }
        //首先递归最左最小值
        Convert(pRootOfTree.left);
        if(pre == null){
            head = pRootOfTree;
            pre = pRootOfTree;
        }else{
            //当前节点与上一节点建立连接，将pre设置为当前值
            pre.right = pRootOfTree;
            pRootOfTree.left = pre;
            pre = pRootOfTree;
        }
        Convert(pRootOfTree.right);
        return head;
    }
}

package sortSearch;

import java.util.ArrayList;

// 二叉搜索树中第K小的元素
public class kthSmallest {
    public int kthSmallest(TreeNode root, int k) {
        //二叉搜索树的第k小的数
        //定义一个列表将数顺序插入列表中 再取出来第K小的数
        ArrayList<Integer> arr = new ArrayList<Integer>();
        ArrayList<Integer> nums =inoder(root,arr);
        return nums.get(k-1);
    }

    public ArrayList<Integer> inoder(TreeNode root,ArrayList<Integer> arr ){
        if(root==null){
            return arr;
        }
        inoder(root.left,arr);
        arr.add(root.val);
        inoder(root.right,arr);
        return arr;
    }
}

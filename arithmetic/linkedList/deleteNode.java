package linkedList;

public class deleteNode {
    public void deleteNode(ListNode node) {
        //此方法是直接删除该节点 不能引用其他节点变量
        //将当前节点值改变 取消下一个节点的引用
        node.val = node.next.val;
        node.next =node.next.next;
    }
}

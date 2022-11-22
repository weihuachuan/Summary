package linkedList;

public class getIntersectionNode {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        //找两个链表的交点 使用双指针分别从两个链表头开始遍历，遍历完指针从另一个链表开始遍历
        //最后将在交叉点相遇
        if(headA==null || headB==null){
            return null;
        }
        ListNode head1 =headA;
        ListNode head2 =headB;
        while(head1 !=head2){
            if(head1!=null){
                head1 =head1.next;
            }else{
                head1 =headB;
            }

            if(head2 != null){
                head2 =head2.next;
            }else{
                head2=headA;
            }
        }
        return head1;
    }
}

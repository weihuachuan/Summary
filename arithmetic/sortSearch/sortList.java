package sortSearch;

// 排序链表
public class sortList {
    public ListNode sortList(ListNode head) {
        //要想把链表排序就需要把元素两两遍历一遍并且时间复杂度为nlog(n) 想到了二分法
        return sortList(head,null);
    }
    public ListNode sortList(ListNode head,ListNode tail){
        if(head==null){
            return null;
        }
        if(head.next==tail){
            head.next=null;
            return head;
        }
        //求链表的中点 开慢指针法
        ListNode fast =head;
        ListNode slow =head;
        while(fast !=tail){
            slow =slow.next;
            fast =fast.next;
            if(fast !=tail){
                fast=fast.next;
            }
        }
        ListNode mid =slow;
        ListNode head1 = sortList(head,mid);
        ListNode head2 = sortList(mid,tail);
        return merge(head1,head2);
    }

    public ListNode merge(ListNode head1, ListNode head2){
        ListNode head =new ListNode(0);
        ListNode temp = head;
        while(head1 !=null && head2 != null){
            if(head1.val <= head2.val){
                temp.next=head1;
                head1=head1.next;
            }else{
                temp.next=head2;
                head2=head2.next;
            }
            temp=temp.next;
        }
        temp.next = (head1!=null)?head1:head2;
        return head.next;

    }
}

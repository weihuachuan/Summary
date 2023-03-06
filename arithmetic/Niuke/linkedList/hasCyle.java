package Niuke.linkedList;

import linkedList.ListNode;

//判断环形链表
public class hasCyle {
    public ListNode hasCyle(ListNode head){
        if(head == null) return null;
        ListNode fast = head;
        ListNode slow = head;
        while(fast !=null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow){
                return slow;
            }
        }
        return null;
    }
}

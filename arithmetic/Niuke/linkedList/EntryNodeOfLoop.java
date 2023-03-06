package Niuke.linkedList;

import linkedList.ListNode;

// 找到入环节点
public class EntryNodeOfLoop {
    public ListNode EntryNodeOfLoop(ListNode pHead) {
        //1.判断是否有环
        ListNode slow = hasCyle(pHead);
        if(slow == null){
            return null;
        }
        ListNode fast = pHead;
        while(fast != slow){
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
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

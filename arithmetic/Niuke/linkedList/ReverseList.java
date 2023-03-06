package Niuke.linkedList;

import linkedList.ListNode;

public class ReverseList {
    // 反转链表
    public ListNode ReverseList(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        ListNode temp;
        while(cur != null){
            temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }

}

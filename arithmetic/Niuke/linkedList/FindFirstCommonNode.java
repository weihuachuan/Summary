package Niuke.linkedList;

import linkedList.ListNode;
//找到链表香蕉的点
public class FindFirstCommonNode {
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        //以相同速度向前走一定会相遇
        ListNode l1 = pHead1;
        ListNode l2 = pHead2;
        while(l1 != l2){
            l1 = (l1 == null)?pHead2:l1.next;
            l2 = (l2 == null)?pHead1:l2.next;
        }
        return l1;
    }
}

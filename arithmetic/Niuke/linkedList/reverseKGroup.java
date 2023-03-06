package Niuke.linkedList;

import linkedList.ListNode;

//指定K组 反转链表 递归
public class reverseKGroup {
    public ListNode reverseKGroup (ListNode head, int k) {
        // write code here
        ListNode tail = head;
        for(int i =0;i < k; i++){
            if(tail == null){
                return head;
            }
            tail = tail.next;
        }
        ListNode pre = null;
        ListNode cur = head;
        while(cur != tail){
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        head.next = reverseKGroup(tail,k);
        return pre;
    }
}

package Niuke.linkedList;

import linkedList.ListNode;

// 指定反转范围 链表
public class reverseBetween {
    public ListNode reverseBetween (ListNode head, int m, int n) {
        // write code here
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        ListNode pre = dummyNode;

        for(int i=0;i<m-1;i++){
            pre = pre.next;
        }

        ListNode rightNode = pre;
        for(int i=0;i<n-m+1;i++){
            rightNode = rightNode.next;
        }

        ListNode leftNode = pre.next;
        ListNode cur = rightNode.next;

        pre.next = null;
        rightNode.next =null;

        reverseLinkedList(leftNode);

        pre.next = rightNode;
        leftNode.next = cur;

        return dummyNode.next;
    }
    public void reverseLinkedList(ListNode head){
        ListNode pre = null;
        ListNode cur = head;
        while(cur != null){
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
    }
}

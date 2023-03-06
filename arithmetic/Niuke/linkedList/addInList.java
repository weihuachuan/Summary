package Niuke.linkedList;

import linkedList.ListNode;

//两个链表相加
public class addInList {
    //反转链表
    public ListNode reverseList(ListNode head){
        ListNode cur = head;
        ListNode pre = null;
        while(cur != null){
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }
    public ListNode addInList (ListNode head1, ListNode head2) {
        // write code here
        //判断head1 和 head2 有一个为空返回另一个
        if(head1 ==null){
            return head2;
        }
        if(head2 == null){
            return head1;
        }
        head1 = reverseList(head1);
        head2 = reverseList(head2);
        //定义表头
        ListNode res = new ListNode(-1);
        ListNode head = res;
        int carry = 0;
        //只要某个链表还有或者进位还有
        while(head1 !=null || head2 !=null || carry != 0){
            int val1 = (head1 ==null)?0:head1.val;
            int val2 = (head2 == null)?0:head2.val;
            int temp = val1 + val2 + carry;
            carry = temp/10;
            temp = temp%10;
            head.next = new ListNode(temp);
            head = head.next;
            if(head1 != null) head1 = head1.next;
            if(head2 != null) head2 = head2.next;
        }
        return reverseList(res.next);
    }
}

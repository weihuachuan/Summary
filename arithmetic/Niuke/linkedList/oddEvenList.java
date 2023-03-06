package Niuke.linkedList;

import linkedList.ListNode;
//奇偶重排
public class oddEvenList {
    public ListNode oddEvenList (ListNode head) {
        // write code here
        // 判断节点为空不用重拍
        if(head ==null)  return null;
        ListNode even = head.next;
        ListNode odd = head;
        ListNode evenHead = even;
        //指向even开头
        while(even!=null && even.next !=null){
            odd.next = even.next;
            odd = odd.next;

            even.next = odd.next;
            even =even.next;
        }
        odd.next =evenHead;
        return head;
    }
}

package Niuke.linkedList;

import linkedList.ListNode;

public class deleteDuplicates2 {
    public ListNode deleteDuplicates (ListNode head) {
        // write code here
        //判空
        if(head ==null) return null;
        ListNode res = new ListNode(0);
        res.next = head;
        ListNode cur = res;
        while(cur.next != null && cur.next.next != null){
            if(cur.next.val == cur.next.next.val){
                int temp = cur.next.val;
                while(cur.next!=null && cur.next.val ==temp){
                    cur.next = cur.next.next;
                }
            }else{
                cur = cur.next;
            }
        }
        return res.next;
    }
}

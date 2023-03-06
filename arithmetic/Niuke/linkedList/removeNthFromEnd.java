package Niuke.linkedList;

import linkedList.ListNode;

// 删除倒数第N个节点
public class removeNthFromEnd {
    public ListNode removeNthFromEnd (ListNode head, int n) {
        // write code here
        ListNode res = new ListNode(-1);
        res.next = head;
        ListNode pre = res;
        ListNode fast = head;
        ListNode slow = head;
        for(int i=0;i<n;i++){
            if(fast !=null){
                fast = fast.next;
            }else{
                return null;
            }
        }
        while(fast != null){
            fast = fast.next;
            pre = slow;
            slow = slow.next;
        }
        // 删除节点
        pre.next = slow.next;
        return res.next;
    }
}

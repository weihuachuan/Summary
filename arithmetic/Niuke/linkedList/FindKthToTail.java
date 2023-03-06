package Niuke.linkedList;

import linkedList.ListNode;
//查找链表第k个节点
public class FindKthToTail {
    public ListNode FindKthToTail (ListNode pHead, int k) {
        // write code here
        ListNode fast = pHead;
        ListNode slow = pHead;
        for(int i =0; i<k;i++){
            if(fast !=null){
                fast = fast.next;
            }else{
                return slow = null;
            }
        }
        while(fast !=null){
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}

package Niuke.linkedList;

import linkedList.ListNode;

//删除链表中重复的数
public class deleteDuplicates {
    public ListNode deleteDuplicates (ListNode head) {
        // write code here
        //空链表返回hull
        if(head ==null) return null;
        //定义一个当前节点
        ListNode cur = head;
        while(cur !=null && cur.next != null){
            if(cur.val == cur.next.val){
                cur.next = cur.next.next;
            }else{
                cur = cur.next;
            }
        }
        return head;
    }
}

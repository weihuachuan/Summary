package Niuke.linkedList;

import linkedList.ListNode;

//合并两个有序链表
public class Merge {
    public ListNode Merge(ListNode list1,ListNode list2) {
        //1.定义一个头节点
        ListNode head = new ListNode(0);
        //2.定义一个临时链表节点用于对链表合并
        ListNode tempNode = head;
        while(list1!=null && list2!=null){
            if(list1.val < list2.val){
                tempNode.next = list1;
                list1 = list1.next;
            }else{
                tempNode.next = list2;
                list2 = list2.next;
            }
            tempNode = tempNode.next;
        }
        tempNode.next = list1 ==null? list2 : list1;
        return head.next;
    }
}

package Niuke.linkedList;

import linkedList.ListNode;

// 单链表遍历
public class sortInList {
    public ListNode sortInList (ListNode head) {
        // write code here
        // 链表为空 或者只有一个节点直接有序
        if(head == null || head.next ==null){
            return head;
        }
        //定义三个指针 找到中间节点 切分
        ListNode left = head;
        ListNode mid = head.next;
        ListNode right = head.next.next;
        while(right!=null && right.next!=null){
            left = left.next;
            mid = mid.next;
            right = right.next.next;
        }
        left.next = null;
        return merge(sortInList(head),sortInList(mid));
    }
    public ListNode merge(ListNode list1,ListNode list2){
        // 如果一个已经空了返回另外一个
        if(list1 == null){
            return list2;
        }
        if(list2 == null){
            return list1;
        }
        //1.定义一个头节点记录头部位置
        ListNode head = new ListNode(0);
        //2.定义一个临时节点用作合并两个有序的list
        ListNode temp = head;
        while(list1 != null && list2 != null){
            if(list1.val <= list2.val){
                temp.next = list1;
                list1 = list1.next;
            }else{
                temp.next = list2;
                list2 = list2.next;
            }
            temp = temp.next;
        }
        temp.next = list1==null?list2:list1;
        return head.next;
    }
}

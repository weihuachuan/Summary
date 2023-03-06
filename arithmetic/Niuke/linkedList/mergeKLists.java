package Niuke.linkedList;

import linkedList.ListNode;

import java.util.ArrayList;

public class mergeKLists {
    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        return mergeKLists(lists,0,lists.size()-1);
    }
    public ListNode mergeKLists(ArrayList<ListNode> lists, int start, int end){
        if(start > end){
            return null;
        }else if(start == end){
            return lists.get(start);
        }
        int mid = (start + end) / 2;
        return merge(mergeKLists(lists,start,mid),mergeKLists(lists,mid+1,end));
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

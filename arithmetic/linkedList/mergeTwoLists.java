package linkedList;

public class mergeTwoLists {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        //1.定义一个头结点
        ListNode head = new ListNode(0);
        //2.定义一个临时变量指向头部节点
        ListNode temp = head;
        while(list1!=null && list2!=null){
            //3.在临时节点后按顺序挂值
            if(list1.val < list2.val){
                temp.next = list1;
                list1 = list1.next;
            }else{
                temp.next = list2;
                list2 = list2.next;
            }
            //4.临时节点后移
            temp = temp.next;
        }
        temp.next = list1==null?list2:list1;
        return head.next;
    }
}

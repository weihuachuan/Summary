package linkedList;

public class rotateRight {
    public ListNode rotateRight(ListNode head, int k) {
        //1.如果为空则返回head
        if(head==null){
            return head;
        }
        //2.定义快慢指针
        ListNode fast = head;
        ListNode slow = head;
        //3.计算一下列表的长度
        int len = caculateLength(head);
        k=k%len;
        //4.快指针先走K步
        for(int i=0;i<k;i++){
            fast =fast.next;
        }
        //5.快慢指针同时走
        while(fast.next!=null){
            fast = fast.next;
            slow = slow.next;
        }
        fast.next = head;
        head = slow.next;
        slow.next = null;
        return head;
    }

    public int caculateLength(ListNode head){
        int len = 0;
        while(head != null){
            head=head.next;
            len++;
        }
        return len;
    }
}

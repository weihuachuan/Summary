package linkedList;

public class hasCycle {
    public boolean hasCycle(ListNode head) {
        if(head ==null){
            return false;
        }
        //使用快慢指针
        ListNode fast =head;
        ListNode slow =head;
        while(fast != null){
            slow = slow.next;
            fast = fast.next;
            if(fast != null){
                fast = fast.next;
            }else{
                return false;
            }
            if(fast == slow){
                return true;
            }
        }
        return false;
    }
}

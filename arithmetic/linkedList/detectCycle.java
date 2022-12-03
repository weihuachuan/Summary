package linkedList;

public class detectCycle {
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast !=null ){
            slow =slow.next;
            fast =fast.next;
            if(fast !=null){
                fast =fast.next;
            }else{
                return null;
            }
            if(fast == slow){
                ListNode pos = head;
                while(pos != slow){
                    pos =pos.next;
                    slow =slow.next;
                }
                return pos;
            }
        }
        return null;
    }
}

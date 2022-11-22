package linkedList;

public class addTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //1.首先我要有个结果列表
        ListNode head = new ListNode(0);
        ListNode cur = head;
        int carry = 0;
        //2.通过传进来的参数做循环判断
        while(l1 != null || l2 != null){
            //3.因为逆序从右到左计算取出x,y
            int x = l1!=null? l1.val:0;
            int y = l2!=null? l2.val:0;
            //4.计算进位
            int sum = x+y+carry;
            carry = sum/10;
            //5.计算相加
            cur.next = new ListNode(sum%10);
            //6.
            cur = cur.next;
            if(l1!=null) l1=l1.next;
            if(l2!=null) l2=l2.next;
        }
        //7.看看是否还有进位
        if(carry > 0){
            cur.next  = new ListNode(carry);
        }
        return head.next;
    }
}

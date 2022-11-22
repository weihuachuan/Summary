package linkedList;

public class mergeKLists {
    public ListNode mergeKLists(ListNode[] lists) {
        return mergeKLists(lists,0,lists.length - 1);
    }
    public ListNode mergeKLists(ListNode[] lists,int start,int end) {
        if(start == end){
            return lists[start];
        }
        if(start > end){
            return null;
        }
        int mid = (start + end)/2;
        ListNode l = mergeKLists(lists,start,mid);
        ListNode r = mergeKLists(lists,mid+1,end);
        return getmergeKLists(l,r);
    }
    public ListNode getmergeKLists(ListNode l, ListNode r) {
        //定义头部节点
        ListNode head = new ListNode(0);
        //定义临时节点
        ListNode temp = head;
        while(l!=null && r!=null){
            if(l.val < r.val){
                temp.next = l;
                l = l.next;
            }else{
                temp.next = r;
                r = r.next;
            }
            temp = temp.next;
        }
        temp.next = l==null?r:l;
        return head.next;
    }
}

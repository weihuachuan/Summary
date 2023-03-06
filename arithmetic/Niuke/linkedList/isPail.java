package Niuke.linkedList;

import linkedList.ListNode;

import java.util.ArrayList;
import java.util.Collections;

//判断一个链表是否是回文结构
public class isPail {
    public boolean isPail (ListNode head) {
        // write code here
        // 正向便利和反向便利不相同
        ArrayList<Integer> nums = new ArrayList();
        while(head != null){
            nums.add(head.val);
            head = head.next;
        }
        ArrayList<Integer> temp = new ArrayList();
        temp = (ArrayList<Integer>) nums.clone();
        Collections.reverse(temp);
        for(int i=0;i<nums.size();i++){
            int x = nums.get(i);
            int y = temp.get(i);
            //正向遍历与反向便利相同
            if(x != y){
                return false;
            }
        }
        return true;
    }
}

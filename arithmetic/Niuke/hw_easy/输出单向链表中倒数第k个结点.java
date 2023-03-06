package Niuke.hw_easy;

import java.util.Scanner;

/*
* 描述
输入一个单向链表，输出该链表中倒数第k个结点，链表的倒数第1个结点为链表的尾指针。

链表结点定义如下：
struct ListNode
{
    int m_nKey;
    ListNode* m_pNext;
};
正常返回倒数第k个结点指针，异常返回空指针.
要求：
(1)正序构建链表;
(2)构建后要忘记链表长度。


本题有多组样例输入。


输入描述：
输入说明
1 输入链表结点个数
2 输入链表的值
3 输入k的值

输出描述：
输出一个整数

示例1
输入：
8
1 2 3 4 5 6 7 8
4
复制
输出：
5*/
public class 输出单向链表中倒数第k个结点 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            //获取链表长度
            int n = Integer.parseInt(sc.next());
            //生成链表
            ListNode head = new ListNode(-1);
            ListNode temp = head;
            for(int i=0;i<n;i++){
                ListNode node = new ListNode(sc.nextInt());
                temp.next = node;
                temp = temp.next;
            }
            int k = Integer.parseInt(sc.next());
            //使用快慢指针
            if(getKthNode(head,k) != null){
                System.out.println(getKthNode(head,k).val);
            }else{
                System.out.println(0);
            }
        }
    }

    static ListNode getKthNode(ListNode head, int k){
        ListNode fast = head;
        ListNode slow = head;
        for(int i=0;i<k;i++){
            if(fast == null){
                return null;
            }
            fast = fast.next;
        }
        while(fast !=null){
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}
class ListNode{
    ListNode next;
    int val;
    ListNode(int val){
        this.val = val;
        next = null;
    }
}
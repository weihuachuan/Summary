package Niuke.acm.acm1;

import java.util.*;

/*
* 给定参数n,从1到n会有n个整数:1,2,3,...,n,
    这n个数字共有n!种排列.
  按大小顺序升序列出所有排列的情况,并一一标记,
  当n=3时,所有排列如下:
  "123" "132" "213" "231" "312" "321"
  给定n和k,返回第k个排列.

  输入描述:
    输入两行，第一行为n，第二行为k，
    给定n的范围是[1,9],给定k的范围是[1,n!]。
  输出描述：
    输出排在第k位置的数字。

  实例1：
    输入:
      3
      3
    输出：
      213
    说明
      3的排列有123,132,213...,那么第三位置就是213

  实例2：
    输入
      2
      2
    输出：
      21
    说明
      2的排列有12,21，那么第二位置的为21
*/
public class 整数排列 {
    // 整数排列   这道题目前还不懂，稍后总结
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            int n = sc.nextInt();
            int index = sc.nextInt();
            // 未进站的火车
            Queue<Integer> queue = new LinkedList<>();
            // 已进站的火车
            Stack<Integer> stack = new Stack<>();

            for (int i = 1; i <= n; i++) {
                queue.offer(i);
            }

            List<String> outQueueList = new ArrayList<>();

            // 获取所有出站队列保存到outQueueList
            processOutQueue(queue, stack, "", outQueueList);

            // 排序
            Collections.sort(outQueueList);
            System.out.println(outQueueList.get(index -1));
        }
    }

    private static void processOutQueue(Queue<Integer> queue, Stack<Integer> stack, String outQueue, List<String> outQueueList) {
        // 如果队列和栈都为空，则保存出站信息
        if (queue.isEmpty() && stack.isEmpty()) {
            outQueueList.add(outQueue.trim());
            return;
        }

        // 队列和栈有两种情况：出栈或进栈
        // 一：出栈
        if (!stack.isEmpty()) {
            // 先克隆
            Queue<Integer> tempQueue = new LinkedList<>(queue);
            Stack<Integer> tempStack = (Stack<Integer>) stack.clone();
            String tempOutQueue = outQueue + (tempStack.pop() + " ");
            processOutQueue(tempQueue, tempStack, tempOutQueue, outQueueList);
        }

        // 二：队列进栈
        if (!queue.isEmpty()) {
            // 先克隆
            Queue<Integer> tempQueue = new LinkedList<>(queue);
            Stack<Integer> tempStack = (Stack<Integer>) stack.clone();
            tempStack.push(tempQueue.poll());
            processOutQueue(tempQueue, tempStack, outQueue, outQueueList);
        }
    }

}

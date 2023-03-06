package Niuke.Stack;

import java.util.Stack;

public class minStack {
    //用于栈的push 与 pop
    Stack<Integer> s1 = new Stack<Integer>();
    //用于存储最小min
    Stack<Integer> s2 = new Stack<Integer>();
    public void push(int node) {
        s1.push(node);
        //空或者新元素较小，则入栈
        if(s2.isEmpty() || s2.peek() > node)
            s2.push(node);
        else
            //重复加入栈顶
            s2.push(s2.peek());
    }

    public void pop() {
        s1.pop();
        s2.pop();
    }

    public int top() {
        return s1.peek();
    }

    public int min() {
        return s2.peek();
    }
}

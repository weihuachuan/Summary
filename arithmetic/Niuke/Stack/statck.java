package Niuke.Stack;

import java.util.Stack;

public class statck {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        //将第一个栈中的能内容淡出放入第二个栈
        while(!stack1.isEmpty()){
            stack2.push(stack1.pop());
        }
        //第二栈栈顶就是最先进来的元素
        int res = stack2.pop();
        //再将第二个栈的元素放入第一个站
        while(!stack2.isEmpty()){
            stack1.push(stack2.pop());
        }
        return res;

    }
}

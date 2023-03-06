package Niuke.Stack;

import java.util.Stack;

//检验括号的有效性
public class isValid {
    /**
     *
     * @param s string字符串
     * @return bool布尔型
     */
    public boolean isValid (String s) {
        // write code here
        //辅助栈
        Stack<Character> st = new Stack<Character>();
        for(int i =0;i<s.length();i++){
            //遇到左小括号
            if(s.charAt(i) == '('){
                //期待遇到右小括号
                st.push(')');
                //遇到左中括号
            }else if(s.charAt(i) == '['){
                //期待遇到右中括号
                st.push(']');
                //遇到左大括号
            }else if(s.charAt(i) == '{'){
                //期待遇到右大括号
                st.push('}');
            }else if(st.isEmpty() || st.pop() !=s.charAt(i)){
                //必须有左括号的情况才能遇到右括号
                return false;
            }
        }
        return st.isEmpty();
    }
}

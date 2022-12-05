package numberAndString;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class isValid {
    public boolean isValid(String s) {
        //有效括号通过站和map 解决问题
        //1.创建一个map 和 站
        Map<Character,Character> map=new HashMap<Character,Character>();
        map.put(')','(');
        map.put('}','{');
        map.put(']','[');
        Deque<Character> stack = new LinkedList<Character>();
        //2.遍历字符判断是否符合规则
        for(int i=0;i<s.length();i++){
            Character ch = s.charAt(i);
            if(map.containsKey(ch)){
                if(stack.isEmpty() || stack.peek() != map.get(ch)){
                    return false;
                }
                stack.pop();
            }else{
                stack.push(s.charAt(i));
            }
        }
        return stack.isEmpty();
    }
}

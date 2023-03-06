package Niuke.string;
// 大数加法
public class StringNumAdd {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * 计算两个数之和
     * @param s string字符串 表示第一个整数
     * @param t string字符串 表示第二个整数
     * @return string字符串
     */
    public String solve (String s, String t) {
        // write code here
        //若其中一个为空返回上一个
        if(s.length() <=0) return t;
        if(t.length() <=0)return s;
        //让s为教长的，t为较短的
        if(s.length() < t.length()){
            String temp =s;
            s= t;
            t = temp;
        }
        int carry = 0;
        char[] res = new char[s.length()];
        //从后往前遍历交长的字符串
        for(int i =s.length()-1;i>=0;i--){
            // 转数字加上进位
            int temp = s.charAt(i) -'0' + carry;
            int j = i - s.length() + t.length();
            //如果较短的字符串还有
            if(j >=0){
                //转数相加
                temp += t.charAt(j) - '0';
            }
            //取进位
            carry = temp / 10;
            //取10位
            temp = temp % 10;
            //修改结果
            res[i]  = (char)(temp + '0');
        }
        String output = String.valueOf(res);
        //最后的进位
        if(carry == 1)
            output = '1' + output;
        return output;
    }
}

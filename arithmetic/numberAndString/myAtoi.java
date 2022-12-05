package numberAndString;

public class myAtoi {
    //字符串转整数
    public int myAtoi(String s) {
        //1.定义一些变量
        int m = s.length();
        int i=0,b=0,sigin=1;
        //2.循环去空
        while(i<m && s.charAt(i)==' '){
            i++;
        }
        //3.判断是否到末尾
        if(i==m){
            return 0;
        }
        //4.判断符号位
        if(s.charAt(i)=='+' || s.charAt(i)=='-'){
            sigin = s.charAt(i++)=='+'? 1:-1;
        }
        //5.转换逻辑
        while(i<m && s.charAt(i) >= '0' && s.charAt(i) <= '9'){
            //6.判断界限超出返回Integer.Max Min
            if(b > Integer.MAX_VALUE/10 ||(b==Integer.MAX_VALUE/10 && (s.charAt(i) - '0' > 7))){
                return sigin==1? Integer.MAX_VALUE:Integer.MIN_VALUE;
            }
            b = b * 10 + s.charAt(i++) - '0';
        }
        return b*sigin;
    }
}

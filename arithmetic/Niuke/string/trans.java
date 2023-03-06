package Niuke.string;
// 单词大小写转换 单词倒转
public class trans {
    public String trans(String s, int n) {
        // write code here
        if(n==0)return s;
        //定以一个结果集 stringbuffer reverse（）
        StringBuffer res = new StringBuffer();
        //大小写转换存入结果集
        for(int i=0;i<n;i++){
            if(s.charAt(i) >='A' && s.charAt(i) <= 'Z'){
                res.append((char)(s.charAt(i)-'A' + 'a'));
            }else if(s.charAt(i) >='a' && s.charAt(i) <= 'z'){
                res.append((char)(s.charAt(i)-'a' + 'A'));
            }else{
                res.append(s.charAt(i));
            }
        }
        //反转整个字符串
        res = res.reverse();
        for(int i = 0;i <n;i++){
            int j = i;
            while(j < n && res.charAt(j) != ' '){
                j++;
            }
            String temp = res.substring(i,j);
            StringBuffer buffer = new StringBuffer(temp);
            temp = buffer.reverse().toString();
            res.replace(i,j,temp);
            i=j;
        }
        return res.toString();
    }
}

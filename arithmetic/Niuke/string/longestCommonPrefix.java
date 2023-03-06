package Niuke.string;

//最长公共前缀
public class longestCommonPrefix {
    public String longestCommonPrefix (String[] strs) {
        // write code here
        //横向扫描
        if(strs.length==0 || strs==null){
            return "";
        }
        //从第一个字符开始
        String prefix =strs[0];
        for(int i=1;i<strs.length;i++){
            prefix = GetCommentPrefix(prefix,strs[i]);
            if(prefix.length() == 0){
                return "";
            }
        }
        return prefix;
    }
    public String GetCommentPrefix(String str1,String str2){
        int len = Math.min(str1.length(),str2.length());
        int flag = 0;
        while(flag < len && str1.charAt(flag)==str2.charAt(flag)){
            flag++;
        }
        return str1.substring(0,flag);
    }
}

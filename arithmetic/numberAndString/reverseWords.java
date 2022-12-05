package numberAndString;

public class reverseWords {
    /***
     * 给定一个字符串 s ，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
     * ***/
    public String reverseWords(String s) {
        int length = s.length();
        int i= 0;
        StringBuffer ans = new StringBuffer();
        while(i<length){
            int start =i;
            //如果不是空格i++
            while(i<length && s.charAt(i) !=' '){
                i++;
            }
            //反转单词
            for(int p=start;p<i;p++){
                ans.append(s.charAt(i+start-p-1));
            }
            //如果是空字符时也添加到ans中
            while(i<length && s.charAt(i) ==' '){
                ans.append(' ');
                i++;
            }
        }
        return ans.toString();
    }
}

package numberAndString;

public class reverseString {
    //翻转字符串
    public void reverseString(char[] s) {
        int n = s.length;
        for(int i=0,j=n-1;i<j;i++,j--){
            Character temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }
    }
}

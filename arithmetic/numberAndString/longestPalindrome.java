package numberAndString;

public class longestPalindrome {
    //最回文子串
    public String longestPalindrome(String s) {
        if(s ==null & s.length() < 1){
            return "";
        }
        String maxStr = "";
        String tempStr = "";
        for(int i=0;i < s.length();i++){
            tempStr = getlongestPalindrome(s,i,i);
            if(tempStr.length() > maxStr.length()){
                maxStr = tempStr;
            }

            tempStr = getlongestPalindrome(s,i,i+1);
            if(tempStr.length() > maxStr.length()){
                maxStr = tempStr;
            }
        }
        return maxStr;
    }

    public String getlongestPalindrome(String s,int start,int end){
        while(start>=0 && end < s.length() && s.substring(start,start+1).equals(s.substring(end,end+1))){
            start--;
            end++;
        }
        return s.substring(start+1,end);
    }
}

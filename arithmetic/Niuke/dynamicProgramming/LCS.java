package Niuke.dynamicProgramming;
//两字符串的最长公共子串
public class LCS {
    /**
     * longest common substring
     * @param str1 string字符串 the string
     * @param str2 string字符串 the string
     * @return string字符串
     */
    public String LCS (String str1, String str2) {
        // write code here
        int maxLenth = 0; //记录最长公共子串的长度
        //记录最长公共子串最后一个元素在字符串str1中的位置
        int maxLastIndex = 0;
        int[] dp = new int[str2.length() + 1];
        for (int i = 0; i < str1.length(); i++) {
            for (int j = str2.length() - 1; j >= 0; j--) {
                //递推公式两字符串相等的情况
                if (str1.charAt(i) == str2.charAt(j)) {
                    dp[j + 1] = dp[j] + 1;
                    //如果遇到更长的子串，需要更新，记录最长子串长度
                    if (dp[j + 1] > maxLenth) {
                        maxLenth = dp[j + 1];
                        maxLastIndex = i;
                    }
                } else {
                    dp[j + 1] = 0;
                }
            }
        }
        return str1.substring(maxLastIndex - maxLenth + 1, maxLastIndex + 1);
    }
}

package Niuke.hw_easy;

import java.util.Scanner;

public class 最长回文子串 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        String max = "";
        max = longestPalindrome(s);
        System.out.print(max.length());
    }
    public static String longestPalindrome(String s){
        if(s==null & s.length() < 1){
            return "";
        }
        String temStr = "";
        String maxStr = "";
        for(int i=0;i<s.length();i++){
            temStr = getLongestPalindrome(s,i,i);
            if(temStr.length() > maxStr.length()){
                maxStr = temStr;
            }
            temStr = getLongestPalindrome(s,i,i+1);
            if(temStr.length() > maxStr.length()){
                maxStr = temStr;
            }
        }
        return maxStr;
    }
    public static String getLongestPalindrome(String s,int start,int end){
        while(start >=0 && end < s.length() && s.substring(start,start+1).equals(s.substring(end,end+1))){
            start--;
            end++;
        }
        return s.substring(start+1,end);
    }
}

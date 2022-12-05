package numberAndString;

public class longestCommonPrefix {
    //公共子串
    public String longestCommonPrefix(String[] strs) {
        //1.二分法
        return longestCommonPrefix(strs,0,strs.length -1);
    }
    public String longestCommonPrefix(String[] strs,int start,int end) {
        //2.二分法临界条件
        if(start == end){
            return strs[start];
        }
        //3.找到中位数
        int mid = (start + end)/2;
        //4.找到左右两个元素 到最后返回方法中执行逻辑
        String left = longestCommonPrefix(strs,start,mid);
        String right = longestCommonPrefix(strs,mid+1,end);
        return getLongestCommonPrefix(left,right);
    }
    public String getLongestCommonPrefix(String left,String right){
        //找到最短字符串的长度 遍历出左右不相等的下标截取即可
        int minLength = Math.min(left.length(),right.length());
        for(int i = 0;i<minLength;i++){
            if(left.charAt(i) != right.charAt(i)){
                return left.substring(0,i);
            }
        }
        return left.substring(0,minLength);
    }
}

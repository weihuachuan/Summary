package Niuke.hw2;

import java.util.Scanner;

/*
* 描述
IPV4地址可以用一个32位无符号整数来表示，一般用点分方式来显示，点将IP地址分成4个部分，每个部分为8位，表示成一个无符号整数（因此正号不需要出现），
* 如10.137.17.1，是我们非常熟悉的IP地址，一个IP地址串中没有空格出现（因为要表示成一个32数字）。
现在需要你用程序来判断IP是否合法。
数据范围：数据组数：1\le t\le 18\1≤t≤18
进阶：时间复杂度：O(n)\O(n) ，空间复杂度：O(n)\O(n)
输入描述：
输入一个ip地址，保证不包含空格
输出描述：
返回判断的结果YES or NO
示例1
输入：
255.255.255.1000
复制
输出：
NO
* */
public class 合法IP {
    public static void main(String[] args){

        Scanner in = new Scanner(System.in);

        while(in.hasNext()){
            String str = in.next();
            String[] nums = str.split("\\.", -1);
            String result = "YES";

            if(nums.length == 4){
                for(String element : nums) { //遍历每个元素，合不合法
                    if(element.length() == 0 || element.length() > 3){ //每段长度等于0，或者长度大于4，都不合法
                        result = "NO";
                        break;
                    }
                    for(Character ch : element.toCharArray()){ //每段的字符必须是数字
                        if(!Character.isDigit(ch)){
                            result = "NO";
                            break;
                        }
                    }
                    if(element.charAt(0) == '0' && element.length() != 1){ //除0以外，所有0开头的字符串都是非法的
                        result = "NO";
                        break;
                    }
                    if(Integer.parseInt(element) > 255){ //每段对应的数大于255，也是非法的
                        result = "NO";
                        break;
                    }
                }
            }else{
                result = "NO";
            }
            System.out.println(result);
        }
    }
}

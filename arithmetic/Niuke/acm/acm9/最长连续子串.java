package Niuke.acm.acm9;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
*  有N个正整数组成的一个序列
 给定一个整数sum
 求长度最长的的连续子序列使他们的和等于sum
 返回次子序列的长度
 如果没有满足要求的序列 返回-1
 案例1：
 输入
 1,2,3,4,2
 6
 输出
 3
 解析：1,2,3和4,2两个序列均能满足要求
 所以最长的连续序列为1,2,3 因此结果为3

 示例2：
 输入
 1,2,3,4,2
 20
 输出
 -1
 解释：没有满足要求的子序列，返回-1

 备注： 输入序列仅由数字和英文逗号构成
 数字之间采用英文逗号分割
 序列长度   1<=N<=200
 输入序列不考虑异常情况
 由题目保证输入序列满足要求
*/
public class 最长连续子串 {
    public static void test02(){
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        int num = Integer.parseInt(sc.nextLine());
        String[] split = line.split(",");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++){
            list.add(Integer.parseInt(split[i]));
        }
        int count = -1;
        for (int i = 0; i < list.size(); i++){
            if (list.get(i) == num) {
                count = Math.max(1,count);
            } else if (list.get(i) < num) {
                int sum = 0;
                int start = i;
                while (start < list.size()){
                    sum += list.get(start);
                    if (sum == num) {
                        count = Math.max(count, start+1-i);
                        break;
                    } else if (sum > num){
                        break;
                    } else {
                        start++;
                    }
                }
            }
        }
        System.out.println(count);
    }
}

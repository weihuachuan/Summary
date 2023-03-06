package Niuke.acm.acm5;

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
* */
public class 最长连续子序列的和等于输入值 {
    // 最长连续子序列的和等于输入值
    // 解题思路：双层循环，第一层遍历每一个数字，第二层往后累加，记录符合条件的长度，保留最大长度
    public static void test044(){
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        int num = Integer.parseInt(sc.nextLine());
        String[] split = line.split(",");
        int count = -1;
        for (int i = 0; i < split.length; i++) {
            // 连续子串累加值
            int sum = 0;
            // 记录起始位置
            int j = i;
            // 子循环，寻找符合条件的子串
            while (j < split.length) {
                sum += Integer.parseInt(split[j]);
                // 小于目标值，则向后走一位
                if (sum < num) {
                    j++;
                } else if (sum == num) { // 等于目标值，记录长度，只保留最大值
                    count = Math.max(count, j - i + 1);
                } else { // 超出目标值，退出子循环，从下个数开始
                    break;
                }
            }
        }
        System.out.println(count);
    }

}

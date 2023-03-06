package Niuke.acm.acm2;

import java.util.Scanner;

/*
*   双十一众多商品进行打折销售
  小明想购买自己心仪的一些物品
  但由于购买资金限制
  所以他决定从众多心仪商品中购买三件
  而且想尽可能得花完资金
  现在请你设计一个程序 计算小明尽可能花费的最大资金数

输入描述：
输入第一行为一维整型数组m
数组长度小于100
数组元素记录单个商品的价格
单个商品加个小于1000

输入第二行为购买资金的额度r
r<100000

输出描述：
 输出为满足上述条件的最大花费额度

   注意：如果不存在满足上述条件的商品请返回-1

  示例：
 输入
  23,26,36,27
  78
 输出
  76
 说明：
  金额23、26、27得到76而且最接近且小于输入金额78

   示例：
 输入
 23,30,40
 26
 输出
 -1
 说明
   因为输入的商品无法满足3件之和小于26
   故返回-1

   输入格式正确无需考虑输入错误情况4
*/
public class 满足最大消费额度 {
    // 满足最大消费额度
    public static void test077() {
        Scanner sc = new Scanner(System.in);
        String line1 = sc.nextLine();
        String line2 = sc.nextLine();
        String[] split = line1.split(",");
        int len = split.length;
        int sum = 0;
        // 依次遍历，列出所有的三数之和
        for (int i = 0; i < len - 2; i++) {
            for (int j = i + 1; j < len - 1; j++) {
                for (int k = j + 1; k < len; k++) {
                    int res = Integer.parseInt(split[i]) + Integer.parseInt(split[j]) + Integer.parseInt(split[k]);
                    if (res < Integer.parseInt(line2)) {
                        // 与原来的值比较，留下大的值
                        sum = Math.max(sum, res);
                    }
                }
            }
        }
        if (sum == 0) {
            System.out.println(-1);
        } else {
            System.out.println(sum);
        }
    }

}

package Niuke.acm.acm0;

import java.util.Scanner;

/*如果三个正整数A、B、C ,A²+B²=C²则为勾股数
        如果ABC之间两两互质，即A与B，A与C，B与C均互质没有公约数，
        则称其为勾股数元组。
        请求出给定n~m范围内所有的勾股数元组

        输入描述
        起始范围
        1 < n < 10000
        n < m < 10000

        输出描述
        ABC保证A<B<C
        输出格式A B C
        多组勾股数元组，按照A B C升序的排序方式输出。
        若给定范围内，找不到勾股数元组时，输出Na。

        示例一
        输入
        1
        20
        输出
        3 4 5
        5 12 13
        8 15 17
        示例二
        输入
        5
        10
        输出
        Na*/

// 解题思路：双层for循环，求出所有勾股数元组，在筛选出符合条件的
public class 勾股数元组 {
    public static void test001() {
        Scanner sc = new Scanner(System.in);
        int num1 = sc.nextInt();
        int num2 = sc.nextInt();
        boolean flag = true;
        for (int i = num1; i < num2; i++) {
            for (int j = i + 1; j <= num2; j++) {
                // pow()：取几次方，0.5次方相当于开根号
                double res = Math.pow(i, 2) + Math.pow(j, 2);
                double pow = Math.pow(res, 0.5);
                // 结果小于nums且是整数    判断一个double型数据是不是整数：Math.abs(pow - Math.round(pow)) < Double.MIN_VALUE
                if (pow <= num2 && Math.abs(pow - Math.round(pow)) < Double.MIN_VALUE) {
                    int powRes = (int) pow;
                    // 两两互质
                    if (isSingle(i, j) && isSingle(i, powRes) && isSingle(j, powRes)) {
                        System.out.println(i + " " + j + " " + powRes);
                        flag = false;
                    }
                }
            }
        }
        if (flag) {
            System.out.println("Na");
        }
    }

    /**
     * 判断两个数字是否互质 （没有相同的公约数）
     *
     * @param num1
     * @param num2
     * @return
     */
    private static boolean isSingle(int num1, int num2) {
        int res = Math.min(num1, num2);
        for (int i = 2; i <= res; i++) {
            if (num1 % i == 0 && num2 % i == 0) {
                return false;
            }
        }
        return true;
    }
}

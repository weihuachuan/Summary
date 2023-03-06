package Niuke.hw_easy;

import java.util.Scanner;

/*
* 输入：
11
1 2 3 4 5 6 7 8 9 0 -1
复制
输出：
1 5.0*/
public class 记负均正 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int countNegative = 0;
            int countPositive = 0;
            int sum = 0;
            for (int i = 0; i < n; i++) {
                int number = sc.nextInt();
                if (number < 0) {
                    countNegative++;
                } else if (number > 0){
                    sum += number;
                    countPositive++;
                }
            }
            double average = countPositive == 0? 0.0 : sum * 1.0 / countPositive;
            System.out.println(countNegative + " " + String.format("%.1f", average));
        }
        sc.close();
    }
}

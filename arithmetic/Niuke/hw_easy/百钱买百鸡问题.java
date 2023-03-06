package Niuke.hw_easy;

import java.util.Scanner;

public class 百钱买百鸡问题 {
    public static void main(String[] args) {
        // 鸡翁值5， 一百块最多买20只  鸡翁买x只
        // 鸡母值3， 一百块最多买33只  鸡母买y只
        // 鸡雏三值1， 一百块最多买300只 鸡雏买z只
        Scanner in = new Scanner(System.in);
        // 买x鸡翁，y只鸡母，z只鸡雏
        //5x+3y+z/3=100  ==>  15x + 9y + z = 300
        //x+y+z==100确定         // ==>   14x + 8y = 200  ==> 7x + 4y = 100  ==>  y = (100 - 7x)/4
        while (in.hasNextInt()) {
            int n = in.nextInt();
            for (int x = 0; x <= 14; x++) {
                if ((100 - 7 * x) % 4 == 0) {
                    int y = (100 - 7 * x) / 4;
                    int z = 100 - x - y;
                    System.out.println(x + " " + y + " " + z);
                }
            }
        }
    }
}

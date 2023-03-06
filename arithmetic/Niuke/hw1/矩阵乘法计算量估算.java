package Niuke.hw1;

import java.util.Scanner;
import java.util.Stack;

/*
* 描述
矩阵乘法的运算量与矩阵乘法的顺序强相关。
例如：
A是一个50×10的矩阵，B是10×20的矩阵，C是20×5的矩阵
计算A*B*C有两种顺序：((AB)C)或者(A(BC))，前者需要计算15000次乘法，后者只需要3500次。
编写程序计算不同的计算顺序需要进行的乘法次数。
输入描述：
输入多行，先输入要计算乘法的矩阵个数n，每个矩阵的行数，列数，总共2n的数，最后输入要计算的法则
计算的法则为一个字符串，仅由左右括号和大写字母（'A'~'Z'）组成，保证括号是匹配的且输入合法！
输出描述：
输出需要进行的乘法次数
示例1
输入：
3
50 10
10 20
20 5
(A(BC))
复制
输出：
3500*/
public class 矩阵乘法计算量估算 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] arr = new int[n][2];
        for(int i=0;i<n;i++){
            arr[i][0] = sc.nextInt();
            arr[i][1] = sc.nextInt();
            sc.nextLine();
        }
        int count = 0;
        String str = sc.nextLine();
        char[] chars = str.toCharArray();
        Stack<Integer> s = new Stack<>();
        for(int j=0;j<chars.length;j++){
            if(chars[j]=='('){
                continue;
            }else if(chars[j]==')'){
                int x = s.pop();
                int y = s.pop();
                int z = s.pop();
                int m = s.pop();
                count += x*y*m;
                s.push(m);
                s.push(x);
            }else{
                s.push(arr[chars[j]-65][0]);
                s.push(arr[chars[j]-65][1]);
            }
        }
        System.out.println(count);
    }
}

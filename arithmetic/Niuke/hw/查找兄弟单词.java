package Niuke.hw;

import java.util.*;

/*
* 兄弟单词要求和原来的单词不同。例如： ab 和 ba 是兄弟单词。 ab 和 ab 则不是兄弟单词。
现在给定你 n 个单词，另外再给你一个单词 x ，让你寻找 x 的兄弟单词里，按字典序排列后的第 k 个单词是什么？
注意：字典中可能有重复单词。

数据范围：1 \le n \le 1000 \1≤n≤1000 ，输入的字符串长度满足 1 \le len(str) \le 10 \1≤len(str)≤10  ， 1 \le k < n \1≤k<n
输入描述：
输入只有一行。 先输入字典中单词的个数n，再输入n个单词作为字典单词。 然后输入一个单词x 最后后输入一个整数k
输出描述：
第一行输出查找到x的兄弟单词的个数m 第二行输出查找到的按照字典顺序排序后的第k个兄弟单词，没有符合第k个的话则不用输出。
示例1
输入：
3 abc bca cab abc 1
复制
输出：
2
bca
复制
示例2
输入：
6 cab ad abcd cba abc bca abc 1
复制
输出：
3
bca
复制
说明：
abc的兄弟单词有cab cba bca，所以输出3
经字典序排列后，变为bca cab cba，所以第1个字典序兄弟单词为bca
* */
public class 查找兄弟单词 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            String[] ss = scanner.nextLine().split(" ");
            Integer a = Integer.parseInt(ss[0]);
            String x = ss[ss.length-2];
            Integer k = Integer.parseInt(ss[ss.length-1]);
            List<String> list = new ArrayList<>();

            for (int i = 1; i <=a ; i++) {
                if (isBrother(x,ss[i])){
                    list.add(ss[i]);
                }
            }
            int size = list.size();
            System.out.println(size);
            if (size>=k){
                Collections.sort(list);
                System.out.println(list.get(k-1));
            }
        }
    }
    public static boolean isBrother(String x,String y){
        if (x.length()!=y.length()||y.equals(x)){
            return false;
        }
        char[] s = x.toCharArray();
        char[] j= y.toCharArray();
        Arrays.sort(s);
        Arrays.sort(j);
        return new String(s).equals(new String(j));
    }
}

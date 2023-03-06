package Niuke.acm.acm8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
* 题目描述：
 二叉树也可以用数组来存储，
 给定一个数组，树的根节点的值储存在下标1，
 对于储存在下标n的节点，他的左子节点和右子节点分别储存在下标2*n和2*n+1，
 并且我们用-1代表一个节点为空，
 给定一个数组存储的二叉树，
 试求从根节点到最小的叶子节点的路径，
 路径由节点的值组成。

 输入描述
 输入一行为数组的内容，
 数组的每个元素都是正整数，元素间用空格分割，
 注意第一个元素即为根节点的值，
 即数组的第n元素对应下标n，
 下标0在树的表示中没有使用，所以我们省略了，
 输入的树最多为7层。

 输出描述
 输出从根节点到最小叶子节点的路径上各个节点的值，
 由空格分割，
 用例保证最小叶子节点只有一个。

 示例一
 输入
 3 5 7 -1 -1 2 4
 输出
 3 7 2
 示例二
 输入
 5 9 8 -1 -1 7 -1 -1 -1 -1 -1 6
 输出
 5 8 7 6
*/
public class 树根节点到最小的叶子节点的路径 {
    // 解题思路：找出除了根节点的最小节点，除以2得到的整数就是它的父节点，依次除下去，直至根节点
    // 树根节点到最小的叶子节点的路径
    public static void test011(){
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] s = line.split(" ");
        // 只输入一个根元素，直接返回
        if(s.length <= 1) {
            System.out.println(s[0]);
            return;
        }
        List<Integer> list = new ArrayList<>();
        // 集合第一个元素是无关元素，存个最大的整型数
        list.add(Integer.MAX_VALUE);
        for (int i = 0;i<s.length;i++) {
            list.add(Integer.parseInt(s[i]));
        }
        // 找出最小的数，-1表示该节点为空，不参与查找
        int res = Integer.MAX_VALUE;
        for (int i = 2;i<list.size();i++) {
            if (list.get(i) != -1) {
                res = Math.min(res, list.get(i));
            }
        }
        // 找不到最小的数，说明树除了根元素，其他都是-1
        if (res == Integer.MAX_VALUE) {
            System.out.println(s[0]);
            return;
        }
        // 找出最小的数所在位置
        int resKey = 0;
        for (int i = 2;i<list.size();i++) {
            if (list.get(i) == res) {
                resKey = i;
            }
        }
        // 用于储存结果
        List<Integer> resList = new ArrayList<>();
        while (resKey != 1) {
            resList.add(resKey);
            resKey = resKey / 2;
        }
        // 添加根元素
        resList.add(1);
        for (int i = resList.size() - 1; i >= 0;i--) {
            System.out.print(list.get(resList.get(i)) + " ");
        }
    }

    public static void main(String[] args) {
        test011();
    }

}

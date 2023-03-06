package Niuke.hw1;

import java.util.Scanner;

/*
* 描述
MP3 Player因为屏幕较小，显示歌曲列表的时候每屏只能显示几首歌曲，用户要通过上下键才能浏览所有的歌曲。为了简化处理，假设每屏只能显示4首歌曲，光标初始的位置为第1首歌。
现在要实现通过上下键控制光标移动来浏览歌曲列表，控制逻辑如下：
歌曲总数<=4的时候，不需要翻页，只是挪动光标位置。
光标在第一首歌曲上时，按Up键光标挪到最后一首歌曲；光标在最后一首歌曲时，按Down键光标挪到第一首歌曲。
* 输入描述：
输入说明：
1 输入歌曲数量
2 输入命令 U或者D
输出描述：
输出说明
1 输出当前列表
2 输出当前选中歌曲
示例1
输入：
10
UUUU
复制
输出：
7 8 9 10
7
* */
public class MP3光标位置 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            String cmd = sc.next();
            parseCmd(cmd, n);
        }
    }

    public static void parseCmd(String commands, int n) {
        // 页面的起始位置
        int start = 1;
        // 页面的末位置
        int end = Math.min(n, 4);
        // 光标的位置， 三个位置都是从1开始
        int index = 1;
        for(int i = 0; i < commands.length(); i++) {
            // 根据向上移动和向下移动的公式，光标位置的变化
            if(commands.charAt(i) == 'U') {
                index = (index-1-1+n) % n + 1;
            } else if(commands.charAt(i) == 'D') {
                index = index % n + 1;
            }
            // 判断滑动窗口的位置是否需要改变
            if(index < start) {
                // 光标在窗口之上
                start = index;
                end = start + 3;
            } else if(index > end){
                // 光标在窗口之下
                end = index;
                start = end - 3;
            }
        }
        // 输出窗口内容
        for(int i = start; i <= end; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        // 打印当前光标
        System.out.println(index);
    }
}

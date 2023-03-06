package Niuke.acm.acm1;

import java.util.Scanner;
/*
* 有一个特殊的五键键盘
上面有A、Ctrl-C、Ctrl-X、Ctrl-V、Ctrl-A
A键在屏幕上输出一个字母A
Ctrl-C将当前所选的字母复制到剪贴板
Ctrl-X将当前选择的字母复制到剪贴板并清空所选择的字母
Ctrl-V将当前剪贴板的字母输出到屏幕
Ctrl-A选择当前屏幕中所有字母
注意：
  1. 剪贴板初始为空
  2. 新的内容复制到剪贴板会覆盖原有内容
  3. 当屏幕中没有字母时,Ctrl-A无效
  4. 当没有选择字母时Ctrl-C、Ctrl-X无效
  5. 当有字母被选择时A和Ctrl-V这两个输出功能的键,
     会先清空所选的字母再进行输出

给定一系列键盘输入,
输出最终屏幕上字母的数量

输入描述:
   输入为一行
   为简化解析用数字12345分别代替A、Ctrl-C、Ctrl-X、Ctrl-V、Ctrl-A的输入
   数字用空格分割

输出描述:
    输出一个数字为屏幕上字母的总数量

示例一:
    输入:
      1 1 1
    输出:
      3

示例二:
    输入:
      1 1 5 1 5 2 4 4
    输出:
      2
*/
public class 计算屏幕字母数量 {
    // 计算屏幕字母数量
    public static void test085() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] split = line.split(" ");
        // 屏幕内容
        int result = 0;
        // 剪切板内容
        int ctrl_c_x = 0;
        // ctrl_A选中的内容
        int ctrl_a = 0;
        for (int i = 0; i < split.length; i++) {

            if (1 == Integer.parseInt(split[i])) { // A
                // Ctrl-A 选中内容，输入A则覆盖，屏幕内容长度为1
                if (ctrl_a != 0) {
                    result = 1;
                } else { // Ctrl-A 没选中内容，输入A屏幕内容长度加1
                    result++;
                }
                ctrl_a = 0;
            } else if (2 == Integer.parseInt(split[i])) { // Ctrl-C
                // 当Ctrl-A 选中内容时，Ctrl-C才有效
                if (0 != ctrl_a) {
                    // 剪切板内容等于ctrl_A选中的内容
                    ctrl_c_x = ctrl_a;
                }
            } else if (3 == Integer.parseInt(split[i])) { // Ctrl-X
                // 当Ctrl-A 选中内容时，Ctrl-X才有效
                if (0 != ctrl_a) {
                    // 剪切板内容等于ctrl_A选中的内容
                    ctrl_c_x = ctrl_a;
                    // Ctrl-X后屏幕内容长度为0
                    result = 0;
                    // Ctrl-X后ctrl_A选中内容长度为0
                    ctrl_a = 0;
                }
            } else if (4 == Integer.parseInt(split[i])) { // Ctrl-V
                // 当剪切板有内容ctrl_v才有用    // PS:这里有个疑问：当剪切板为空时，使用Ctrl-V是用空覆盖还是无效呢？？我的处理是无效，我觉得这里题目描述的不是很清楚
                if (0 != ctrl_c_x) {
                    // Ctrl-A 选中内容时
                    if (ctrl_a != 0) {
                        result = ctrl_c_x;
                        ctrl_a = 0;
                    } else if (ctrl_a == 0) { // Ctrl-A 没选中内容时
                        result += ctrl_c_x;
                    }
                }
            } else if (5 == Integer.parseInt(split[i])) { // Ctrl-A
                // 选中屏幕所有内容
                ctrl_a = result;
            }
        }
        System.out.println(result);
    }

}

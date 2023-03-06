package Niuke.hw1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/*
* 为了简化输入，方便用户，以“最短唯一匹配原则”匹配（注：需从首字母开始进行匹配）：

1、若只输入一字串，则只匹配一个关键字的命令行。例如输入：r，根据该规则，匹配命令reset，
* 执行结果为：reset what；输入：res，根据该规则，匹配命令reset，执行结果为：reset what；
2、若只输入一字串，但匹配命令有两个关键字，则匹配失败。例如输入：reb，可以找到命令reboot backpalne，
* 但是该命令有两个关键词，所有匹配失败，执行结果为：unknown command

3、若输入两字串，则先匹配第一关键字，如果有匹配，继续匹配第二关键字，如果仍不唯一，匹配失败。
例如输入：r b，找到匹配命令reset board 和 reboot backplane，执行结果为：unknown command。
例如输入：b a，无法确定是命令board add还是backplane abort，匹配失败。
4、若输入两字串，则先匹配第一关键字，如果有匹配，继续匹配第二关键字，如果唯一，匹配成功。例如输入：bo a，确定是命令board add，匹配成功。
5、若输入两字串，第一关键字匹配成功，则匹配第二关键字，若无匹配，失败。例如输入：b addr，无法匹配到相应的命令，所以执行结果为：unknow command。
6、若匹配失败，打印“unknown command”

注意：有多组输入。
数据范围：数据组数：1\le t\le 800\1≤t≤800 ，字符串长度1\le s\le 20\1≤s≤20
进阶：时间复杂度：O(n)\O(n) ，空间复杂度：O(n)\O(n)
输入描述：
多行字符串，每行字符串一条命令

输出描述：
执行结果，每条命令输出一行

示例1
输入：
reset
reset board
board add
board delet
reboot backplane
backplane abort
复制
输出：
reset what
board fault
where to add
no board at all
impossible
install first
* */
public class 配置文件恢复 {
    static Map<String, String> map = new HashMap<String, String>(){
        {
            put("reset", "reset what");
            put("reset board", "board fault");
            put("board add", "where to add");
            put("board delete", "no board at all");
            put("reboot backplane", "impossible");
            put("backplane abort", "install first");
        }
    };

    public static void main(String []args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String cmd = sc.nextLine();
            calculate(cmd);
        }
    }

    public static void calculate(String cmd) {
        String[] cmdArr = cmd.split(" ");
        if (cmdArr.length == 1) {
            if ("reset".contains(cmd)) {
                System.out.println(map.get("reset"));
            } else {
                System.out.println("unknown command");
            }
        } else {
            String result = "unknown command";
            int count = 0;
            for (String key : map.keySet()) {
                if (key.equals(cmd)) {
                    result = map.get(key);
                    break;
                } else {
                    String[] keyArr = key.split(" ");
                    if (keyArr.length > 1 && keyArr[0].indexOf(cmdArr[0]) == 0 && keyArr[1].indexOf(cmdArr[1]) == 0) {
                        result = map.get(key);
                        count++;
                    }
                }
            }
            System.out.println(count > 1 ? "unknown command" : result);
        }
    }
}

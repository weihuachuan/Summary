package Niuke.acm.acm5;

import java.util.Scanner;

/*
*   给定一个url前缀和url后缀
  通过,分割 需要将其连接为一个完整的url
  如果前缀结尾和后缀开头都没有/
  需要自动补上/连接符
  如果前缀结尾和后缀开头都为/
  需要自动去重
  约束：
  不用考虑前后缀URL不合法情况

  输入描述
  url前缀(一个长度小于100的字符串)
  url后缀(一个长度小于100的字符串)
  输出描述
  拼接后的url

  一、
  输入
  /acm,/bb
  输出
  /acm/bb

  二、
  输入
  /abc/,/bcd
  输出
  /abc/bcd

  三、
  输入
  /acd,bef
  输出
  /acd/bef

  四、
  输入
  ,
  输出
  /

* */
public class url拼接 {
    // url拼接
    public static void test038() {
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        in.close();

        String[] split = line.split(",");
        if (split.length == 0) {
            System.out.println("/");
            return;
        }

        String combine = split[0] + "/" + split[1];
        // 如果出现多个/，则替换成/
        String url = combine.replaceAll("/+", "/");
        System.out.println(url);
    }

}

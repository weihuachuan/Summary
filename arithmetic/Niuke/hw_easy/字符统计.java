package Niuke.hw_easy;

import java.util.Scanner;
import java.util.TreeMap;

/*
* 描述
输入一个只包含小写英文字母和数字的字符串，按照不同字符统计个数由多到少输出统计结果，如果统计的个数相同，则按照ASCII码由小到大排序输出。
数据范围：字符串长度满足
1
≤
�
�
�
(
�
�
�
)
≤
1000

1≤len(str)≤1000

输入描述：
一个只包含小写英文字母和数字的字符串。

输出描述：
一个字符串，为不同字母出现次数的降序表示。若出现次数相同，则按ASCII码的升序输出。

示例1
输入：
aaddccdc
复制
输出：
cda
复制
说明：
样例里，c和d出现3次，a出现2次，但c的ASCII码比d小，所以先输出c，再输出d，最后输出a.*/
public class 字符统计 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();

        //将字符存入TreeMap
        TreeMap<Character,Integer> map = new TreeMap<>();
        for (int i = 0; i < str.length(); i++) {
            if(!map.containsKey(str.charAt(i))){
                map.put(str.charAt(i),1);
            }else{
                map.put(str.charAt(i),map.get(str.charAt(i))+1);
            }
        }
//        Collection<Integer> values = map.values();
//        Collections.max(values);
        //找到max值
        int max = 0;
        for (int val:map.values()) {
            if(val>max)
                max = val;
        }

        //输出
        while (max>0){
            for (char key:map.keySet()){
                if(map.get(key)==max)
                    System.out.print(key);
            }
            max--;
        }

    }
}

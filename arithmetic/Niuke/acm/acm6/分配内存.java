package Niuke.acm.acm6;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/*
*   有一个简易内存池，内存按照大小粒度分类
  每个粒度有若干个可用内存资源
  用户会进行一系列内存申请
  需要按需分配内存池中的资源
  返回申请结果成功失败列表
  分配规则如下
  1.分配的内存要大于等于内存的申请量
  存在满足需求的内存就必须分配
  优先分配粒度小的，但内存不能拆分使用
  2.需要按申请顺序分配
  先申请的先分配，有可用内存分配则申请结果为true
  没有可用则返回false
  注释：不考虑内存释放

  输入描述
  输入为两行字符串
  第一行为内存池资源列表
  包含内存粒度数据信息，粒度数据间用逗号分割
  一个粒度信息内用冒号分割
  冒号前为内存粒度大小，冒号后为数量
  资源列表不大于1024
  每个粒度的数量不大于4096

  第二行为申请列表
  申请的内存大小间用逗号分割，申请列表不大于100000

  如
  64:2,128:1,32:4,1:128
  50,36,64,128,127

  输出描述
  输出为内存池分配结果
  如true,true,true,false,false

  示例一：
  输入：
  64:2,128:1,32:4,1:128
  50,36,64,128,127
  输出：
  true,true,true,false,false

  说明:
  内存池资源包含：64k共2个、128k共1个、32k共4个、1k共128个的内存资源
  针对50,36,64,128,127的内存申请序列，
  分配的内存依次是，64,64,128,null,null
  第三次申请内存时已经将128分配出去，因此输出的结果是
  true,true,true,false,false

* */
public class 分配内存 {
    // 分配内存
    // 解题思路：用一个有序的Map存储内存、内存数量，遍历需要分配的内存，在Map中找是否有满足要求的
    public static void test033() {
        Scanner sc = new Scanner(System.in);
        String[] split1 = sc.nextLine().split(",");
        String[] split2 = sc.nextLine().split(",");
        // treeMap是有序的，按照key降序排序，hashMap是无序的。key:内存大小，value：数量
        Map<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < split1.length; i++) {
            String[] split = split1[i].split(":");
            int num = Integer.parseInt(split[0]);
            int count = Integer.parseInt(split[1]);
            map.put(num, count);
        }
        // 拼接结果
        StringBuilder sb = new StringBuilder();
        // 遍历需要分配的内存
        for (int i = 0; i < split2.length; i++) {
            // 标志位，用来判断输入true/false
            boolean flag = true;
            // 遍历map
            for (Integer m : map.keySet()) {
                int key = Integer.parseInt(split2[i]);
                // 是否满足条件
                if (m > key && map.get(m) >= 1) {
                    sb.append("true,");
                    // 数量减1
                    map.put(m, map.get(m) - 1);
                    flag = false;
                    break;
                }
            }
            // 没有满足条件的，输出false
            if (flag) {
                sb.append("false,");
            }
        }
        System.out.println(sb.substring(0, sb.length() - 1));
    }

}

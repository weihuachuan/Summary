package Niuke.acm.acm5;

import java.util.*;

/*
*   幼儿园两个班的小朋友排队时混在了一起
  每个小朋友都知道自己跟前面一个小朋友是不是同班
  请你帮忙把同班的小朋友找出来
  小朋友的编号为整数
  与前面一个小朋友同班用Y表示
  不同班用N表示
  输入描述：
  输入为空格分开的小朋友编号和是否同班标志
  比如 6/N 2/Y 3/N 4/Y
  表示一共有4位小朋友
  2和6是同班 3和2不同班 4和3同班
  小朋友总数不超过999
  0< 每个小朋友编号 <999
  不考虑输入格式错误

  输出两行
  每一行记录一班小朋友的编号  编号用空格分开
  并且
  1. 编号需要按照大小升序排列，分班记录中第一个编号小的排在第一行
  2. 如果只有一个班的小朋友 第二行为空
  3. 如果输入不符合要求输出字符串ERROR

  示例：
  输入
  1/N 2/Y 3/N 4/Y
  输出
  1 2
  3 4
  说明：2的同班标记为Y因此和1同班
  3的同班标记位N因此和1,2不同班
  4的同班标记位Y因此和3同班

* */
public class 小朋友分班 {
    // 小朋友分班
    // 解题思路：用一个LinkedHashMap存储小朋友编号和Y/N，遍历map，用两个集合存放不同班的小朋友，判断小朋友应该加入哪个list
    public static void test040() {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        // map key存储每一个小朋友的编号,value存储N/Y。用LinkedHashMap是因为它是有序的，按插入顺序排序
        Map<Integer, String> map = new LinkedHashMap<>();
        try {
            String[] split = line.split(" ");
            for (int i = 0; i < split.length; i++) {
                String str = split[i];
                String[] split1 = str.split("/");
                // 第一个小朋友必须是N
                if (i == 0 && "Y".equals(split1[1])) {
                    System.out.println("ERROR");
                    return;
                }
                // 不是N/Y，或重复出现，报错
                if (!("N".equals(split1[1]) || "Y".equals(split1[1])) || map.containsKey(Integer.parseInt(split1[0]))) {
                    System.out.println("ERROR");
                    return;
                }
                map.put(Integer.parseInt(split1[0]), split1[1]);
            }
            // 用两个list存储两个班的小朋友
            List<Integer> list1 = new ArrayList<>();
            List<Integer> list2 = new ArrayList<>();
            // 先存第一个小朋友
            list1.add(Integer.parseInt(line.substring(0, line.indexOf("/"))));
            // 从map中移除第一个小朋友
            map.remove(Integer.parseInt(line.substring(0, line.indexOf("/"))), "N");
            // 用来判断小朋友应该加入哪个list
            boolean flag = true;
            // 遍历map，当flag=true，key=Y时，加入list1；key=N时，加入list2，且flag=false。当flag=false，key=Y时，加入list2；key=N时，加入list1，且flag=true。
            for (Integer s : map.keySet()) {
                if (flag) {
                    if ("Y".equals(map.get(s))) {
                        list1.add(s);
                    } else {
                        list2.add(s);
                        flag = false;
                    }
                } else {
                    if ("Y".equals(map.get(s))) {
                        list2.add(s);
                    } else {
                        list1.add(s);
                        flag = true;
                    }

                }
            }
            // 对list进行排序
            Collections.sort(list1);
            Collections.sort(list2);
            for (int k = 0; k < list1.size(); k++) {
                System.out.print(list1.get(k) + " ");
            }
            System.out.println();
            for (int k = 0; k < list2.size(); k++) {
                System.out.print(list2.get(k) + " ");
            }

        } catch (Exception e) {
            System.out.println("ERROR");
            return;
        }

    }

}

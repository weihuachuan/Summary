package Niuke.acm.acm3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
*   绘图机器的绘图笔初始位置在原点(0,0)
  机器启动后按照以下规则来进行绘制直线
  1. 尝试沿着横线坐标正向绘制直线
  直到给定的终点E
  2. 期间可以通过指令在纵坐标轴方向进行偏移
  offsetY为正数表示正向偏移,为负数表示负向偏移

  给定的横坐标终点值E 以及若干条绘制指令
  请计算绘制的直线和横坐标轴以及x=E的直线组成的图形面积
  输入描述:
  首行为两个整数N 和 E
  表示有N条指令,机器运行的横坐标终点值E
  接下来N行 每行两个整数表示一条绘制指令x offsetY
  用例保证横坐标x以递增排序的方式出现
  且不会出现相同横坐标x
  取值范围:
  0<N<=10000
  0<=x<=E<=20000
  -10000<=offsetY<=10000

  输出描述:
  一个整数表示计算得到的面积 用例保证结果范围在0到4294967295之内
  示例1:
  输入:
  4 10
  1 1
  2 1
  3 1
  4 -2
  输出:
  12

  示例2:
  输入:
  2 4
  0 1
  2 -2
  输出:
  4
*/
public class 计算图形面积 {
    // 这个通过率好像只有95%，没有具体的测试数据，我也不知道哪里有问题
    // 计算图形面积
    // 解题思路：每个点的面积等于下一个点x值 - 当前点x值 乘以 当前点y的累计值。最后一个点则是:终点x值 - 当前点x值 乘以 当前点y的累计值
    public static void test064() {
        Scanner sc = new Scanner(System.in);
        String line1 = sc.nextLine();
        String[] split1 = line1.split(" ");
        int num = Integer.parseInt(split1[0]);
        int end = Integer.parseInt(split1[1]);
        // 存放x坐标的值
        List<Integer> listX = new ArrayList<>();
        // 存放y坐标的值
        List<Integer> listY = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            String line = sc.nextLine();
            String[] split = line.split(" ");
            listX.add(Integer.parseInt(split[0]));
            listY.add(Integer.parseInt(split[1]));
        }
        // y的累计值
        int sumY = 0;
        // 总面积
        int sumArea = 0;
        for (int i = 0; i < num; i++) {
            // 最后一个点
            if (i == num - 1) {
                // y的累计值
                sumY += listY.get(i);
                // 终点x值 - 当前点x值 乘以 当前点y的累计值
                sumArea += (end - listX.get(i)) * Math.abs(sumY);
            } else {
                // y的累计值
                sumY += listY.get(i);
                // 下一个点x值 - 当前点x值 乘以 当前点y的累计值
                sumArea += (listX.get(i + 1) - listX.get(i)) * Math.abs(sumY);
            }
        }
        System.out.println(sumArea);
    }

}

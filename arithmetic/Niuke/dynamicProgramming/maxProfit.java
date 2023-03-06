package Niuke.dynamicProgramming;

public class maxProfit {
    /**
     *
     * @param prices int整型一维数组
     * @return int整型
     */
    public int maxProfit (int[] prices) {
        // write code here
        int minValue = Integer.MAX_VALUE;
        int maxProfit = 0;
        for(int price : prices){
            //寻找最低点
            if(price < minValue){
                minValue = price;
            }else if(price - minValue > maxProfit){
                //更新最大利润
                maxProfit = price - minValue;
            }
        }
        return maxProfit;
    }
}

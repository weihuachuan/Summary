package dynamic_planning;
//买股票的最佳时机
public class maxProfit {
    public int maxProfit(int[] prices) {
        int minValue = Integer.MAX_VALUE;
        int maxProfit=0;
        for(int price : prices){
            if(price < minValue){
                minValue = price;
            }else if(price - minValue > maxProfit){
                maxProfit = price - minValue;
            }
        }
        return maxProfit;
    }
}

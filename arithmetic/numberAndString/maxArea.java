package numberAndString;

public class maxArea {
    //最大盛水量
    public int maxArea(int[] height) {
        //1.定义最大值变量
        int maxContains=Integer.MIN_VALUE;
        int l = 0;
        int r = height.length - 1;
        //2.通过移动左右下标找到最大面积
        while(l <=r){
            maxContains = Math.max(maxContains,(r-l)*Math.min(height[r],height[l]));
            //3.小的柱子坐标往中间移动
            if(height[l] <= height[r]){
                l+=1;
            }else{
                r-=1;
            }
        }
        return maxContains;
    }
}

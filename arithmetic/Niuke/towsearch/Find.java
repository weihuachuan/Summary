package Niuke.towsearch;
//二维数组查找
public class Find {
    public boolean Find(int target, int [][] array) {
        //优先判断特殊
        if(array.length == 0){
            return false;
        }
        int n = array.length;
        if(array[0].length == 0){
            return false;
        }
        int m = array[0].length;
        //从左下角开始往右往上进行遍历
        for(int i = n-1,j=0;i>=0&&j < m;){
            //元素较大的网上走找小的
            if(array[i][j] >target){
                i--;
            }
            //元素较小往右走找大的
            else if(array[i][j] < target){
                j++;
            }else{
                return true;
            }
        }
        return false;
    }
}

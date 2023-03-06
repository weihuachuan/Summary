package Niuke.tree;
//旋转数组找最小值
public class minNumberInRotateArray {
    public int minNumberInRotateArray(int [] array) {
        int left = 0;
        int right = array.length -1;
        while(left < right){
            int mid = (left + right)/2;
            //最小数字在mid的右边
            if(array[mid] > array[right]){
                left = mid +1;
                //无法判断需要一个一个式
            }else if(array[mid] == array[right]){
                right--;
            }
            //最小数字要么是mid 要么是mid左边
            else{
                right = mid;
            }
        }
        return array[left];
    }
}

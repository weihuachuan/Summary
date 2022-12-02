package sortSearch;

import java.util.Random;
// 数组中的第K个最大元素
public class findKthLargest {
    public int findKthLargest(int[] nums, int k) {
        int length =nums.length;
        int targetIndex =length - k;
        int low =0,high=length-1;
        while(true){
            int i =partition(nums,low,high);
            if(i==targetIndex){
                return nums[targetIndex];
            }else if(i<targetIndex){
                low =i+1;
            }else{
                high =i-1;
            }
        }
    }
    public int partition(int []arr,int low,int high){
        if(high > low){
            int random =low + new Random().nextInt(high - low);
            swap(arr,random,high);
        }
        int i=low;
        int pravot =arr[high];

        for(int j=low;j<=high;j++){
            if(arr[j] < pravot){
                swap(arr,i,j);
                i++;
            }
        }
        swap(arr,i,high);
        return i;
    }

    public void swap(int [] arr,int i,int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
}

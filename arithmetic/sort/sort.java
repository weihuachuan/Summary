package com.huachuan.sort;

import java.util.Arrays;

public class sort {

    private static String bubbleSort(int[] arr){
       for(int i=0;i<arr.length-1;i++){
           for(int j=0;j < arr.length-i-1;j++){
               if(arr[j] > arr[j+1]){
                   int temp = arr[j];
                   arr[j] = arr[j+1];
                   arr[j+1] = temp;
               }
           }
           System.out.println("第"+i+"轮排序结果"+Arrays.toString(arr));
       }
       return Arrays.toString(arr);
    }
    //选择排序记录每一轮遍历的最小值的下标和值 来跟头部元素替换 System.out.println("一轮过后排序情况"+Arrays.toString(arr));
    private static String selectionSort(int arr[]){
        for(int i=0;i<arr.length;i++){
            boolean flag =false;
            int min = arr[i];
            int index = i;
            for(int j=i+1;j<arr.length;j++){
                if(arr[j] < min){
                    min =arr[j];
                    index = j;
                    flag =true;
                }
            }
            if(!flag){
                break;
            }
            int temp =arr[i];
            arr[i] = min;
            arr[index] = temp;
            System.out.println("第"+i+"轮排序结果"+Arrays.toString(arr));
        }
        return Arrays.toString(arr);
    }

    private static String insertionSort(int[] arr){
        System.out.println("插入排序");
        for(int i=1;i<arr.length;i++){
            for(int j=i;j>0;j--){
                if(arr[j] < arr[j-1]){
                    int temp = arr[j];
                    arr[j] =arr[j-1];
                    arr[j-1] =temp;
                }
            }
            System.out.println("第"+i+"轮排序结果"+Arrays.toString(arr));
        }
        return Arrays.toString(arr);
    }

    private static String shellSort(int arr[]){
        System.out.println("希尔排序");
        for(int i= arr.length/2;i>0;i=i/2){
            for(int j=i;j<arr.length;j++){
                for(int k=j;k>0 && k-i>=0;k=k-i){
                    if(arr[k] < arr[k-i]){
                        int temp =arr[k];
                        arr[k] =arr[k-i];
                        arr[k-i] = temp;
                    }
                }
                System.out.println("第"+i+"步长"+"第"+j+"轮排序结果"+Arrays.toString(arr));
            }
        }
        return Arrays.toString(arr);
    }

    private static  void quickSort(int[] arr,int low,int hight){
        if(hight - low < 1){
            return;
        }
        int start = low;
        int end = hight;
        int midValue = arr[low];
        boolean flag =true;
        while(true){
            if(flag){
                if(arr[hight] >= midValue){
                    hight--;
                }else {
                    arr[low] = arr[hight];
                    low++;
                    flag =false;
                }
            }else {
                if(arr[low] <= midValue){
                    low++;
                }else {
                    arr[hight] = arr[low];
                    hight--;
                    flag=true;
                }
            }
            if(low==hight){
                arr[low] = midValue;
                break;
            }
        }
        quickSort(arr,start,low-1);
        quickSort(arr,low+1,end);
    }




    public static void mergeSort(int[] arr,int start, int end){
        //1.查看数组是否拆分为最小单位
        if(end - start >0){
            mergeSort(arr,start,(start+end)/2);
            mergeSort(arr,(start+end)/2+1,end);
            int left = start;
            int right = (start + end) / 2 + 1;
            int[] result =new int[end - start + 1];
            int index =0;
            //合并最小的两个数组
            while (left <=(start +end)/2 && right <= end){
                if(arr[left] <= arr[right]){
                    result[index] = arr[left];
                    left++;
                }else {
                    result[index] = arr[right];
                    right++;
                }
                index++;
            }
            //当有一个数组合并完了
            while(left <=(start +end)/2 || right <= end){
                if(left <= (start+end)/2){
                    result[index] = arr[left];
                    left++;
                }else {
                    result[index] = arr[right];
                    right++;
                }
                index++;
            }

            for(int i =start;i<=end;i++){
                arr[i] = result[i-start];
            }
        }

    }

    private static int findKthLagest(int arr[],int k){
        int targetIndex =arr.length - k;
        int low=0,higth = arr.length - 1;
       while (true){
           int i = partiton(arr,low,higth);
           if(i == targetIndex){
               return arr[targetIndex];
           }else if(i < targetIndex){
               low = i + 1;
           }else {
               higth = i - 1;
           }
       }
    }

    private static int partiton(int[] arr, int low, int high) {
        int i = low;
        int privot = arr[high];
        for(int j =i;j <= high; j++){
            if(arr[j] < privot){
                swap(arr,i,j);
                i++;
            }
        }
        swap(arr,i,high);
        return i;
    }


    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void heapSort(int[] arr){
        //1.构建大堆顶
        for(int i = arr.length/2 - 1;i >= 0;i--){
            adjustHeap(arr,i,arr.length);
        }
        //交换首尾元素,重新调整结构
        for(int j = arr.length-1;j >0;j--){
            swap(arr,0,j);
            adjustHeap(arr,0,j);
        }
    }

    private static void adjustHeap(int[] arr, int i, int length) {
        int temp = arr[i];   //先取出当前元素i
        for(int k=2*i+1;k < length;k=k*2+1) { //从i节点的左子树开始，也就是2*i+1处
            if (k + 1 < length && arr[k] < arr[k + 1]) { //如果左子节点小于右子节点,那么指向右子节点
                k++;
            }
            if (arr[k] > temp) {   //如果子节点大于父节点那么将子节点赋值个父节点 不用进行交换
                arr[i] = arr[k];
                i = k;
            } else {
                break;
            }
            arr[i] = temp; //將temp值放到最終位置
        }
    }

    private static int findLittlerArrIndex(int[] arr,int target){
        int left =0;
        int right = arr.length - 1;
        if(arr.length<1 && arr[right] < target){
            return -1;
        }

        while (left <= right){
            int mid = (left + right)/2;
            if(arr[mid] > target){
                right = mid - 1;
            }else if(arr[mid] < target) {
                left = mid + 1;
            }else {
                return mid;
            }
        }
//        if(arr[left] == target){
//            return left;
//        }else {
//            return -1;
//        }
        return -1;
    }

    private static int findCntofNum(int[] arr,int target,boolean isLeft){
        int left =0,right=arr.length-1;
        int pos=0,mid;
        while (left <= right){
            mid = (left+right)/2;
            if(arr[mid] < target){
                left = mid + 1;
            }else if(arr[mid] > target){
                right =mid - 1;
            }else {
                pos = mid;
                if(isLeft){
                    right = mid - 1;
                }else {
                    left = mid + 1;
                }
            }
        }
        return pos;
    }
    public static void main(String[] args) {
        int [] arr = {1,2,3,4,5,6,7,8,9,10};
//        int [] arr ={1,1,2,2,2,3,3,3,3,4,4,4};
//        int [] arr ={9,8,7,6,5,4,3,2,1};
//        System.out.println(shellSort(arr));
//        mergeSort(arr,0,arr.length-1);
//        quickSort(arr,0,arr.length-1);

        //mergeSort(arr,0,arr.length-1);

        //heapSort(arr);
//        insertionSort(arr);
//        System.out.println("最终排序情况"+Arrays.toString(arr));
//          System.out.println(findKthLagest(arr,3));
//        System.out.println("最终排序情况."+Arrays.toString(arr));
        //System.out.println(findKthLagest(arr,4));;
        int littlerArrIndex = findLittlerArrIndex(arr, 11);
        System.out.println(littlerArrIndex);
//        int cntofNum = findCntofNum(arr, 2, false);
//        System.out.println(cntofNum);
    }
}

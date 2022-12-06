package com.huachuan.test;

public class Queen8 {
    static int count;
    int max =8;
    int arr[] = new int[max];

    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
        queen8.putQueen(0);
        System.out.println("一共有"+count+"种方法");
    }

    private void putQueen(int n) {
        if(max ==n){
            print();
            return;
        }
        for(int i=0;i<max;i++){
            arr[n] =i;
            if(judge(n)){
               putQueen(n+1);
            }
        }
    }

    private boolean judge(int n) {
        for(int i=0;i<n;i++){
            if(arr[i]==arr[n] || Math.abs(n-i) == Math.abs(arr[i] -arr[n])){
                return false;
            }
        }
        return true;
    }

    private void print() {
        for (int i=0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
        count++;
        System.out.println();
    }
}

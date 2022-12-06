package com.huachuan.test;

public class Dijsktra {
    static final int MAX = 1000;
    public static void main(String[] args) {
//        int MAX = Integer.MAX_VALUE;
        int[][] weight = {
                {0,3,6,7,MAX},
                {3,0,4,2,MAX},
                {6,4,0,5,4},
                {7,2,5,0,6},
                {MAX,MAX,4,6,0}
        };
        int start = 0;
        int[] dijsktra = Dijsktra1(weight,start);
    }

    private static int[] Dijsktra1(int[][] weight, int start) {
        int length = weight.length;
        int [] shortPath = new int[length];  //记录到当前点的最短路径
        shortPath[0] = 0;
        String path[] = new String[length];  //记录从start到当前点最短路径的字符串表示
        for(int i=0;i<length;i++){
            path[i] = start +"->"+ i;
        }
        int [] visited = new int[length];    //记录当前点最短路径是找到 1代表找到
        visited[0] = 1;
        for (int count=1;count<length;count++){
            int k=-1;
            int dmin = Integer.MAX_VALUE;
            for(int i=0;i<length;i++){
                if(visited[i]==0 && weight[start][i] < dmin){
                    dmin =weight[start][i];
                    k=i;
                }
            }
            shortPath[k] = dmin;
            visited[k] =1;

            //以k点为中阶点修正最短路径
            for(int i=0;i<length;i++){
                if(visited[i]==0 && weight[start][k]+weight[k][i] < weight[start][i]){
                        weight[start][i] = weight[start][k]+weight[k][i];
                        path[i] =path[k]+"->"+ i;
                }
            }
        }
        for(int i=0;i<length;i++){
            System.out.println("从"+start+"到"+i+"最短路径"+path[i]+"="+shortPath[i]);
        }
        return shortPath;
    }
}

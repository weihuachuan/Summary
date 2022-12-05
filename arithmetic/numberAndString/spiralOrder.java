package numberAndString;

import java.util.ArrayList;
import java.util.List;

public class spiralOrder {
    //螺旋矩阵
    public List<Integer> spiralOrder(int[][] matrix) {
        int t=0,d=matrix.length-1;
        int l=0,r=matrix[0].length-1;
        ArrayList<Integer> list = new ArrayList<Integer>();
        //1.循环条件和退出条件是什么
        while(true){
            for(int i=l;i<=r;i++) list.add(matrix[t][i]);
            t++;
            if(t>d)break;
            for(int i=t;i<=d;i++) list.add(matrix[i][r]);
            r--;
            if(l>r) break;
            for(int i=r;i>=l;i--) list.add(matrix[d][i]);
            d--;
            if(t>d)break;
            for(int i=d;i>=t;i--) list.add(matrix[i][l]);
            l++;
            if(l>r)break;
        }
        return list;
    }
}

package numberAndString;

public class multiply {
    //字符串相乘
    public String multiply(String num1, String num2) {
        if(num1.equals("0") || num2.equals("0")){
            return "0";
        }
        //定义m n 记录两字符串的长度
        int m = num1.length();
        int n = num2.length();
        int [] ant = new int [m+n];
        for(int i=m-1;i>=0;i--){
            int x=num1.charAt(i) - '0';
            for(int j = n-1;j>=0;j--){
                int y=num2.charAt(j) - '0';
                ant[i+j+1] += x*y;
            }
        }
        //2.解决进位问题
        for(int i = n + m -1;i>0;i--){
            ant[i-1] += ant[i]/10;
            ant[i] = ant[i]%10;
        }
        //3.将数组转换为字符串 注意最高位是否为0
        StringBuffer ans = new StringBuffer();
        int index = ant[0]==0?1:0;
        while(index < m + n){
            ans.append(ant[index]);
            index++;
        }
        return ans.toString();
    }
}

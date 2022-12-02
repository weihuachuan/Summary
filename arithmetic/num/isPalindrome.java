package num;
// 回文数
public class isPalindrome {

    public boolean isPalindrome(int x) {
        //1.小于0则返回false
        if(x < 0){
            return false;
        }
        //2.用于求最高位的数 7999 7000
        int temp = 1;
        while(x/temp >= 10){
            temp*= 10;
        }
        //3.当 x > 0 截取最低和最高位对比 不相等返回false
        while(x > 0){
            int right = x%10;
            int lelft = x/temp;
            if(right != lelft){
                return false;
            }
            //4.去头去尾
            x = x % temp / 10;
            //5. 7000/100 = 70
            temp = temp / 100;
        }
        return true;
    }
}

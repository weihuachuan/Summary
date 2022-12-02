package num;
// 整数反转
public class reverse {
    public int reverse(int x) {
        int rev = 0, a = 0;
        while(x!=0){
            a=x%10;
            x=x/10;
            if(rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE/10 && a > 7)){
                return 0;
            }
            if(rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE/10 && a < -8)){
                return 0;
            }
            rev = rev*10 + a;
        }
        return rev;
    }
}

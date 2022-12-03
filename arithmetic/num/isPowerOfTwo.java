package num;
// 2的幂
public class isPowerOfTwo {
    public boolean isPowerOfTwo(int n) {
        return n>0 && (n&(n-1))==0;
    }
}

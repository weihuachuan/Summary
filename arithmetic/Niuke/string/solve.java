package Niuke.string;

public class solve {
    /**
     * 验证IP地址
     * @param IP string字符串 一个IP地址字符串
     * @return string字符串
     */
    public String solve (String IP) {
        // write code here
        if(isIPv4(IP)){
            return "IPv4";
        }else if(isIPv6(IP)){
            return "IPv6";
        }
        return "Neither";
    }
    public boolean isIPv4(String IP){
        if(IP.indexOf('.') == -1){
            return false;
        }
        String[] s = IP.split("\\.");
        //IPv4必定为4组
        if(s.length != 4)
            return false;
        for(int i = 0; i < s.length; i++){
            //不可缺省，有一个分割为零，说明两个点相连
            if(s[i].length() == 0)
                return false;
            //比较数字位数及不为零时不能有前缀零
            if(s[i].length() < 0 || s[i].length() > 3 || (s[i].charAt(0)=='0' && s[i].length() != 1))
                return false;
            int num = 0;
            //遍历每个分割字符串，必须为数字
            for(int j = 0; j < s[i].length(); j++){
                char c = s[i].charAt(j);
                if (c < '0' || c > '9')
                    return false;
                //转化为数字比较，0-255之间
                num = num * 10 + (int)(c - '0');
                if(num < 0 || num > 255)
                    return false;
            }
        }
        return true;
    }

    public boolean isIPv6(String IP){
        if (IP.indexOf(':') == -1) {
            return false;
        }
        String[] s = IP.split(":",-1);
        //IPv6必定为8组
        if(s.length != 8){
            return false;
        }
        for(int i = 0; i < s.length; i++){
            //每个分割不能缺省，不能超过4位
            if(s[i].length() == 0 || s[i].length() > 4){
                return false;
            }
            for(int j = 0; j < s[i].length(); j++){
                //不能出现a-fA-F以外的大小写字符
                char c = s[i].charAt(j);
                boolean expr = (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F') ;
                if(!expr){
                    return false;
                }
            }
        }
        return true;
    }
}

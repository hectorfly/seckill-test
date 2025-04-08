package com.xb.seckilltest.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class MD5Util {

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "2sc9s0xz2ned";

    /**
     * 第一次加密是固定的
     * @param str
     * @return
     */
    public static String inputPassToFromPass(String str){
        str = "" + salt.charAt(1) + salt.charAt(6) + str + salt.charAt(5) + salt.charAt(0);
        return md5(str);
    }

    /**
     * 第二次加密是 salt 指的是数据库拿的
     * @param str
     * @param salt
     * @return
     */
    public static String fromPassToDBPass(String str,String salt){
        str = "" +  salt.charAt(1) + salt.charAt(3) + str + salt.charAt(2) + salt.charAt(0);
        return md5(str);
    }

    /**
     * 直接加密成数据库保存的数据
     * @param str
     * @param salt
     * @return
     */
    public static String inputPassToDBPass(String str,String salt){
        String s1 = inputPassToFromPass(str);
        return fromPassToDBPass(s1, salt);
    }
//78fcfa35606730a75be9c726d616a526
    public static void main(String[] args) {
        String s1 = inputPassToDBPass("123456", salt);
        System.out.println(s1);
//        System.out.println(fromPassToDBPass("78fcfa35606730a75be9c726d616a526", salt));

    }




}

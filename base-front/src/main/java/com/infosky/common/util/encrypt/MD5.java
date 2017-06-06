package com.infosky.common.util.encrypt;

import java.security.MessageDigest;

/**
 * 本类从别处抄袭过来的 呵呵呵呵
 * 
 * @author  李艳海
 * @version  [版本号, Jun 7, 2012]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class MD5 {

    private static final String[] HEXDIGITS = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"
    };

    private MD5() {
    }

    /**
     * 转换字节数组为16进制字串
     * 
     * @param b
     *            字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 字节转成16进制字符
     * 
     * @param b
     *            byte
     * @return String
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEXDIGITS[d1] + HEXDIGITS[d2];
    }

    /**
     * 返回经MD5算法加密后的16进制字符串
     * 
     * 
     * @param origin
     *            String
     * @return String
     */
    public static String md5Encode(String origin) {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            resultString = byteArrayToHexString(md.digest(origin.getBytes()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    public static void main(String[] ss) {
        if (null != ss && ss.length > 0) {
            for (String s : ss) {
                System.out.println(MD5.md5Encode(s));
            }
        }
    }
}

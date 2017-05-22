package com.infosky.wechat.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.infosky.common.util.PropertiesConfig;

public class SignUtil {

    /**
    * 校验签名
    * 
    * @param signature 微信加密签名
    * @param timestamp 时间戳
    * @param nonce     随机数
    * 
    * @return boolean
    */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String token = PropertiesConfig.readValue("token");
        String[] arr = new String[] {
                token, timestamp, nonce
        };
        // 排序将token、timestamp、nonce三个参数进行字典序排序 
        Arrays.sort(arr);
        StringBuilder contents = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            contents.append(arr[i]);
        }
        MessageDigest md = null;
        String tempStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(contents.toString().getBytes());
            tempStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return tempStr != null && signature != null ? tempStr.toUpperCase().equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符
     * 
     * @param byteArray
     * @return
     */
    public static String byteToStr(byte[] byteArray) {

        String strDigest = "";

        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }

        return strDigest;
    }

    /**
     * 将字符转换为十六进制字符串
     * 
     * @param mByte
     * @return
     */
    public static String byteToHexStr(byte mByte) {
        char[] Digit = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };

        char[] tempStr = new char[2];

        tempStr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempStr[1] = Digit[mByte & 0X0F];

        String s = new String(tempStr);

        return s;
    }

    public static Map<String, String> jsapiSign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());//digest() 生成散列码
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonce_str", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 字节数组转换为 十六进制
     * @param hash 散列码
     * @return
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);//%02X：以十六进制输出,2为指定的输出字段的宽度.如果位数小于2,则左端补0
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}

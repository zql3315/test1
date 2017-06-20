package com.infosky.common.util.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author n004881
 *MD5 即Message-Digest Algorithm 5（信息-摘要算法 5），用于确保信息传输完整一致。是计算机广泛使用的杂凑算法之一（又译摘要算法、哈希算法），
 *主流编程语言普遍已有MD5实现。将数据（如汉字）运算为另一固定长度值，是杂凑算法的基础原理，MD5的前身有MD2、MD3和MD4。
 *MD5的作用是让大容量信息在用数字签名软件签署私人密钥前被"压缩"成一种保密的格式（就是把一个任意长度的字节串变换成一定长的十六进制数字串）。
 */
public class EncrypMD5 {

    public static byte[] eccrypt(String info) throws NoSuchAlgorithmException {
        //根据MD5算法生成MessageDigest对象
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] srcBytes = info.getBytes();
        //		使用srcBytes更新摘要
        md5.update(srcBytes);
        //完成哈希计算，得到result
        //md.digest() 该函数返回值为存放哈希值结果的byte数组  
        byte[] resultBytes = md5.digest();
        return resultBytes;
    }

    public static void main(String args[]) throws NoSuchAlgorithmException {
        String msg = "111111";
        //		String msg = "郭XX-精品相声技术郭XX";
        //		baeaa57ba98a2056748620193c414a2e
        //		80827c434cae1cb9029eac010eadb9ba
        byte[] resultBytes = EncrypMD5.eccrypt(msg);

        System.out.println("十六进制的密文是：" + EncrypMD5.getStr(resultBytes));
        System.out.println("明文是：" + msg);

        String sign = DigestUtils.md5Hex(msg).toString().toLowerCase();

        System.out.println("密文是：" + sign);

    }

    // 将加密后的byte数组转换为十六进制的字符串,否则的话生成的字符串会乱码
    public static String getStr(byte byteArray[]) {
        StringBuffer md5StrBuff = new StringBuffer("");
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString();//32 位
        //		return md5StrBuff.toString().substring(8,24);//16 位
    }

}

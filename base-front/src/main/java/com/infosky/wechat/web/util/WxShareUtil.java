package com.infosky.wechat.web.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.infosky.common.util.PropertiesConfig;

/**
 * 微信分享工具类
 */
public class WxShareUtil {

    private static final Log logger = LogFactory.getLog(WxShareUtil.class);

    /**
     * @Description 获取微信分享配置信息
     * @param requestUrl
     * @param access_token
     * @return
     */
    public static Map<String, String> getWxShareConfig(String requestUrl, String jsapi_ticket) {
        Map<String, String> map = new HashMap<String, String>();

        String appId = PropertiesConfig.readValue("appId");

        // 必填，生成签名的时间戳
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        // 必填，生成签名的随机串
        String nonceStr = UUID.randomUUID().toString();
        // 获取jsapi_ticket---------------测试
        String signature = "";

        // 注意这里参数名必须全部小写，且必须有序
        String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + requestUrl;

        try {
            // 验证签名
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(sign.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        logger.info("nonceStr=" + nonceStr);
        logger.info("timestamp=" + timestamp);
        logger.info("signature=" + signature);
        logger.info("appid=" + appId);
        logger.info("jsapi_ticket=" + jsapi_ticket);
        logger.info("requestUrl=" + requestUrl);

        map.put("appid", appId);
        map.put("timestamp", timestamp);
        map.put("nonceStr", nonceStr);
        map.put("signature", signature);

        return map;
    }

    /**
     * 字符串加密辅助方法
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();

        for (byte b : hash) {
            formatter.format("%02x", b);
        }

        String result = formatter.toString();
        formatter.close();

        return result;
    }

    public static void main(String[] args) throws Exception {
        String url = "http://example.com";
        Map<String, String> map = getWxShareConfig(url, "");

        for (Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

}

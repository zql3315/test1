package com.infosky.common.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

    /**
     * 创建自定义位数的随机数   大小写字母和数字
     * @return
     */
    public static String CreateNoncestr(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < length; i++) {
            Random rd = new Random();
            res.append(chars.charAt(rd.nextInt(chars.length() - 1)));
        }
        return res.toString();
    }

    /**
     * 创建16位的随机字符  大小写字母和数字
     * @return
     */
    public static String CreateNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            res.append(chars.charAt(rd.nextInt(chars.length() - 1)));
        }
        return res.toString();
    }

    /**
     * 按照map里面的key值进行ASCII从小到大排序（字典序）
     * @param paraMap
     * @param urlencode 是否要UTF-8的URl编码
     * @return
     * 返回一个键值对的格式（即key1=value1&key2=value2...）拼接成字符串
     */
    public static String FormatBizQueryParaMap(Map<String, String> paraMap, boolean urlencode) {
        StringBuffer buff = new StringBuffer();
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(paraMap.entrySet());
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

            for (int i = 0; i < infoIds.size(); i++) {
                Map.Entry<String, String> item = infoIds.get(i);
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlencode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    buff.append(key + "=" + val + "&");
                }
            }

            if (buff.toString().isEmpty() == false) {
                return buff.toString().substring(0, buff.toString().length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buff.toString();
    }

    /**
     * 用正则表达式判断是否整形
     * @param str
     * @return
     */
    public static boolean IsNumeric(String str) {
        if (str == null) return false;
        Pattern pattern = Pattern.compile("[0-9]+");
        return pattern.matcher(str).matches();
    }

    /**
     * 浮点型判断
    * @param str
    * @return
    */
    public static boolean isDecimal(String str) {
        if (str == null || "".equals(str)) return false;
        Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 用正则表达式判断是否手机号码
     * @param str
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 转换map对象为包含CDATA的xml格式字符串
     * @param arr
     * @return
     */
    public static String ArrayToXml(Map<String, String> arr) {
        String xml = "<xml>";

        Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            String key = entry.getKey();
            String val = entry.getValue();
            if (IsNumeric(val)) {
                xml += "<" + key + ">" + val + "</" + key + ">";

            } else
                xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
        }

        xml += "</xml>";
        return xml;
    }

    // 判断是否 AJAX 请求
    public static boolean useAjax(HttpServletRequest request) {
        return request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
    }

}

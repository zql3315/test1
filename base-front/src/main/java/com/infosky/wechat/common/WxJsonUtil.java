package com.infosky.wechat.common;

import net.sf.json.JSONObject;

/**
 * JSON数据转换帮助类
 * @author jian
 *
 */
public class WxJsonUtil {

    /**
     * 对象转换为JSON字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        JSONObject jsonObj0 = JSONObject.fromObject(obj);
        return jsonObj0.toString();
    }

    /**
     * JSON字符串转换为对象
     * @param json
     * @param c
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Class<T> c) {

        JSONObject jsonObj0 = JSONObject.fromObject(json);
        T newInstance = (T) JSONObject.toBean(jsonObj0, c);
        return newInstance;
    }

}

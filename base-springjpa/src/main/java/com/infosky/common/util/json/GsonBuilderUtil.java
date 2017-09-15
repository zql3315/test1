package com.infosky.common.util.json;

import java.text.DateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Gson 工具类
 * 
 * @author n004881
 */
public class GsonBuilderUtil {

    /**
     * 创建一个可以转换毫秒时间字符串到 Date 或者 Timestamp 类型的Gson
     * 
     * @return
     */
    public static Gson createDateFormat() {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(java.util.Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
        gb.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG);
        Gson gson = gb.create();
        return gson;
    }

}

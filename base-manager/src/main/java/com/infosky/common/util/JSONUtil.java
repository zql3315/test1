package com.infosky.common.util;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json 工具类
 * 
 * @author n004881
 */
public class JSONUtil {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public static String formatStr(String value) {
        if (value == null) {
            return "";
        }

        return value;
    }

    public static Integer formatInt(Integer value) {
        if (value == null) {
            return -1;
        }

        return value;
    }

    /**
     * 把对象转换成json的数组字节
     * 
     * @param object
     * @return
     * @throws IOException
     */
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}

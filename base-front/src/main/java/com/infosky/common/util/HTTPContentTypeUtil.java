package com.infosky.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 获取文件类型工具类
 */
public class HTTPContentTypeUtil {

    private static Properties property;

    static {
        //InputStream file = HTTPContentTypeUtil.class.getResourceAsStream("mimetype.properties");
        String path = Thread.currentThread().getContextClassLoader().getResource("mimetype.properties").getPath();
        try {
            property = new Properties();
            //property.load(new FileInputStream("D:\\gittest\\test1\\base-front\\src\\main\\resources\\mimetype.properties"));
            property.load(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readValue(String suffix) {
        return property.getProperty(suffix).trim();
    }
}

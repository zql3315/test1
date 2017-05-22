package com.infosky.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 系统工具类
 *
 * @author n004881
 *
 */
public class PropertiesConfig {

    public static Properties property;
    static {
        String path = Thread.currentThread().getContextClassLoader().getResource("constant.properties").getPath();
        try {
            property = new Properties();
            property.load(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readValue(String key) {
        return property.getProperty(key);
    }
}

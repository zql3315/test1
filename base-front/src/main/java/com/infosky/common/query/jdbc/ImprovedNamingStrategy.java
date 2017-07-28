package com.infosky.common.query.jdbc;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zan
 * @version  [版本号, 2015年2月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ImprovedNamingStrategy {

    public String columnToProperty(String column) {
        String[] array = column.split("_");

        StringBuffer buff = new StringBuffer();
        buff.append(array[0].toLowerCase());
        for (int i = 1; i < array.length; i++) {
            buff.append(array[i].substring(0, 1) + array[i].substring(1).toLowerCase());
        }

        return buff.toString();
    }
}

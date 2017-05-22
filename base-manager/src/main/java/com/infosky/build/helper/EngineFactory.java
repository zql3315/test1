package com.infosky.build.helper;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class EngineFactory {

    public static Query buildEQuery(String type, String url, String user, String password) {
        if ("oracle.jdbc.driver.OracleDriver".equals(type)) {
            return new OracleQuery(url, user, password);
        } else if ("com.mysql.jdbc.Driver".equals(type)) {
            return new MySQLQuery(url, user, password);
        }

        return null;
    }
}

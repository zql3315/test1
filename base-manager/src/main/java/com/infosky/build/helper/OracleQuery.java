package com.infosky.build.helper;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.lang3.StringUtils;

import com.infosky.build.entity.dto.Column;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OracleQuery extends AbstractQuery {

    private String url;

    private String user;

    private String password;

    public enum Type {
        VARCHAR2("VARCHAR2", "String"), DOUBLE("NUMBER(n,m)", "double"), LONG("NUMBER(n)", "long"), TIMESTAMP("TIMESTAMP", "java.util.Date"),DATETIME("DATETIME", "java.util.Date");

        private String db_type;

        private String java_type;

        Type(String db_type, String java_type) {
            this.db_type = db_type;
            this.java_type = java_type;
        }

        public static String getJavaType(Column col) {
            for (Type type : Type.values()) {
                if (type.db_type.equals(col.getType())) {
                    return type.java_type;
                } else if ("NUMBER".equals(col.getType())) {
                    if (StringUtils.isNotEmpty(col.getType())) {
                        return Type.DOUBLE.java_type;
                    } else {
                        return Type.LONG.java_type;
                    }

                }
            }

            return Type.VARCHAR2.java_type;
        }
    }

    /** <默认构造函数>
     */
    public OracleQuery() {
    }

    /** <默认构造函数>
     */
    public OracleQuery(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /** {@inheritDoc} */

    @Override
    protected Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    /** {@inheritDoc} */

    @Override
    protected String getType(Column col) {
        return Type.getJavaType(col);
    }

}

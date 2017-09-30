package com.infosky.build.helper;

import java.sql.Connection;
import java.sql.DriverManager;

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
public class MySQLQuery extends AbstractQuery {

    private String url;

    private String user;

    private String password;

    public enum Type {
        VARCHAR("VARCHAR", "String"), BLOB("BLOB", "String"), BIGINT("BIGINT", "int"), INT("INT", "int"), FLOAT("FLOAT", "float"), DOUBLE("DOUBLE", "double"), DATE("DATE", "java.util.Date"), BOOLEAN(
                "BOOLEAN", "boolean"), TIMESTAMP("TIMESTAMP", "java.util.Date"), DATETIME("DATETIME", "java.util.Date");

        private String db_type;

        private String java_type;

        Type(String db_type, String java_type) {
            this.db_type = db_type;
            this.java_type = java_type;
        }

        public static String getJavaType(String db_type) {
            for (Type type : Type.values()) {
                if (type.db_type.equals(db_type)) {
                    return type.java_type;
                }
            }

            return db_type;
        }
    }

    /** <默认构造函数>
     */
    public MySQLQuery(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /** {@inheritDoc} */

    @Override
    protected Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    /** {@inheritDoc} */

    @Override
    protected String getType(Column col) {
        return Type.getJavaType(col.getType());
    }

}

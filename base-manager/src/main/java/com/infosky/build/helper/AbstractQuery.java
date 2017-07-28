package com.infosky.build.helper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;
import com.infosky.build.entity.dto.Column;
import com.infosky.common.query.jdbc.ImprovedNamingStrategy;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractQuery implements Query {

    protected abstract Connection getConnection();

    protected abstract String getType(Column col);

    public void close(Connection conn) {
        try {
            if (null != conn) {

                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */

    @SuppressWarnings("resource")
    public List<Column> getColumns(String tableName) {
        Connection conn = null;
        ResultSet rs = null;
        List<Column> columns = Lists.newArrayList();

        try {
            conn = this.getConnection();
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getPrimaryKeys(null, null, tableName);
            String pk_name = "";
            while (rs.next()) {
                pk_name = rs.getString(4);

            }

            rs = dbmd.getColumns(null, "%", tableName, "%");

            while (rs.next()) {
                Column col = new Column();

                String columnName = rs.getString("COLUMN_NAME");

                if (pk_name.equals(columnName)) {
                    continue;
                }

                String columnType = rs.getString("TYPE_NAME");
                int datasize = rs.getInt("COLUMN_SIZE");
                int digits = rs.getInt("DECIMAL_DIGITS");
                int nullable = rs.getInt("NULLABLE");
                String remarks = rs.getString("REMARKS");
                ImprovedNamingStrategy improved = new ImprovedNamingStrategy();

                String propertyName = improved.columnToProperty(columnName);

                col.setColumnName(columnName);
                col.setType(columnType);
                col.setLength(datasize);
                col.setDigits(digits);
                col.setNullable(nullable);
                col.setRemarks(remarks);

                col.setPropertyName(propertyName);
                col.setJavaType(getType(col));
                col.setSetter("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
                col.setGetter("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));

                columns.add(col);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != rs) {
                    rs.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }

        return columns;
    }

}

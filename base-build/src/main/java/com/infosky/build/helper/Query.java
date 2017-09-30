package com.infosky.build.helper;

import java.sql.Connection;
import java.util.List;

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
public interface Query {

    List<Column> getColumns(String tableName);

    void close(Connection conn);
}

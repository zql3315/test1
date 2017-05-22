package com.infosky.demo.entity.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 工程项目
 * 
 * @author xx
 * @version [版本号, xx年xx月xx日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DemoMapperDTO implements RowMapper<DemoDTO> {

    @Override
    public DemoDTO mapRow(ResultSet rs, int i) throws SQLException {
        DemoDTO demoDTO = new DemoDTO();
        demoDTO.setId(rs.getString("id"));
        demoDTO.setName(rs.getString("name"));
        demoDTO.setAge(rs.getInt("age"));
        return demoDTO;
    }

}

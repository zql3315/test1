package com.infosky.sys.dao;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.infosky.framework.dao.DAO;
import com.infosky.sys.entity.po.Dictionary;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Repository
public interface DictionaryDAO extends DAO<Dictionary, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    public Dictionary findByDatakey(String datakey);
}
package com.infosky.framework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.springframework.data.domain.Sort;

import com.infosky.common.query.jpa.Searchable;
import com.infosky.framework.View;

/**
 * 带分页查询的接口
 * 
 * @author  n004881
 * @version  [版本号, 2015年3月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface PagingService<V extends View, P, K extends Serializable> extends CrudService<V, K> {

    V find(Searchable s);

    Collection<V> findAll(Searchable s);

    Collection<V> findAll(Searchable s, P page);

    Collection<V> findAll(Searchable s, P page, Sort sort);

    V doConvert(V entity, Map<String, Object> extInfo);

}

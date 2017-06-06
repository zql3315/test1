package com.infosky.framework.service;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.data.domain.Sort;

import com.infosky.common.query.jpa.Searchable;
import com.infosky.framework.View;

/**
 * 增删改查基础接口
 * 
 * @author  n004881
 * @version  [版本号, 2015年3月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface CrudService<V extends View, K extends Serializable> extends Service<V, K> {

    V save(V entity);

    Collection<V> save(Collection<V> entities);

    void delete(V entity);

    void delete(Searchable s);

    void deleteAll();

    void deleteAll(Collection<V> entities);

    V update(V entity);

    V find(K id);

    Collection<V> findAll();

    Long count();

    boolean exists(K primaryKey);

    // Iterable<V> findAll(Iterable<K> ids);

    Collection<V> findAll(Searchable s, Sort sort);

    void delete(K id);

}

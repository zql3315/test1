package com.infosky.demo.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.infosky.demo.entity.po.Demo;
import com.infosky.framework.dao.DAO;

/**
 * <Demo测试举例> <功能详细描述>
 * 
 * @author xx
 * @version [版本号, xx年xx月xx日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository
public interface DemoDAO extends DAO<Demo, String> {

    public Page<Demo> findByName(String name, Pageable pageable);

    @Query(" from Demo d where d.name = ? and d.age = ?")
    public Page<Demo> findByName2(String name, int age, Pageable pageable);

    @Query(value = " select * from t_demo d where d.name = ? and d.age = ?", nativeQuery = true)
    public List<Demo> findByName3(String name, int age);

    public List<Demo> findByName(String name, Sort sort);

}
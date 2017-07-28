package com.infosky.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.infosky.demo.entity.po.Child;
import com.infosky.framework.dao.DAO;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  xx
 * @version  [版本号, xx年xx月xx日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Repository
public interface ChildDAO extends DAO<Child, String> {

    public List<Child> findByDepartment_Name(String name);
}
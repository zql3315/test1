package com.infosky.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.demo.dao.ChildDAO;
import com.infosky.demo.entity.dto.ChildDTO;
import com.infosky.demo.entity.po.Child;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author xx
 * @version [版本号, xx年xx月xx日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Transactional
public class ChildService extends JpaService<Child, ChildDTO, PageResult<ChildDTO>, String> {

    @Autowired
    private ChildDAO dao;

    public void testSpecifications() {
        Searchable s = new SearchRequest();
        s.addSearchParam("department.id", Operator.EQ, "2");
        List<ChildDTO> list = (List<ChildDTO>) this.findAll(s);
        System.out.println(list);

    }

    public List<Child> findByDepartment_Name(String name) {
        return dao.findByDepartment_Name(name);
    }

    /** {@inheritDoc} */

    @Override
    protected DAO<Child, String> getDAO() {
        return dao;
    }
}

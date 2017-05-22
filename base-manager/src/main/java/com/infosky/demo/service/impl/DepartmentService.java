package com.infosky.demo.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.demo.dao.DepartmentDAO;
import com.infosky.demo.entity.dto.ChildDTO;
import com.infosky.demo.entity.dto.DepartmentDTO;
import com.infosky.demo.entity.po.Department;
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
public class DepartmentService extends JpaService<Department, DepartmentDTO, PageResult<DepartmentDTO>, String> {

    @Autowired
    private DepartmentDAO dao;

    public void testSpecifications() {
        Searchable s = new SearchRequest();
        s.addSearchParam("id", Operator.EQ, "1");
        List<DepartmentDTO> list = (List<DepartmentDTO>) this.findAll(s);
        System.out.println(list);

    }

    public void testAdd() {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setAge(111);
        departmentDTO.setName("222");

        ChildDTO child = new ChildDTO();
        child.setId("22");
        Set<ChildDTO> set = Sets.newHashSet();
        set.add(child);
        departmentDTO.setChild(set);

        this.save(departmentDTO);

    }

    public void testDel() {
        Searchable s = new SearchRequest();
        s.addSearchParam("id", Operator.EQ, "2");
        try {
            this.delete(this.find(s));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /** {@inheritDoc} */

    @Override
    protected DAO<Department, String> getDAO() {
        return dao;
    }
}

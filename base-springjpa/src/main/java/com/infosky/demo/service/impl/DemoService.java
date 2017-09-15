package com.infosky.demo.service.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.json.JSONArray;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.demo.dao.DemoDAO;
import com.infosky.demo.entity.dto.DemoDTO;
import com.infosky.demo.entity.po.Demo;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  xx
 * @version  [版本号, xx年xx月xx日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
@Transactional
public class DemoService extends JpaService<Demo, DemoDTO, PageResult<DemoDTO>, String> {

    @Autowired
    private DemoDAO dao;

    @PersistenceContext
    private EntityManager em;

    public void testSql() {
        String sql = "select d from Demo d";
        Query query = em.createQuery(sql);
        List<Demo> list = query.getResultList();
        System.out.println(JSONArray.fromObject(list));

    }

    public void testNativeSql() {
        String sql = "select id,age,name from t_demo";
        Query query = em.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(DemoDTO.class));
        List<DemoDTO> list = query.getResultList();
        System.out.println(JSONArray.fromObject(list));

    }

    public void testNativeSql1() {
        String sql = "select id,age,name from t_demo";
        Query query = em.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.getResultList();
        System.out.println(JSONArray.fromObject(list));

    }

    public void testSpecifications() {
        Searchable s = new SearchRequest();
        //      s.addSearchParam("name", Operator.EQ, "Hello");
        //      s.addSearchParam("age", Operator.EQ, 1111);
        s.addSearchParam("age", Operator.IN, new Integer[] {
                1111, 999
        });
        //      s.addSearchParam("id", Operator.EQ, "8a94d1b753c0ecab0153c0ed893f0000");
        List<DemoDTO> list = (List<DemoDTO>) this.findAll(s);
        System.out.println(JSONArray.fromObject(list));

    }

    public Page<Demo> findByName(String name, PageResult<Demo> page) {
        Pageable pageable = new PageRequest((int) (page.getStart() / page.getLength()), page.getLength());
        Page<Demo> dataPage = dao.findByName(name, pageable);
        return dataPage;
    }

    public Page<Demo> findByName2(String name, int age, PageResult<Demo> page) {
        Pageable pageable = new PageRequest((int) (page.getStart() / page.getLength()), page.getLength());
        Page<Demo> dataPage = dao.findByName2(name, age, pageable);
        return dataPage;
    }

    public List<Demo> findByName3(String name, int age, PageResult<Demo> page) {
        List<Demo> dataPage = dao.findByName3(name, age);
        return dataPage;
    }

    public List<Demo> findByName(String name, Sort sort) {
        return dao.findByName(name, sort);
    }

    /** {@inheritDoc} */

    @Override
    protected DAO<Demo, String> getDAO() {
        return dao;
    }
}

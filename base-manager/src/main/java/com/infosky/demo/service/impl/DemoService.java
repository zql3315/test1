package com.infosky.demo.service.impl;

import java.util.ArrayList;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.demo.dao.DemoDAO;
import com.infosky.demo.entity.dto.DemoDTO;
import com.infosky.demo.entity.dto.DemoNativeDTO;
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
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(DemoNativeDTO.class));
        List<DemoNativeDTO> list = query.getResultList();
        System.out.println(JSONArray.fromObject(list));

    }

    private List<String> sqlList = new ArrayList<String>();

    {
        sqlList.add("CREATE TABLE if not exists t_xfw_rights (\n" + "  id INT not null  AUTO_INCREMENT,\n" + "  rightName varchar(45) DEFAULT NULL,\n" + "  iconCls varchar(45) DEFAULT NULL,\n"
                + "  orderIndex INT DEFAULT NULL,\n" + "  fartherId INT DEFAULT 0,\n" + "  urlPath varchar(45) DEFAULT NULL,\n" + "  rightType INT DEFAULT NULL,\n" + "  PRIMARY KEY (id) \n" + ")\n");

        sqlList.add("create table if not exists t_xfw_operator(\n" + "  id INT not null AUTO_INCREMENT,\n" + " userName varchar(45) default null,\n" + " passWord varchar(45) default null,\n"
                + " userType int default null,\n" + " PRIMARY KEY (id),\n" + " UNIQUE KEY UK_username (userName)\n" + ")\n");

        sqlList.add("Create table if not exists t_xfw_operator_rights(\n" + " user_id INT(20),\n " + " right_id INT(20),\n " + " PRIMARY KEY (user_id,right_id),\n"
                + " KEY FK_right_id (`right_id`),\n" + " CONSTRAINT FK_user_id foreign key(user_id) references t_xfw_operator(id),\n"
                + " CONSTRAINT FK_right_id foreign key(right_id) references t_xfw_rights(id)\n" + ")\n");
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
        //    	s.addSearchParam("name", Operator.EQ, "Hello");
        //    	s.addSearchParam("age", Operator.EQ, 1111);
        s.addSearchParam("age", Operator.IN, new Integer[] {
                1111, 999
        });
        //    	s.addSearchParam("id", Operator.EQ, "8a94d1b753c0ecab0153c0ed893f0000");
        List<DemoDTO> list = (List<DemoDTO>) this.findAll(s);
        System.out.println(JSONArray.fromObject(list));

    }

    public Page<Demo> findByName(String name, Pageable pageable) {
        return dao.findByName(name, pageable);
    }

    public List<Demo> findByName(String name, Sort sort) {
        return dao.findByName(name, sort);
    }

    @Async
    public void asyncMethod() {
        try {
            //让程序暂停100秒，相当于执行一个很耗时的任务
            Thread.sleep(5000);
            System.out.println("=================");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */

    @Override
    protected DAO<Demo, String> getDAO() {
        return dao;
    }
}

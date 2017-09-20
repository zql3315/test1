package com.infosky.adv.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.adv.dao.AdverManagerDAO;
import com.infosky.adv.entity.dto.AdverManagerDTO;
import com.infosky.adv.entity.po.AdverManager;
import com.infosky.common.query.jpa.Request;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;

/**
 * 广告位管理业务层
 * 
 * @author  n003588
 */
@Service
@Transactional
public class AdverManagerService extends JpaService<AdverManager, AdverManagerDTO, PageResult<AdverManagerDTO>, String> {

    @Autowired
    private AdverManagerDAO dao;

    @PersistenceContext
    private EntityManager em;

    /**
     * 分页对象转换为SPRINGMVC分页对象并且支持排序
     * 
     * @param s
     *            查询字段以及策略
     * @param page
     *            分页对象
     * @param sort
     *            排序对象
     * @return
     */
    public Collection<AdverManagerDTO> findAll(Searchable s, PageResult<AdverManagerDTO> page, Sort sort) {

        List<AdverManagerDTO> list = null;
        StringBuffer sql = new StringBuffer("select * from (");
        sql.append("select t1.*,t2.itemvalue as advtypename,t3.itemvalue as advpositionname from adver_manager t1, sys_dictionary t2,sys_dictionary t3 ");
        sql.append(" where  t1.advtype=t2.itemcode and t2.datakey='dd_adv_type' and  t1.advposition=t3.itemcode and t3.datakey='dd_adv_advposition' ");
        sql.append(" ) v_ where 1=1 ");
        StringBuffer countsql = new StringBuffer("select count(1) num from ( ").append(sql);
        List<Request> params = s.getConditions();
        DynamicSearchUtils.toDynamicSql(params, "v_.", countsql);
        countsql.append(" ) c_");
        Query query = em.createNativeQuery(countsql.toString());
        DynamicSearchUtils.toDynamicQuery(query, params);
        Object count = query.getSingleResult();
        int num = Integer.parseInt(String.valueOf(count));
        if (num > 0 && num > page.getStart()) {
            DynamicSearchUtils.toDynamicSql(params, "v_.", sql);
            if (sort != null) {
                Iterator<Order> orders = sort.iterator();
                List<String> orderByStr = new ArrayList<String>();
                while (orders.hasNext()) {
                    Order order = orders.next();
                    orderByStr.add(order.getProperty() + " " + order.getDirection().name());
                    //                    sql.append(order.getProperty()).append(" ").append(order.getDirection().name());
                }
                if (orderByStr.size() > 0) {
                    sql.append(" order by ");
                    sql.append(StringUtils.join(orderByStr, ","));

                }
            }
            query = em.createNativeQuery(sql.toString());
            DynamicSearchUtils.toDynamicQuery(query, params);
            query.setFirstResult((int) page.getStart());
            query.setMaxResults(page.getLength());
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(AdverManagerDTO.class));
            list = query.getResultList();

            page.setRecordsTotal(num);

            page.setData(list);
        }

        return list;
    }

    @Override
    protected DAO<AdverManager, String> getDAO() {
        return dao;
    }

}

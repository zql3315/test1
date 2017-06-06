package com.infosky.framework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.common.mapper.BeanMapper;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.common.util.ReflectUtils;
import com.infosky.framework.View;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.entity.po.PO;
import com.infosky.framework.web.PageResult;

/**
 * 结合JPA数据访问接口提供服务
 * 
 * @author n004881
 * @version [版本号, 2015年3月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Transactional
public abstract class JpaService<P extends PO<K>, D extends View, PR extends PageResult<D>, K extends Serializable> implements PagingService<D, PR, K> {

    protected abstract DAO<P, K> getDAO();

    /** {@inheritDoc} */

    @Override
    public D save(D entity) {
        Class<P> poClass = ReflectUtils.findParameterizedType(getClass(), 0);
        Class<D> dtoClass = ReflectUtils.findParameterizedType(getClass(), 1);

        P po = (P) BeanMapper.map(entity, poClass);
        po = this.getDAO().save(po);

        return BeanMapper.map(po, dtoClass);
    }

    /** {@inheritDoc} */

    @Override
    public Collection<D> save(Collection<D> entities) {
        Class<P> poClass = ReflectUtils.findParameterizedType(getClass(), 0);
        Class<D> dtoClass = ReflectUtils.findParameterizedType(getClass(), 1);

        Collection<P> ps = BeanMapper.mapList(entities, poClass);
        ps = this.getDAO().save(ps);

        return BeanMapper.mapList(ps, dtoClass);
    }

    /** {@inheritDoc} */

    @Override
    public void delete(D entity) {
        Class<P> poClass = ReflectUtils.findParameterizedType(getClass(), 0);
        P po = (P) BeanMapper.map(entity, poClass);
        this.getDAO().delete(po);
    }

    /** {@inheritDoc} */

    @Override
    public void delete(K id) {
        this.getDAO().delete(id);
    }

    /** {@inheritDoc} */

    @Override
    public void delete(Searchable s) {
        Specification<P> specification = DynamicSearchUtils.toSpecification(s);

        Collection<P> d = this.getDAO().findAll(specification);
        this.getDAO().deleteInBatch(d);
    }

    /** {@inheritDoc} */

    @Override
    public void deleteAll() {
        this.getDAO().deleteAll();
    }

    @Override
    public void deleteAll(Collection<D> entities) {
        Class<P> poClass = ReflectUtils.findParameterizedType(getClass(), 0);
        Collection<P> ps = BeanMapper.mapList(entities, poClass);
        this.getDAO().deleteInBatch(ps);
    }

    /** {@inheritDoc} */

    @Override
    public D update(D entity) {
        Class<P> poClass = ReflectUtils.findParameterizedType(getClass(), 0);
        Class<D> dtoClass = ReflectUtils.findParameterizedType(getClass(), 1);

        P po = (P) BeanMapper.map(entity, poClass);
        po = this.getDAO().save(po);

        return BeanMapper.map(po, dtoClass);
    }

    /**
     * 根据主键查询对象
     * 
     * @id 主键
     * @return
     */

    @Override
    public D find(K id) {
        Class<D> dtoClass = ReflectUtils.findParameterizedType(getClass(), 1);

        P po = this.getDAO().findOne(id);

        return BeanMapper.map(po, dtoClass);
    }

    /**
     * 查询满足条件的所有对象切排序
     * 
     * @param s
     *            查询字段以及策略
     * @param sort
     *            排序对象
     * @return
     */
    public Collection<D> findAllBySort(Searchable s, Sort sort) {
        Class<D> dtoClass = ReflectUtils.findParameterizedType(getClass(), 1);
        Specification<P> specification = DynamicSearchUtils.toSpecification(s);
        Collection<P> ps = this.getDAO().findAll(specification, sort);
        List<D> dtoList = (List<D>) BeanMapper.mapList(ps, dtoClass);
        return dtoList;
    }

    /**
     * 查询所有对象
     * 
     * @return
     */

    @Override
    public Collection<D> findAll() {
        Class<D> dtoClass = ReflectUtils.findParameterizedType(getClass(), 1);

        Collection<P> ps = this.getDAO().findAll();

        return BeanMapper.mapList(ps, dtoClass);
    }

    /**
     * 查询满足条件的第一个对象
     * 
     * @param s
     *            查询字段以及策略
     * @return
     */

    @Override
    public D find(Searchable s) {
        Class<D> dtoClass = ReflectUtils.findParameterizedType(getClass(), 1);

        Specification<P> specification = DynamicSearchUtils.toSpecification(s);
        P p = this.getDAO().findOne(specification);
        return BeanMapper.map(p, dtoClass);
    }

    /**
     * 查询满足条件的所有对象
     * 
     * @param s
     *            查询字段以及策略
     * @return
     */

    @Override
    public Collection<D> findAll(Searchable s) {
        Class<D> dtoClass = ReflectUtils.findParameterizedType(getClass(), 1);
        Specification<P> specification = DynamicSearchUtils.toSpecification(s);

        Collection<P> ps = this.getDAO().findAll(specification);

        return BeanMapper.mapList(ps, dtoClass);
    }

    /**
     * 查询满足条件的所有对象
     * 
     * @param s
     *            查询字段以及策略
     * @return
     */

    @Override
    public Collection<D> findAll(Searchable s, Sort sort) {
        Class<D> dtoClass = ReflectUtils.findParameterizedType(getClass(), 1);
        Specification<P> specification = DynamicSearchUtils.toSpecification(s);

        Collection<P> ps = this.getDAO().findAll(specification, sort);

        return BeanMapper.mapList(ps, dtoClass);
    }

    /**
     * 分页对象转换为SPRINGMVC分页对象
     * 
     * @param s
     *            查询字段以及策略
     * @param page
     *            分页对象
     * @return
     */

    @Override
    public Collection<D> findAll(Searchable s, PR page) {

        return findAll(s, page, null);
    }

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
    public Collection<D> findAll(Searchable s, PR page, Sort sort) {
        Class<D> dtoClass = ReflectUtils.findParameterizedType(getClass(), 1);

        //
        Pageable pageable = new PageRequest((int) (page.getStart() / page.getLength()), page.getLength(), sort);

        Specification<P> specification = DynamicSearchUtils.toSpecification(s);

        Page<P> dataPage = this.getDAO().findAll(specification, pageable);

        page.setRecordsTotal(dataPage.getTotalElements());

        List<D> dtoList = (List<D>) BeanMapper.mapList(dataPage.getContent(), dtoClass);

        page.setData(dtoList);

        return dtoList;
    }

    /**
     * 返回的实体的数量
     */
    @Override
    public Long count() {
        return this.getDAO().count();
    }

    /**
     * 表示一个实体是否与给定id的存在。
     */
    @Override
    public boolean exists(K primaryKey) {
        return this.getDAO().exists(primaryKey);
    }

    @Override
    public D doConvert(D entity, Map<String, Object> extInfo) {

        return entity;
    }

}

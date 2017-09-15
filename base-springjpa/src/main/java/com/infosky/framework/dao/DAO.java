package com.infosky.framework.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.infosky.framework.entity.po.PO;

/**
 * 数据访问接口
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DAO<P extends PO<I>, I extends Serializable> extends JpaRepository<P, I>, JpaSpecificationExecutor<P> {

}

package com.infosky.task.dao;

import org.springframework.stereotype.Repository;

import com.infosky.framework.dao.DAO;
import com.infosky.task.entity.po.Task;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Repository
public interface TaskDAO extends DAO<Task, String> {

}
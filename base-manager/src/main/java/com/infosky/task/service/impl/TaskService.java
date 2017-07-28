package com.infosky.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.task.dao.TaskDAO;
import com.infosky.task.entity.dto.TaskDTO;
import com.infosky.task.entity.po.Task;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
@Transactional
public class TaskService extends JpaService<Task, TaskDTO, PageResult<TaskDTO>, String> {

    @Autowired
    private TaskDAO dao;

    /** {@inheritDoc} */

    @Override
    protected DAO<Task, String> getDAO() {
        return dao;
    }
}

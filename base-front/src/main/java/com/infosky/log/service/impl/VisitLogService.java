package com.infosky.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.log.dao.VisitLogDAO;
import com.infosky.log.entity.dto.VisitLogDTO;
import com.infosky.log.entity.po.VisitLog;

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
public class VisitLogService extends JpaService<VisitLog, VisitLogDTO, PageResult<VisitLogDTO>, String> {

    @Autowired
    private VisitLogDAO dao;

    /** {@inheritDoc} */

    @Override
    protected DAO<VisitLog, String> getDAO() {
        return dao;
    }
}

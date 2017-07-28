package com.infosky.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.dao.OperationDAO;
import com.infosky.sys.entity.dto.OperationDTO;
import com.infosky.sys.entity.po.Operation;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 */
@Service
@Transactional
public class OperationService extends JpaService<Operation, OperationDTO, PageResult<OperationDTO>, String> {

    @Autowired
    private OperationDAO dao;

    /** {@inheritDoc} */

    @Override
    protected DAO<Operation, String> getDAO() {
        return dao;
    }
}

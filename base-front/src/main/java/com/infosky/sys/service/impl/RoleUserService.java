package com.infosky.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.dao.RoleUserDAO;
import com.infosky.sys.entity.dto.RoleUserDTO;
import com.infosky.sys.entity.po.RoleUser;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * @author  zan
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
@Transactional
public class RoleUserService extends JpaService<RoleUser, RoleUserDTO, PageResult<RoleUserDTO>, String> {

    @Autowired
    private RoleUserDAO dao;

    public RoleUser getRoleUser(String userId) {
        return dao.getRoleUser(userId);
    }

    /** {@inheritDoc} */

    @Override
    protected DAO<RoleUser, String> getDAO() {
        return dao;
    }
}

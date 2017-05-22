package com.infosky.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.dao.RoleDAO;
import com.infosky.sys.dao.RoleUserDAO;
import com.infosky.sys.entity.dto.RoleDTO;
import com.infosky.sys.entity.po.Role;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zan
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
@Transactional
public class RoleService extends JpaService<Role, RoleDTO, PageResult<RoleDTO>, String> {

    @Autowired
    private RoleDAO dao;

    @Autowired
    private RoleUserDAO roleUserDAO;

    public int getRoleCount(String roleName, String id) {
        if (id == null) {
            return dao.getRoleCount(roleName);
        }
        return dao.getRoleCount(roleName, id);
    }

    public void updateRole(RoleDTO roleDTO) {
        String id = roleDTO.getId();
        String name = roleDTO.getName();
        String sn = roleDTO.getSn();
        String description = roleDTO.getDescription();
        String creator = roleDTO.getCreator();

        dao.updateRole(id, name, sn, description, creator);
    }

    /**
     * 删除角色
     * @param roleId
     */
    public int findRoleCount(RoleDTO roleDTO) {
        //删除roleUser
        return roleUserDAO.findByRoleUser(roleDTO.getId());
    }

    /** {@inheritDoc} */

    @Override
    protected DAO<Role, String> getDAO() {
        return dao;
    }
}

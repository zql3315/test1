package com.infosky.sys.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.infosky.framework.dao.DAO;
import com.infosky.sys.entity.po.RoleUser;

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
public interface RoleUserDAO extends DAO<RoleUser, String> {

    @Query(" from RoleUser t where t.user.id = ?1 ")
    public RoleUser getRoleUser(String userId);

    @Query(" select count(*) from RoleUser t where t.role.id = ?1 ")
    public int findByRoleUser(String roleId);
}
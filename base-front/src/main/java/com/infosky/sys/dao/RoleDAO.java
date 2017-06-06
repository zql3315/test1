package com.infosky.sys.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.infosky.framework.dao.DAO;
import com.infosky.sys.entity.po.Role;

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
public interface RoleDAO extends DAO<Role, String> {

    @Query("select count(*) from Role t where t.name = ?1 and t.id <> ?2")
    public int getRoleCount(String name, String id);

    @Query("select count(*) from Role t where t.name = ?1")
    public int getRoleCount(String name);

    @Modifying
    @Query("update Role set name=?2, sn=?3, description=?4, creator=?5 where id=?1")
    public void updateRole(String id, String name, String sn, String description, String creator);
}
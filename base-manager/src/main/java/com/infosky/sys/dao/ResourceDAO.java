package com.infosky.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.infosky.framework.dao.DAO;
import com.infosky.sys.entity.po.Permission;
import com.infosky.sys.entity.po.Resource;

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
public interface ResourceDAO extends DAO<Resource, String> {

    @Query("select t.resource from Permission t where t.resource.type = '2' and t.role.id = ?2 and t.resource.id = ?1")
    public List<Resource> findByResourceId(String reid, String roleId);

    @Query("from Permission t where role.id=?1 and t.resource.type='2' and t.resource.parent.id=?2 order by t.resource.priority ASC")
    public List<Permission> findByRoleId(String roleId, String pid);

    @Query("from Permission t where role.id=?1 ")
    public List<Permission> findByRoleId(String roleId);

    @Query("from Resource t where t.type in('1', '2') and t.parent.id = '0' order by t.priority ASC")
    public List<Resource> findParentResource();

    @Modifying
    @Query("delete from Resource where id=?1")
    public void deleteByReId(String id);

}
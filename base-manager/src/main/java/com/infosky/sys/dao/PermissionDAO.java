package com.infosky.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.infosky.framework.dao.DAO;
import com.infosky.sys.entity.po.Operation;
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
public interface PermissionDAO extends DAO<Permission, String> {

    @Query("select distinct t.resource from Permission t where t.role.id = ?1")
    public List<Resource> getResource(String rid);

    @Query("select distinct t.operation from Permission t where t.role.id = ?1")
    public List<Operation> getOperation(String rid);

    @Modifying
    @Query("delete from Permission where role.id=?1")
    public void deleteByRoleId(String id);

    @Modifying
    @Query("delete from Permission where id=?1")
    public void deleteResourceById(String id);

}
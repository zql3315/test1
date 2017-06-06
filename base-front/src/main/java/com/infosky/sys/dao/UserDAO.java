package com.infosky.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.infosky.framework.dao.DAO;
import com.infosky.sys.entity.po.User;

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
public interface UserDAO extends DAO<User, String> {

    @Query("from User a where a.id = :id")
    public User findById(@Param("id") String Id);

    @Query("select u from User u where u.loginname = :names or u.name = :names")
    public User findByLoginnameOrName(@Param("names") String names);

    @Query("from User where loginname = ?1")
    public User findByLoginname(String loginname);

    public List<User> findByLoginnameIgnoreCase(String loginname);

    public List<User> findByLoginnameAndNameAllIgnoreCase(String loginname, String name);

    public Long countByLoginname(String loginname);

    @Query("select id from User where loginname=?1")
    public int getID(String name);

    @Query("select count(*) from User where loginname = ?1")
    public int findLoginnameCount(String loginname);

    @Query("select count(*) from User where loginname = ?1 and id <>?2")
    public int findLoginnameCount(String loginname, String id);

    //	 public Long deleteByLoginname(String loginname);

    //	 public List<User> removeByLoginname(String loginname);

}
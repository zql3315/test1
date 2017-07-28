package com.infosky.wechat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.infosky.framework.dao.DAO;
import com.infosky.wechat.entity.po.CustomMenu;

@Repository
public interface CustomMenuDAO extends DAO<CustomMenu, String> {

    @Query("select c from CustomMenu c where c.parent.id = ?1 order by orderNum asc")
    public List<CustomMenu> findByPid(String pid);

    @Query("select c from CustomMenu c where c.parent.id is Null order by orderNum asc")
    public List<CustomMenu> queryFirstMenus();

    @Modifying
    @Query("delete from CustomMenu where id=?1")
    public void deleteById(String id);

}
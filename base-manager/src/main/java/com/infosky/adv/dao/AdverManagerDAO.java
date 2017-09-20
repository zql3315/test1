package com.infosky.adv.dao;

import org.springframework.stereotype.Repository;

import com.infosky.adv.entity.po.AdverManager;
import com.infosky.framework.dao.DAO;

/**
 * 广告位管理数据访问层
 * 
 * @author  n003588
 */
@Repository
public interface AdverManagerDAO extends DAO<AdverManager, String>
{

}
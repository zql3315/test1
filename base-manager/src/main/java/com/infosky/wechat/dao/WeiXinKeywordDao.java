package com.infosky.wechat.dao;

import org.springframework.stereotype.Repository;

import com.infosky.framework.dao.DAO;
import com.infosky.wechat.entity.po.WeiXinKeyword;

@Repository
public interface WeiXinKeywordDao extends DAO<WeiXinKeyword, String> {

}

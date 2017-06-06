package com.infosky.wechat.dao;

import org.springframework.stereotype.Repository;

import com.infosky.framework.dao.DAO;
import com.infosky.wechat.entity.po.WeiXinPublicAccount;

@Repository
public interface WeiXinPublicAccountDao extends DAO<WeiXinPublicAccount, String> {

}

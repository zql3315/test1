package com.infosky.wechat.dao;

import org.springframework.stereotype.Repository;

import com.infosky.framework.dao.DAO;
import com.infosky.wechat.entity.po.WeiXinNotifyOrder;

@Repository
public interface WeiXinNotifyOrderDao extends DAO<WeiXinNotifyOrder, String> {

}

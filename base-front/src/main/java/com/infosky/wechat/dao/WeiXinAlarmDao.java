package com.infosky.wechat.dao;

import org.springframework.stereotype.Repository;

import com.infosky.framework.dao.DAO;
import com.infosky.wechat.entity.po.WeiXinAlarm;

@Repository
public interface WeiXinAlarmDao extends DAO<WeiXinAlarm, String> {

}

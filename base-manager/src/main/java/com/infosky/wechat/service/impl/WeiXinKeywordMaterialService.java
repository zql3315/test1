package com.infosky.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.wechat.dao.WeiXinKeywordMaterialDao;
import com.infosky.wechat.entity.dto.WeiXinKeywordMaterialDTO;
import com.infosky.wechat.entity.po.WeiXinKeywordMaterial;

/**
 * 微信关键词素材业务层
 * 
 * @author  n930177
 */
@Service
@Transactional
public class WeiXinKeywordMaterialService extends JpaService<WeiXinKeywordMaterial, WeiXinKeywordMaterialDTO, PageResult<WeiXinKeywordMaterialDTO>, String> {

    @Autowired
    private WeiXinKeywordMaterialDao dao;

    @Override
    protected DAO<WeiXinKeywordMaterial, String> getDAO() {
        return dao;
    }
}

package com.infosky.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.wechat.dao.WeiXinMaterialDao;
import com.infosky.wechat.entity.dto.WeiXinMaterialDTO;
import com.infosky.wechat.entity.po.WeiXinMaterial;

/**
 * 素材-图文消息
 * @author n004883
 */
@Service
@Transactional
public class WeiXinMaterialService extends JpaService<WeiXinMaterial, WeiXinMaterialDTO, PageResult<WeiXinMaterialDTO>, String> {

    @Autowired
    private WeiXinMaterialDao dao;

    @Override
    protected DAO<WeiXinMaterial, String> getDAO() {
        return dao;
    }
}

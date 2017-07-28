package com.infosky.wechat.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.common.mapper.BeanMapper;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.wechat.dao.WeiXinMaterialDao;
import com.infosky.wechat.dao.WeiXinMaterialMessageDao;
import com.infosky.wechat.entity.dto.WeiXinMaterialMessageDTO;
import com.infosky.wechat.entity.po.WeiXinMaterial;
import com.infosky.wechat.entity.po.WeiXinMaterialMessage;

/**
 * 素材-图文消息
 * @author n004883
 */
@Service
@Transactional
public class WeiXinMaterialMessageService extends JpaService<WeiXinMaterialMessage, WeiXinMaterialMessageDTO, PageResult<WeiXinMaterialMessageDTO>, String> {

    @Autowired
    private WeiXinMaterialMessageDao dao;

    @Autowired
    private WeiXinMaterialDao weiXinMaterialDao;

    /**
     * 新增图文消息
     */
    public WeiXinMaterialMessageDTO save(WeiXinMaterialMessageDTO t) {
        WeiXinMaterialMessage weiXinMaterialMessage = BeanMapper.map(t, WeiXinMaterialMessage.class);

        if (t.getWeiXinMaterial() != null && StringUtils.isNotBlank(t.getWeiXinMaterial().getId())) {
            WeiXinMaterial weiXinMaterial = weiXinMaterialDao.findOne(t.getWeiXinMaterial().getId());
            weiXinMaterialMessage.setWeiXinMaterial(weiXinMaterial);
        }

        weiXinMaterialMessage = this.getDAO().save(weiXinMaterialMessage);

        return BeanMapper.map(weiXinMaterialMessage, WeiXinMaterialMessageDTO.class);
    }

    /**
     * 编辑图文消息
     */
    public WeiXinMaterialMessageDTO update(WeiXinMaterialMessageDTO t) {
        WeiXinMaterialMessage weiXinMaterialMessage = BeanMapper.map(t, WeiXinMaterialMessage.class);

        WeiXinMaterialMessage po = this.getDAO().findOne(t.getId());

        if (po != null) {
            WeiXinMaterial weiXinMaterial = po.getWeiXinMaterial();
            if (weiXinMaterial != null) {
                weiXinMaterialMessage.setWeiXinMaterial(weiXinMaterial);
            }
        }

        weiXinMaterialMessage = this.getDAO().save(weiXinMaterialMessage);

        return BeanMapper.map(weiXinMaterialMessage, WeiXinMaterialMessageDTO.class);

    }

    @Override
    protected DAO<WeiXinMaterialMessage, String> getDAO() {
        return dao;
    }
}

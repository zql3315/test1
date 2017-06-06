package com.infosky.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.common.mapper.BeanMapper;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.wechat.dao.WeiXinKeywordDao;
import com.infosky.wechat.dao.WeiXinKeywordMaterialDao;
import com.infosky.wechat.dao.WeiXinMaterialMessageDao;
import com.infosky.wechat.dao.WeiXinMessageDao;
import com.infosky.wechat.entity.dto.WeiXinKeywordDTO;
import com.infosky.wechat.entity.dto.WeiXinKeywordMaterialDTO;
import com.infosky.wechat.entity.dto.WeiXinMaterialDTO;
import com.infosky.wechat.entity.po.WeiXinKeyword;
import com.infosky.wechat.entity.po.WeiXinKeywordMaterial;
import com.infosky.wechat.entity.po.WeiXinMaterial;
import com.infosky.wechat.entity.po.WeiXinMaterialMessage;
import com.infosky.wechat.entity.po.WeiXinMessage;

/**
 * 微信关键词业务层
 * 
 * @author  n930177
 */
@Service
@Transactional
public class WeiXinKeywordService extends JpaService<WeiXinKeyword, WeiXinKeywordDTO, PageResult<WeiXinKeywordDTO>, String> {

    @Autowired
    private WeiXinKeywordDao weiXinKeywordDao;

    @Autowired
    private WeiXinKeywordMaterialDao weiXinKeywordMaterialDao;

    @Autowired
    private WeiXinMaterialMessageDao weiXinMaterialMessageDao;

    @Autowired
    private WeiXinMessageDao weiXinMessageDao;

    /**
     * 新增关键词
     * @param b 关键词对象
     */
    public void addWeiXinKeyword(WeiXinKeywordDTO b) {
        try {
            //1.添加关键词
            b.setDescription("图文消息");

            List<WeiXinKeywordMaterialDTO> weiXinKeywordMaterialLst = b.getWeiXinKeywordMaterials();

            if (weiXinKeywordMaterialLst != null && weiXinKeywordMaterialLst.size() > 0) {
                b.setMsgcount(weiXinKeywordMaterialLst.size());
            }

            WeiXinKeyword weiXinKeyword = weiXinKeywordDao.save(BeanMapper.map(b, WeiXinKeyword.class));

            for (WeiXinKeywordMaterialDTO weiXinKeywordMaterialDTO : weiXinKeywordMaterialLst) {
                WeiXinMaterialDTO weiXinMaterialDTO = weiXinKeywordMaterialDTO.getWeiXinMaterial();
                WeiXinMaterial weiXinMaterial = BeanMapper.map(weiXinMaterialDTO, WeiXinMaterial.class);

                if (weiXinMaterial != null) {
                    //查询图文消息
                    WeiXinMaterialMessage materialMessage = weiXinMaterialMessageDao.findOne(weiXinMaterial.getId());

                    if (materialMessage != null) {
                        //查询素材
                        weiXinMaterial = materialMessage.getWeiXinMaterial();

                        //2.添加关键词所对应的图文消息
                        WeiXinMessage weiXinMessage = new WeiXinMessage();
                        weiXinMessage.setTitle(materialMessage.getTitle());
                        weiXinMessage.setPicurl(materialMessage.getPicurl());
                        weiXinMessage.setLinkurl(materialMessage.getContent_source_url());
                        weiXinMessage.setDescription(materialMessage.getDigest());
                        weiXinMessage.setWeiXinKeyword(weiXinKeyword);
                        weiXinMessageDao.save(weiXinMessage);
                    }
                }

                //3.添加关键词与素材关联关系
                WeiXinKeywordMaterial po = new WeiXinKeywordMaterial();
                po.setWeiXinKeyword(weiXinKeyword);
                po.setWeiXinMaterial(weiXinMaterial);
                weiXinKeywordMaterialDao.save(po);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除关键词
     * @param b
     */
    public void deleteKeyword(List<WeiXinKeywordDTO> b) {
        try {
            List<WeiXinKeyword> weiXinKeywordLst = BeanMapper.mapList(b, WeiXinKeyword.class);

            for (WeiXinKeyword weiXinKeyword : weiXinKeywordLst) {
                //1.删除关键词与素材关联关系
                Searchable s = new SearchRequest();
                s.addSearchParam("weiXinKeyword.id", Operator.EQ, weiXinKeyword.getId());
                Specification<WeiXinKeywordMaterial> sp = DynamicSearchUtils.toSpecification(s);
                List<WeiXinKeywordMaterial> weiXinKeywordMaterialLst = (List<WeiXinKeywordMaterial>) weiXinKeywordMaterialDao.findAll(sp);

                for (WeiXinKeywordMaterial weiXinKeywordMaterial : weiXinKeywordMaterialLst) {
                    weiXinKeywordMaterialDao.delete(weiXinKeywordMaterial);
                }

                //2.刪除关键词关联的图文消息
                Searchable ss = new SearchRequest();
                ss.addSearchParam("weiXinKeyword.id", Operator.EQ, weiXinKeyword.getId());
                Specification<WeiXinMessage> spm = DynamicSearchUtils.toSpecification(ss);
                List<WeiXinMessage> weiXinMessageLst = (List<WeiXinMessage>) weiXinMessageDao.findAll(spm);

                for (WeiXinMessage weiXinMessage : weiXinMessageLst) {
                    weiXinMessageDao.delete(weiXinMessage);
                }

                //3.删除关键词
                weiXinKeywordDao.delete(weiXinKeyword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 编辑关键词
     * @param b
     */
    public void updateKeyword(WeiXinKeywordDTO b) {
        //1.编辑关键词
        b.setDescription("图文消息");

        List<WeiXinKeywordMaterialDTO> weiXinKeywordMaterialLst = b.getWeiXinKeywordMaterials();

        if (weiXinKeywordMaterialLst != null && weiXinKeywordMaterialLst.size() > 0) {
            b.setMsgcount(weiXinKeywordMaterialLst.size());
        }

        WeiXinKeyword weiXinKeyword = weiXinKeywordDao.save(BeanMapper.map(b, WeiXinKeyword.class));

        //2.删除关联关系
        Searchable ss = new SearchRequest();
        ss.addSearchParam("weiXinKeyword.id", Operator.EQ, weiXinKeyword.getId());
        Specification<WeiXinKeywordMaterial> sp = DynamicSearchUtils.toSpecification(ss);
        List<WeiXinKeywordMaterial> keywordMaterialLst = (List<WeiXinKeywordMaterial>) weiXinKeywordMaterialDao.findAll(sp);

        for (WeiXinKeywordMaterial weiXinKeywordMaterial : keywordMaterialLst) {
            weiXinKeywordMaterialDao.delete(weiXinKeywordMaterial);
        }

        //2.1刪除关键词关联的图文消息
        Searchable sm = new SearchRequest();
        sm.addSearchParam("weiXinKeyword.id", Operator.EQ, weiXinKeyword.getId());
        Specification<WeiXinMessage> spm = DynamicSearchUtils.toSpecification(sm);
        List<WeiXinMessage> weiXinMessageLst = (List<WeiXinMessage>) weiXinMessageDao.findAll(spm);

        for (WeiXinMessage weiXinMessage : weiXinMessageLst) {
            weiXinMessageDao.delete(weiXinMessage);
        }

        //3.添加关键词与素材关联关系(关键词关联的图文消息)
        for (WeiXinKeywordMaterialDTO weiXinKeywordMaterialDTO : weiXinKeywordMaterialLst) {
            WeiXinMaterialDTO weiXinMaterialDTO = weiXinKeywordMaterialDTO.getWeiXinMaterial();
            WeiXinMaterial weiXinMaterial = BeanMapper.map(weiXinMaterialDTO, WeiXinMaterial.class);

            if (weiXinMaterial != null) {
                //查询图文消息
                WeiXinMaterialMessage materialMessage = weiXinMaterialMessageDao.findOne(weiXinMaterial.getId());

                if (materialMessage != null) {
                    //查询素材
                    weiXinMaterial = materialMessage.getWeiXinMaterial();

                    //添加关键词所对应的图文消息
                    WeiXinMessage weiXinMessage = new WeiXinMessage();
                    weiXinMessage.setTitle(materialMessage.getTitle());
                    weiXinMessage.setPicurl(materialMessage.getPicurl());
                    weiXinMessage.setLinkurl(materialMessage.getContent_source_url());
                    weiXinMessage.setDescription(materialMessage.getDigest());
                    weiXinMessage.setWeiXinKeyword(weiXinKeyword);
                    weiXinMessageDao.save(weiXinMessage);
                }
            }

            //新增关键词与素材关联关系
            WeiXinKeywordMaterial po = new WeiXinKeywordMaterial();
            po.setWeiXinKeyword(weiXinKeyword);
            po.setWeiXinMaterial(weiXinMaterial);

            weiXinKeywordMaterialDao.save(po);
        }
    }

    /**
     * 查询关键词是否已存在
     * @param keyword 关键词
     * @return boolean
     */
    public boolean isValidateKeyword(WeiXinKeywordDTO weiXinKeywordDTO) {
        Searchable s = new SearchRequest();
        s.addSearchParam("keyword", Operator.EQ, weiXinKeywordDTO.getKeyword());
        Specification<WeiXinKeyword> sp = DynamicSearchUtils.toSpecification(s);
        WeiXinKeyword weiXinKeyword = weiXinKeywordDao.findOne(sp);

        if (weiXinKeyword == null) {
            return false;
        }

        if (weiXinKeyword.getId().equals(weiXinKeywordDTO.getId())) {
            return false;
        }

        return true;

    }

    @Override
    protected DAO<WeiXinKeyword, String> getDAO() {
        return weiXinKeywordDao;
    }
}

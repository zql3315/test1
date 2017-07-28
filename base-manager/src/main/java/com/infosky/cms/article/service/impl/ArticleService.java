package com.infosky.cms.article.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.cms.article.dao.ArticleDAO;
import com.infosky.cms.article.entity.dto.ArticleDTO;
import com.infosky.cms.article.entity.po.Article;
import com.infosky.common.util.FreeMarkerUtils;
import com.infosky.common.util.UUIDUtil;
import com.infosky.common.util.date.DateUtils;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  xx
 * @version  [版本号, xx年xx月xx日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
@Transactional
public class ArticleService extends JpaService<Article, ArticleDTO, PageResult<ArticleDTO>, String> {

    @Autowired
    private ArticleDAO dao;

    /**
     * 发布文章
     * 
     * @param id 文章的id
     */
    public boolean releaseArticle(String id) {

        ArticleDTO articleDTO = this.find(id);
        Map<String, String> aItem = new HashMap<String, String>();
        aItem.put("title", articleDTO.getTitle());
        aItem.put("addtime", DateUtils.date2Str(articleDTO.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        aItem.put("showContent", articleDTO.getContent());
        Map<String, Map<String, String>> root = new HashMap<String, Map<String, String>>();
        root.put("newsitem", aItem);
        String sGeneFilePath = "/tpxw/";
        if (StringUtils.isBlank(articleDTO.getContentId())) {
            articleDTO.setContentId(UUIDUtil.getUUID());
        }
        String sFileName = articleDTO.getContentId() + ".html";
        this.save(articleDTO);
        return FreeMarkerUtils.geneHtmlFile("template.ftl", root, sGeneFilePath, sFileName);

    }

    /** {@inheritDoc} */

    @Override
    protected DAO<Article, String> getDAO() {
        return dao;
    }
}

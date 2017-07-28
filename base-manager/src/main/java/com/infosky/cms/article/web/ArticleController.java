package com.infosky.cms.article.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infosky.cms.article.entity.dto.ArticleDTO;
import com.infosky.cms.article.service.impl.ArticleService;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;

/**
 * 表操作
 * 
 * @author  xx
 * @version  [版本号, xx年xx月xx日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/cms/article")
public class ArticleController extends CrudController<String, PageResult<ArticleDTO>, ArticleDTO> {

    @Autowired
    private ArticleService service;

    /** {@inheritDoc} */
    public PagingService<ArticleDTO, PageResult<ArticleDTO>, String> getService() {
        return service;
    }

    /**
     * 发布文章
     * 
     * @param articleIds
     * @return
     */
    @RequestMapping("release/{id}")
    @ResponseBody
    public String releaseArticle(@PathVariable String id) {

        service.releaseArticle(id);

        return "SUCCESS";
    }

}

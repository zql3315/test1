package com.infosky.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.entity.dto.ResourceDTO;
import com.infosky.sys.service.impl.ResourceService;

/**
 * 表操作
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/resource")
@Description(value = "资源管理")
public class ResourceController extends CrudController<String, PageResult<ResourceDTO>, ResourceDTO> {

    @Autowired
    private ResourceService service;

    @Autowired
    CacheManager cache;

    private static final Logger logger = LoggerFactory.getLogger(CrudController.class);

    /**
     * 列表页面table分页数据Search
     * @param page
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @Description(value = "查询列表数据")
    @Override
    public PageResult<ResourceDTO> list(@ModelAttribute PageResult<ResourceDTO> page, @ModelAttribute("sort") String sorts, HttpServletRequest request) {
        Searchable s = DynamicSearchUtils.toSearchable(request);
        s.addSearchParam("parent.id", Operator.ISNOTNULL, null);
        try {
            if (StringUtils.isNotBlank(sorts)) {
                Sort sort;
                if (JSONObject.fromObject(sorts).getString("sort").toUpperCase().equals("DESC")) {
                    sort = new Sort(Sort.Direction.DESC, JSONObject.fromObject(sorts).getString("fileName").toString());
                } else {
                    sort = new Sort(Sort.Direction.ASC, JSONObject.fromObject(sorts).getString("fileName").toString());
                }
                getService().findAll(s, page, sort);
            } else {
                getService().findAll(s, page);
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        return page;
    }

    @RequestMapping(value = "ztree")
    @ResponseBody
    public List<Map<String, Object>> ztree(HttpServletRequest request) {
        return service.buildTree(request.getParameter("rid"));
    }

    @RequestMapping(value = "edit")
    @ResponseBody
    @Description(value = "更新数据")
    public Map<String, Object> edit(@RequestBody ResourceDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            getService().update(b);
            result.put("result", true);
            if (StringUtils.isNotBlank(b.getUrl())) {
                cache.getCache("urlPermCache").remove(b.getUrl());
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    /**
     * 根据主键删除资源
     * @param b
     * @return
     */
    @RequestMapping(value = "deleteByID")
    @ResponseBody
    @Description(value = "根据主键删除资源")
    public Map<String, Object> delete(@RequestBody List<ResourceDTO> b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        result.put("msg", "系统繁忙");
        try {
            for (ResourceDTO resourceDTO : b) {
                service.deleteReById(resourceDTO.getId());
            }
            result.put("result", true);
        } catch (DataIntegrityViolationException e) {
            result.put("msg", "请先删除子栏目");
        } catch (Exception e) {
            logger.error("", e);
        }
        return result;
    }

    /** {@inheritDoc} */
    public PagingService<ResourceDTO, PageResult<ResourceDTO>, String> getService() {
        return service;
    }

}

package com.infosky.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.entity.dto.AreaDTO;
import com.infosky.sys.service.impl.AreaService;

/**
 * 
 * 区域管理
 * 
 * @author n004881
 */
@Controller
@RequestMapping(value = "/area")
@Description(value = "省市区管理")
public class AreaController extends CrudController<String, PageResult<AreaDTO>, AreaDTO> {

    @Autowired
    private AreaService service;

    private static final Logger logger = LoggerFactory.getLogger(AreaController.class);

    @RequestMapping(value = "/index")
    public String toIndex() {
        return getView("index");
    }

    /**
     * 
     * 热门城市列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/hotCity")
    public String hotIndex() {
        return getView("hotcity");
    }

    /**
     * 
     * 热门城市添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/toAddHotCity")
    public String toAddHotCity() {
        return getView("addhotcity");
    }

    /**
     * 
     * 添加热门城市
     * 
     * @return
     */
    @RequestMapping(value = "/addHotCity")
    @ResponseBody
    public Map<String, Object> addHotCity(@RequestBody AreaDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        try {
            AreaDTO area = service.find(b.getId());
            area.setIsHot(1);
            getService().save(area);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    /**
     * 
     * 添加热门城市
     * 
     * @return
     */
    @RequestMapping(value = "/delHotCity")
    @ResponseBody
    public Map<String, Object> delHotCity(@RequestBody List<AreaDTO> b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        try {
            service.delHotCity(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    /**
     * 
     * 热门城市列表
     * 
     * @param page
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/hotCityList")
    @ResponseBody
    @Description(value = "查询列表数据")
    public PageResult<AreaDTO> list(@ModelAttribute PageResult<AreaDTO> page, @ModelAttribute("sort") String sorts, HttpServletRequest request) {
        Searchable s = DynamicSearchUtils.toSearchable(request);
        s.addSearchParam("level", Operator.EQ, 1);
        s.addSearchParam("isHot", Operator.EQ, 1);
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

    @RequestMapping(value = "add")
    @ResponseBody
    @Description(value = "添加数据")
    public Map<String, Object> add(@RequestBody AreaDTO b) {
        if (b.getParent() != null && StringUtils.isNotBlank(b.getParent().getId())) {
            AreaDTO parent = service.find(b.getParent().getId());
            if (parent != null) {
                b.setLevel(parent.getLevel() + 1);
            }
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        try {
            getService().save(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping(value = "ztree")
    @ResponseBody
    public List<Map<String, Object>> ztree(HttpServletRequest request) {
        Searchable s = DynamicSearchUtils.toSearchable(request);
        s.addSearchParam("parent.id", Operator.ISNULL, null);
        List<Map<String, Object>> list = null;
        try {
            list = service.buildTree(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping(value = "/treeWiew")
    @ResponseBody
    public Map<String, Object> treeWiew(String pid) {

        Searchable s = new SearchRequest();
        if (StringUtils.isBlank(pid))
            s.addSearchParam("parent.id", Operator.ISNULL, pid);
        else
            s.addSearchParam("parent.id", Operator.EQ, pid);
        List<AreaDTO> list = (List<AreaDTO>) service.findAll(s);

        Map<String, Object> map = new HashMap<String, Object>();

        for (AreaDTO area : list) {
            Map<String, String> temp = new HashMap<String, String>();
            temp.put("name", area.getName());
            temp.put("id", area.getId());
            if (area.getLevel() < 2) {
                temp.put("type", "folder");
            } else {
                temp.put("type", "item");
            }
            map.put(area.getName(), temp);
        }
        return map;
    }

    @Override
    public PagingService<AreaDTO, PageResult<AreaDTO>, String> getService() {
        return service;
    }

}
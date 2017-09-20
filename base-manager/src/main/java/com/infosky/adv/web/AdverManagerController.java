package com.infosky.adv.web;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.adv.entity.dto.AdverManagerDTO;
import com.infosky.adv.service.impl.AdverManagerService;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;

/**
 * 广告位管理控制层
 * @author n003588
 */
@Controller
@RequestMapping("adv")
@Description(value = "广告位管理")
public class AdverManagerController extends CrudController<String, PageResult<AdverManagerDTO>, AdverManagerDTO> {

    private static final Logger logger = LoggerFactory.getLogger(AdverManagerController.class);

    @Autowired
    private AdverManagerService adverManagerService;

    @RequestMapping(value = "add")
    @ResponseBody
    @Description(value = "添加数据")
    public Map<String, Object> add(@RequestBody AdverManagerDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        try {
            Searchable s = new SearchRequest();
            if (b.getAdvno() != null) {
                s.addSearchParam("advid", Operator.EQ, b.getAdvno());
            }
            if (StringUtils.isNotBlank(b.getCitycode())) {
                s.addSearchParam("citycode", Operator.EQ, b.getCitycode());
            }
            if (b.getTerminaltype() != null) {
                s.addSearchParam("terminaltype", Operator.EQ, b.getTerminaltype());
            }
            if (StringUtils.isNotBlank(b.getAdvposition())) {
                s.addSearchParam("advposition", Operator.EQ, b.getAdvposition());
            }
            if (b.getAdvtype() != null) {
                s.addSearchParam("advtype", Operator.EQ, b.getAdvtype());
            }
            AdverManagerDTO tmp = adverManagerService.find(s);
            if (tmp != null) {
                result.put("msg", "已存在相同类型的广告位");
                return result;
            }
            getService().save(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping(value = "edit")
    @ResponseBody
    @Description(value = "编辑数据")
    public Map<String, Object> edit(@RequestBody AdverManagerDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        try {
            getService().update(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @Override
    public PagingService<AdverManagerDTO, PageResult<AdverManagerDTO>, String> getService() {
        return adverManagerService;
    }
}

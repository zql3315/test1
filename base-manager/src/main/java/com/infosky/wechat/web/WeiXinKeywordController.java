package com.infosky.wechat.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.wechat.entity.dto.WeiXinKeywordDTO;
import com.infosky.wechat.entity.dto.WeiXinKeywordMaterialDTO;
import com.infosky.wechat.entity.dto.WeiXinMaterialMessageDTO;
import com.infosky.wechat.service.impl.WeiXinKeywordMaterialService;
import com.infosky.wechat.service.impl.WeiXinKeywordService;
import com.infosky.wechat.service.impl.WeiXinMaterialMessageService;

/**
 * 微信关键词管理控制层
 * 
 * @author  n930177
 */
@Controller
@RequestMapping("/weiXin/weiXinKeyword")
public class WeiXinKeywordController extends CrudController<String, PageResult<WeiXinKeywordDTO>, WeiXinKeywordDTO> {

    private static final Logger logger = LoggerFactory.getLogger(WeiXinKeywordController.class);

    @Autowired
    private WeiXinKeywordService weiXinKeywordService;

    @Autowired
    private WeiXinMaterialMessageService weiXinMaterialMessageService;

    @Autowired
    private WeiXinKeywordMaterialService weiXinKeywordMaterialService;

    /**
     * 跳转至新增关键词页面
     * @param model 关键词类
     * @return 增关键词页面
     */
    @RequestMapping(value = "toAdd")
    @Description(value = "打开添加页面")
    public String toAdd(Model model) {
        List<WeiXinMaterialMessageDTO> materialMessageLst = (List<WeiXinMaterialMessageDTO>) weiXinMaterialMessageService.findAll();

        int count = 0;

        if (materialMessageLst != null && materialMessageLst.size() > 0) {
            count = materialMessageLst.size();
        }

        model.addAttribute("weiXinMaterialCount", count);
        model.addAttribute("weiXinMaterialLst", materialMessageLst);

        return getView("add");
    }

    @RequestMapping(value = "add")
    @ResponseBody
    @Description(value = "添加数据")
    public Map<String, Object> add(@RequestBody WeiXinKeywordDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        try {
            //查询关键词是否已存在
            boolean flag = weiXinKeywordService.isValidateKeyword(b);

            if (flag) {
                result.put("msg", "关键词已存在!");
                return result;
            }
            getService().save(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping(value = "addKeyword")
    @ResponseBody
    @Description(value = "添加关键词")
    public Map<String, Object> addKeyword(@RequestBody WeiXinKeywordDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            //查询关键词是否已存在
            boolean flag = weiXinKeywordService.isValidateKeyword(b);

            if (flag) {
                result.put("msg", "关键词已存在!");
                return result;
            }

            //添加关键词
            weiXinKeywordService.addWeiXinKeyword(b);

            result.put("result", true);
        } catch (Exception e) {
            logger.error("addKeywordError", e);
        }

        return result;
    }

    /**
     * 跳转至编辑关键词页面
     * @param model 关键词类
     * @param id 关键词编号
     * @return 关键词页面
     */
    @RequestMapping(value = "toEdit/{id}")
    @Description(value = "打开编辑页面")
    public String toEdit(@PathVariable String id, Model model) {
        WeiXinKeywordDTO d = weiXinKeywordService.find(id);

        int count = 0;
        List<WeiXinMaterialMessageDTO> materialMessageLst = new ArrayList<WeiXinMaterialMessageDTO>();

        //查询关键词素材关联关系表
        Searchable s = new SearchRequest();
        s.addSearchParam("weiXinKeyword.id", Operator.EQ, id);
        List<WeiXinKeywordMaterialDTO> weiXinKeywordMaterialLst = (List<WeiXinKeywordMaterialDTO>) weiXinKeywordMaterialService.findAll(s);

        for (WeiXinKeywordMaterialDTO weiXinKeywordMaterialDTO : weiXinKeywordMaterialLst) {
            Searchable ss = new SearchRequest();
            ss.addSearchParam("weiXinMaterial.id", Operator.EQ, weiXinKeywordMaterialDTO.getWeiXinMaterial().getId());

            List<WeiXinMaterialMessageDTO> newLst = (List<WeiXinMaterialMessageDTO>) weiXinMaterialMessageService.findAll(ss);

            if (newLst != null && newLst.size() > 0) {
                count = count + newLst.size();
            }

            materialMessageLst.addAll(newLst);
        }

        model.addAttribute("weiXinMaterialCount", count);
        model.addAttribute("weiXinMaterialLst", materialMessageLst);

        //查询所有图文信息
        List<WeiXinMaterialMessageDTO> materialMessageAllLst = (List<WeiXinMaterialMessageDTO>) weiXinMaterialMessageService.findAll();

        int allCount = 0;

        if (materialMessageAllLst != null && materialMessageAllLst.size() > 0) {
            allCount = materialMessageAllLst.size();
        }

        model.addAttribute("weiXinMaterialAllCount", allCount);
        model.addAttribute("weiXinMaterialAllLst", materialMessageAllLst);

        model.addAttribute("model", d);

        return getView("edit");
    }

    @RequestMapping(value = "edit")
    @ResponseBody
    @Description(value = "更新数据")
    public Map<String, Object> edit(@RequestBody WeiXinKeywordDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            //查询关键词是否已存在
            boolean flag = weiXinKeywordService.isValidateKeyword(b);

            if (flag) {
                result.put("msg", "关键词已存在!");
                return result;
            }
            getService().update(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    /**
     * 更新关键词
     * @param b 关键词类
     * @return Map集合对象
     */
    @RequestMapping(value = "editKeyword")
    @ResponseBody
    @Description(value = "更新关键词")
    public Map<String, Object> editKeyword(@RequestBody WeiXinKeywordDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            //查询关键词是否已存在
            boolean flag = weiXinKeywordService.isValidateKeyword(b);

            if (flag) {
                result.put("msg", "关键词已存在!");
                return result;
            }

            weiXinKeywordService.updateKeyword(b);

            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping(value = "deleteByIds")
    @ResponseBody
    @Description(value = "根据多个id批量删除数据")
    public Map<String, Object> deleteByIds(@RequestBody List<WeiXinKeywordDTO> b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        try {
            weiXinKeywordService.deleteKeyword(b);

            result.put("result", true);
        } catch (Exception e) {
            logger.error("deleteKeywordError", e);
        }

        return result;
    }

    @Override
    public PagingService<WeiXinKeywordDTO, PageResult<WeiXinKeywordDTO>, String> getService() {
        return weiXinKeywordService;
    }
}

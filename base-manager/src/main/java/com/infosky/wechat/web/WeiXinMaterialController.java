package com.infosky.wechat.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.infosky.wechat.entity.dto.WeiXinKeywordMaterialDTO;
import com.infosky.wechat.entity.dto.WeiXinMaterialDTO;
import com.infosky.wechat.entity.dto.WeiXinMaterialMessageDTO;
import com.infosky.wechat.service.impl.WeiXinKeywordMaterialService;
import com.infosky.wechat.service.impl.WeiXinMaterialMessageService;
import com.infosky.wechat.service.impl.WeiXinMaterialService;

/**
 * 素材管理-图文消息 控制层
 * @author  n930177
 */
@Controller
@RequestMapping("/weiXin/weiXinMaterial")
public class WeiXinMaterialController extends CrudController<String, PageResult<WeiXinMaterialDTO>, WeiXinMaterialDTO> {

    private static final Logger logger = LoggerFactory.getLogger(WeiXinMaterialController.class);

    @Autowired
    private WeiXinMaterialService service;

    @Autowired
    private WeiXinMaterialMessageService materialMessageService;

    @Autowired
    private WeiXinKeywordMaterialService keywordMaterialService;

    /**
     * 预览素材列表页面
     * @param model 素材类 
     * @return 素材列表页面
     */
    @RequestMapping(value = "preview")
    @Description(value = "预览列表页面")
    public String preview(Model model) {
        List<WeiXinMaterialMessageDTO> materialMessageLst = (List<WeiXinMaterialMessageDTO>) materialMessageService.findAll();

        int count = 0;

        if (materialMessageLst != null && materialMessageLst.size() > 0) {
            count = materialMessageLst.size();
        }

        model.addAttribute("weiXinMaterialCount", count);
        model.addAttribute("materialMessageLst", materialMessageLst);

        return getView("list");
    }

    /**
     * 预览素材列表页面
     * @param model 素材类 
     * @return 素材列表页面
     */
    @RequestMapping(value = "imagesPreview")
    @Description(value = "图片预览列表页面")
    public String imagesPreview(Model model) {
        List<WeiXinMaterialMessageDTO> materialMessageLst = new ArrayList<WeiXinMaterialMessageDTO>();

        Searchable s = new SearchRequest();
        s.addSearchParam("type", Operator.EQ, "images");
        List<WeiXinMaterialDTO> weiXinMaterialLst = (List<WeiXinMaterialDTO>) service.findAll(s);

        for (WeiXinMaterialDTO weiXinMaterialDTO : weiXinMaterialLst) {
            materialMessageLst.addAll(weiXinMaterialDTO.getMessageList());
        }

        model.addAttribute("weiXinMaterialCount", materialMessageLst.size());
        model.addAttribute("materialMessageLst", materialMessageLst);

        return getView("imageslist");
    }

    /**
     * 列表页面table分页数据Search
     * @param page
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "queryNewsPreview")
    @Description(value = "查询素材列表数据")
    public String queryNewsPreview(HttpServletRequest request, Model model) {
        String search_LIKE_title = request.getParameter("search_LIKE_title");

        try {
            int count = 0;
            Searchable ss = new SearchRequest();

            if (StringUtils.isNotBlank(search_LIKE_title)) {
                ss.addSearchParam("title", Operator.LIKE, search_LIKE_title);
            }

            List<WeiXinMaterialMessageDTO> materialMessageLst = (List<WeiXinMaterialMessageDTO>) materialMessageService.findAll(ss);

            if (materialMessageLst != null && materialMessageLst.size() > 0) {
                count = materialMessageLst.size();
            }

            model.addAttribute("weiXinMaterialCount", count);
            model.addAttribute("materialMessageLst", materialMessageLst);
        } catch (Exception e) {
            logger.error("queryList", e);
        }

        return getView("newslist");
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @Description(value = "删除数据")
    public Map<String, Object> delete(@RequestBody WeiXinMaterialDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            Searchable s = new SearchRequest();
            s.addSearchParam("weiXinMaterial.id", Operator.EQ, b.getId());
            List<WeiXinKeywordMaterialDTO> weiXinKeywordMaterialLst = (List<WeiXinKeywordMaterialDTO>) keywordMaterialService.findAll(s);

            if (weiXinKeywordMaterialLst != null && weiXinKeywordMaterialLst.size() > 0) {
                result.put("result", "该图文消息正在被使用，不能删除");
                return result;
            }

            getService().delete(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @Override
    public PagingService<WeiXinMaterialDTO, PageResult<WeiXinMaterialDTO>, String> getService() {
        return service;
    }

}

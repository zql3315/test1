package com.infosky.wechat.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.common.util.TimeUtil;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.wechat.entity.dto.WeiXinMaterialDTO;
import com.infosky.wechat.entity.dto.WeiXinMaterialMessageDTO;
import com.infosky.wechat.service.impl.WeiXinMaterialMessageService;
import com.infosky.wechat.service.impl.WeiXinMaterialService;

/**
 * 素材消息管理-图文消息 控制层
 * @author  n930177
 */
@Controller
@RequestMapping("/weiXin/weiXinMaterialMessage")
public class WeiXinMaterialMessageController extends CrudController<String, PageResult<WeiXinMaterialMessageDTO>, WeiXinMaterialMessageDTO> {

    private static final Logger logger = LoggerFactory.getLogger(WeiXinMaterialMessageController.class);

    @Autowired
    private WeiXinMaterialMessageService service;

    @Autowired
    private WeiXinMaterialService weiXinMaterialService;

    /**
     * 添加图文消息
     */
    @RequestMapping(value = "add")
    @ResponseBody
    @Description(value = "添加数据")
    public Map<String, Object> add(@RequestBody WeiXinMaterialMessageDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        try {
            //新增素材
            WeiXinMaterialDTO materialDTO = new WeiXinMaterialDTO();
            materialDTO.setType("news");
            materialDTO.setCreatetime(TimeUtil.currentTime("yyyy-MM-dd HH:mm:ss"));

            WeiXinMaterialDTO newMaterialDTO = weiXinMaterialService.save(materialDTO);

            if (newMaterialDTO == null) {
                return result;
            }

            //新增图文消息
            WeiXinMaterialMessageDTO dto = new WeiXinMaterialMessageDTO();
            dto.setTitle(b.getTitle());
            dto.setAuthor(b.getAuthor());
            dto.setPicurl(b.getPicurl());
            dto.setContent_source_url(b.getContent_source_url());
            dto.setDigest(b.getDigest());
            dto.setWeiXinMaterial(newMaterialDTO);
            dto.setFailtime(b.getFailtime());
            getService().save(dto);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("addWeiXinMaterialMessage", e);
        }

        return result;
    }

    @Override
    public PagingService<WeiXinMaterialMessageDTO, PageResult<WeiXinMaterialMessageDTO>, String> getService() {
        return service;
    }
}

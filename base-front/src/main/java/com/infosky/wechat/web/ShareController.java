package com.infosky.wechat.web;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.framework.annotation.Description;
import com.infosky.wechat.service.impl.WeiXinPublicAccountService;
import com.infosky.wechat.web.util.WxShareUtil;

/**
 * 微信分享功能
 * @author 930177
 */
@Controller
@RequestMapping(value = "share")
@Description(value = "微信分享功能")
public class ShareController {

    private static final Logger logger = LoggerFactory.getLogger(ShareController.class);

    @Autowired
    private WeiXinPublicAccountService weiXinPublicAccountService;

    /**
     * 获取分享配置信息
     * @param model
     * @return
     */
    @RequestMapping(value = "getJsapiSign", method = RequestMethod.POST)
    @ResponseBody
    @Description(value = "获取分享配置信息")
    public Map<String, Object> getJsapiSign(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            String requestUrl = request.getParameter("url");
            String jsapi_ticket = weiXinPublicAccountService.getJsapi_ticket();
            Map<String, String> ret = WxShareUtil.getWxShareConfig(requestUrl, jsapi_ticket);
            for (Entry<String, String> entry : ret.entrySet()) {
                result.put(entry.getKey().toString(), entry.getValue());
                logger.debug(entry.getKey().toString(), entry.getValue());
            }

            result.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}

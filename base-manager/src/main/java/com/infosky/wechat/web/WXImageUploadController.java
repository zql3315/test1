package com.infosky.wechat.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infosky.common.util.HttpUtil;
import com.infosky.common.util.PropertiesConfig;
import com.infosky.sys.service.security.ShiroUser;
import com.infosky.wechat.service.impl.WeiXinPublicAccountService;

/**
 * 微信图片上传
 * @author 004881
 */
@Controller
@RequestMapping(value = "wxImageUpload")
public class WXImageUploadController {

    private static final Logger logger = LoggerFactory.getLogger(WXImageUploadController.class);

    @Autowired
    private WeiXinPublicAccountService weiXinPublicAccountService;

    /**
     * 上次头像
     * 
     * 1、可以把该方法拷贝到相应的模块中去，上传完直接保存到数据库
     * 2、通过返回的路径，可以在页面中和其他属性一起保存的数据库
     * 
     * @param request
     * @return
     */
    @RequestMapping("/savehead")
    @ResponseBody
    public Map<String, Object> savehead(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        model.put("msg", "失败");
        String serverId = request.getParameter("serverId");
        String access_token;
        try {
            access_token = weiXinPublicAccountService.getAccessToken();
            if (StringUtils.isNotBlank(serverId)) {
                Subject subject = SecurityUtils.getSubject();
                ShiroUser user = (ShiroUser) subject.getPrincipal();
                String filePath = "/upload/images/wechathead/" + user.getId() + "/" + new Date().getTime() + ".jpg";
                String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + access_token + "&media_id=" + serverId;
                boolean b = HttpUtil.downFile(url, PropertiesConfig.readValue("imagewebroot") + filePath);
                if (b) {
                    model.put("result", true);
                    model.put("msg", "SUCCESS");
                    model.put("fileUrl", filePath);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return model;
    }

}

package com.infosky.wechat.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.common.util.TimeUtil;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.wechat.common.TicketResult;
import com.infosky.wechat.common.WechatConstantParam;
import com.infosky.wechat.common.WxConfigUtil;
import com.infosky.wechat.entity.dto.WeiXinPublicAccountDTO;
import com.infosky.wechat.service.impl.WeiXinPublicAccountService;

/**
 * 微信公众账号配置
 * 
 * @author  n004883
 */
@Controller
@RequestMapping("/weiXin/weiXinPublicAccount")
public class WeiXinPublicAccountController extends CrudController<String, PageResult<WeiXinPublicAccountDTO>, WeiXinPublicAccountDTO> {

    private static final Logger logger = LoggerFactory.getLogger(WeiXinPublicAccountController.class);

    @Autowired
    private WeiXinPublicAccountService service;

    /**
     * 跳转至编辑公众账号配置页面
     * @param model 微信公共账号类
     * @return 编辑公众账号页面
     */
    @RequestMapping(value = "toEdit")
    @Description(value = "跳转至编辑公众账号配置页面")
    public String toEdit(Model model) {
        WeiXinPublicAccountDTO d = service.find(WechatConstantParam.PUBLICACCOUNT_VAlue);

        model.addAttribute("model", d);

        return getView("edit");
    }

    /**
     * 编辑公众账号
     * @param b 微信公共账号类
     * @return Map集合对象
     */
    @RequestMapping(value = "edit")
    @ResponseBody
    @Description(value = "更新数据")
    public Map<String, Object> edit(@RequestBody WeiXinPublicAccountDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        b.setCreatetime(TimeUtil.currentTime(WechatConstantParam.PATTERN_STYLE));

        try {
            getService().update(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    /**
     * 获取AccessToken
     * @param request HttpServletRequest
     * @return Map集合对象
     */
    @RequestMapping("/getAccessToken")
    @ResponseBody
    @Description(value = "获取AccessToken")
    public Map<String, Object> getAccessToken(HttpServletRequest request) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        result.put("accessToken", "");
        result.put("access_token_expires_in", "");
        result.put("access_token_createtime", "");
        result.put("jsapi_ticket", "");
        result.put("jsapi_ticket_expires_in", "");
        result.put("jsTicket_createtime", "");

        try {
            WeiXinPublicAccountDTO d = service.find(WechatConstantParam.PUBLICACCOUNT_VAlue);

            if (d == null) {
                return result;
            }

            String accessTokenJson = WxConfigUtil.getAccess_token(d.getAppid(), d.getAppsecret());
            JSONObject jsonObj = JSONObject.fromObject(accessTokenJson);
            String accessToken = jsonObj.get("access_token").toString();
            result.put("access_token", accessToken);
            result.put("access_token_expires_in", jsonObj.get("expires_in"));
            result.put("access_token_createtime", TimeUtil.currentTime(WechatConstantParam.PATTERN_STYLE));

            TicketResult ticketResult = WxConfigUtil.getJsTicket(d.getAppid(), d.getAppsecret(), accessToken);

            if (ticketResult != null) {
                result.put("jsapi_ticket", ticketResult.getTicket());
                result.put("jsapi_ticket_expires_in", ticketResult.getExpires_in());
                result.put("jsTicket_createtime", TimeUtil.currentTime(WechatConstantParam.PATTERN_STYLE));
            }

            result.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /** {@inheritDoc} */
    public PagingService<WeiXinPublicAccountDTO, PageResult<WeiXinPublicAccountDTO>, String> getService() {
        return service;
    }

}

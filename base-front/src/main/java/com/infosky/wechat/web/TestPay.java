package com.infosky.wechat.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infosky.common.util.CommonUtil;
import com.infosky.common.util.HttpUtil;
import com.infosky.common.util.PropertiesConfig;
import com.infosky.common.util.encrypt.MD5Util;
import com.infosky.wechat.service.impl.WeiXinUtilServiceImpl;

/**
 * 
 * 微信支付测试
 * 
 * @author n004881
 *
 */
@Controller
@RequestMapping("wechat")
public class TestPay {

    @Autowired
    private WeiXinUtilServiceImpl weiXinUtilService;

    protected final static Logger logger = LoggerFactory.getLogger(TestPay.class);

    /**
     * 去支付页面
     * 
     * @param request
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping("pay")
    public String pay(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws IOException {

        String openid = (String) request.getSession().getAttribute("openid");
        if (StringUtils.isBlank(openid)) {
            getOpenidByOauth2(request, response);
        }
        map.put("openid", request.getSession().getAttribute("openid"));
        String timeStamp = new Date().getTime() + "";
        String nonceStr = CommonUtil.CreateNoncestr(32);//随机字符串
        map.put("appId", PropertiesConfig.readValue("appId"));//微信服务号的唯一的应用ID
        map.put("timeStamp", timeStamp.substring(0, 10));//时间戳
        map.put("nonceStr", nonceStr);//随机字符串
        map.put("signType", "MD5");//微信签名方式
        return "wechat/testpay";

    }

    /**
     * @param request
     * @param response
     * @throws IOException 
     */
    private void getOpenidByOauth2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ua = request.getHeader("user-agent").toLowerCase();
        String state = request.getParameter("state");
        String code = request.getParameter("code");
        if (ua.indexOf("micromessenger") > 0 && StringUtils.isBlank(state) && StringUtils.isBlank(code)) {// 是微信浏览器
            String appId = PropertiesConfig.readValue("appId");
            String redirect_uri = request.getRequestURL().append("?" + request.getQueryString()).toString();
            String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=" + redirect_uri
                    + "&response_type=code&scope=snsapi_base&state=Infosky#wechat_redirect";
            response.sendRedirect(redirectUrl);
            return;
        }
        // **********************************oauth2 重定向跳转 end ************************************************//

        // **********************************oauth2 获取opendid start ************************************************//
        if (StringUtils.isNotBlank(code) && state != null && state.equals("Infosky")) {// 微信回调请求
            logger.info("===NeedOauth2UrlInterceptor==用户同意授权获取的code:" + code);
            logger.info("===NeedOauth2UrlInterceptor==用户同意授权获取的state:" + state);
            String getAccess_tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET" + "&code=" + code + "&grant_type=authorization_code";
            getAccess_tokenUrl = getAccess_tokenUrl.replace("APPID", PropertiesConfig.readValue("appId"));
            getAccess_tokenUrl = getAccess_tokenUrl.replace("APPSECRET", PropertiesConfig.readValue("appSecret"));
            // 通过code换取网页授权access_token的请求
            String message = HttpUtil.sendGet(getAccess_tokenUrl);
            logger.info("===NeedOauth2UrlInterceptor==获取微信用户授权的信息：" + message);

            JSONObject access_message = JSONObject.fromObject(message);
            if (access_message != null && access_message.get("openid") != null) {
                String openid = access_message.getString("openid");
                logger.info("======openid: " + openid);
                request.getSession().setAttribute("openid", openid);
            }

        }
    }

    /**
    * 调用微信统一支付接口，获取预支付id 
    * @param request
    * @param map
    * @return
    */
    @RequestMapping("pay/getUniFiedorder")
    @ResponseBody
    public Map<String, String> getUniFiedorder(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            map.put("errorMsg", "获取预支付失败");
            logger.info("=====获取预支付id开始=============");
            String prepay_id = weiXinUtilService.unifiedorder(request);
            if (StringUtils.isNotBlank(prepay_id)) {
                map.put("prepay_id", prepay_id);
                logger.info("=====获取预支付id结束=============");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("=====获取预支付id异常=============");
        }
        return map;
    }

    /**
     * 获取  JSAPI  支付签名
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("pay/paySign")
    @ResponseBody
    public Map<String, String> paySign(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("errorMsg", "获取  JSAPI  支付签名失败");
        logger.info("=====获取  JSAPI  支付签名 开始=============");
        String appId = PropertiesConfig.readValue("appId");
        String timeStamp = request.getParameter("timeStamp");//时间戳
        String nonceStr = request.getParameter("nonceStr");//随机字符串
        String signType = "MD5";//微信签名方式
        String key = PropertiesConfig.readValue("payPartnerKey"); //商户支付密钥
        String packages = "prepay_id=" + request.getParameter("prepay_id"); //预支付id

        String content = "appId=" + appId + "&nonceStr=" + nonceStr + "&package=" + packages + "&signType=" + signType + "&timeStamp=" + timeStamp;
        content = content + "&key=" + key;//支付签名
        logger.info("=====明文签名字符串：" + content);
        String paySign = MD5Util.MD5(content).toUpperCase();
        logger.info("=====密文签名字符串：" + paySign);
        map.put("paySign", paySign);
        logger.info("=====获取  JSAPI  支付签名 结束=============");
        return map;
    }

    /**
     * 微信支付成功通知
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("payNotify")
    @ResponseBody
    public Map<String, String> PayNotify(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        logger.info("====支付成功通知 start=== ==");
        String respMessage = weiXinUtilService.processNotice(request);
        map.put("return_code", respMessage.toUpperCase());
        logger.info("====支付成功通知 end=== ==");
        return map;
    }

}

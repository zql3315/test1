package com.infosky.wechat.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosky.common.util.CommonUtil;
import com.infosky.common.util.PropertiesConfig;
import com.infosky.common.util.encrypt.MD5Util;
import com.infosky.wechat.service.PaymentService;
import com.infosky.wechat.service.WeiXinUtilService;

@Controller
public class TestPay {

    @Autowired
    private WeiXinUtilService weiXinUtilService;

    @Autowired
    private PaymentService paymentService;

    protected final static Logger logger = LoggerFactory.getLogger(TestPay.class);

    @RequestMapping("/test/pay")
    public String pay(HttpServletRequest request, ModelMap map) {

        map.put("openid", request.getSession().getAttribute("openid"));
        String timeStamp = new Date().getTime() + "";
        String nonceStr = CommonUtil.CreateNoncestr(32);//随机字符串
        map.put("appId", PropertiesConfig.readValue("appId"));//微信服务号的唯一的应用ID
        map.put("timeStamp", timeStamp.substring(0, 10));//时间戳
        map.put("nonceStr", nonceStr);//随机字符串
        map.put("signType", "MD5");//微信签名方式
        return "/test/pay";

    }

    /**
    * 调用微信统一支付接口，获取预支付id 
    * @param request
    * @param map
    * @return
    */
    @RequestMapping("pay/getUniFiedorder")
    public ModelAndView getUniFiedorder(HttpServletRequest request, ModelMap map) {
        try {
            map.put("errorMsg", "获取预支付失败");
            logger.info("=====获取预支付id开始=============");
            String prepay_id = weiXinUtilService.unifiedorder(request, map);
            if (StringUtils.isNotBlank(prepay_id)) {
                map.put("prepay_id", prepay_id);
                logger.info("=====获取预支付id结束=============");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("=====获取预支付id异常=============");
        }
        return new ModelAndView("jsonView", map);
    }

    /**
     * 获取  JSAPI  支付签名
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("test/pay/paySign")
    public ModelAndView paySign(HttpServletRequest request, ModelMap map) {
        map.put("errorMsg", "获取  JSAPI  支付签名失败");
        logger.info("=====获取  JSAPI  支付签名 开始=============");
        String appId = PropertiesConfig.readValue("appId");
        String timeStamp = request.getParameter("timeStamp");//时间戳
        String nonceStr = request.getParameter("nonceStr");//随机字符串
        String signType = "MD5";//微信签名方式
        String key = PropertiesConfig.readValue("partnerKey"); //商户支付密钥
        String packages = "prepay_id=" + request.getParameter("prepay_id"); //预支付id

        String content = "appId=" + appId + "&nonceStr=" + nonceStr + "&package=" + packages + "&signType=" + signType + "&timeStamp=" + timeStamp;
        content = content + "&key=" + key;//支付签名
        logger.info("=====明文签名字符串：" + content);
        String paySign = MD5Util.MD5(content).toUpperCase();
        logger.info("=====密文签名字符串：" + paySign);
        map.put("paySign", paySign);
        logger.info("=====获取  JSAPI  支付签名 结束=============");
        return new ModelAndView("jsonView", map);
    }

    /**
     * 微信支付成功通知
     * 本地保存一份，蜜蜂保存一份
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("payNotify")
    public ModelAndView PayNotify(HttpServletRequest request, ModelMap map) {
        logger.info("====支付成功通知 start=== ==");
        String respMessage = paymentService.processNotice(request);
        map.put("return_code", respMessage.toUpperCase());
        logger.info("====支付成功通知 end=== ==");
        return new ModelAndView("jsonView", map);
    }

}

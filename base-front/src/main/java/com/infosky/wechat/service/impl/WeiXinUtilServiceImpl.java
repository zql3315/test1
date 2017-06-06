package com.infosky.wechat.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosky.common.util.AddUtils;
import com.infosky.common.util.CommonUtil;
import com.infosky.common.util.HttpUtil;
import com.infosky.common.util.PropertiesConfig;
import com.infosky.common.util.XmlFormatter;
import com.infosky.common.util.encrypt.MD5Util;
import com.infosky.wechat.common.MessageUtil;
import com.infosky.wechat.entity.dto.WeiXinNotifyOrderDTO;
import com.infosky.wechat.entity.po.WeixinUniFiedOrder;

/**
 * 微信工具服务类
 * 
 * @author n004881
 *
 */
@Service
public class WeiXinUtilServiceImpl {

    @Autowired
    protected final static Logger logger = LoggerFactory.getLogger(WeiXinUtilServiceImpl.class);

    @Autowired
    private WeiXinPublicAccountService weiXinPublicAccountService;

    /**
     /**
     * 微信统一支付接口调用
     * @param request
     * @return 返回预支付id
     * @throws UnknownHostException
     * @throws UnsupportedEncodingException 
     */
    public String unifiedorder(HttpServletRequest request) {

        //把支付价格的单位从元变成分

        String openid = request.getParameter("openid");
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        List<String> list = new ArrayList<String>();
        WeixinUniFiedOrder wuo = new WeixinUniFiedOrder();
        wuo.setAppId(PropertiesConfig.readValue("appId"));
        wuo.setAttach("微信支付");
        if (StringUtils.isNotBlank(request.getParameter("body"))) if (request.getParameter("body").trim().length() > 120)
            wuo.setBody(request.getParameter("body").trim().substring(0, 120));
        else
            wuo.setBody(request.getParameter("body").trim());
        wuo.setDevice_info(PropertiesConfig.readValue("device_info"));
        wuo.setMch_id(PropertiesConfig.readValue("payMchid"));
        wuo.setNotify_url(PropertiesConfig.readValue("notifyurl"));
        wuo.setNonce_str(CommonUtil.CreateNoncestr(32));
        wuo.setOpenid(openid);
        wuo.setOut_trade_no(CommonUtil.CreateNoncestr(32));
        wuo.setSpbill_create_ip(AddUtils.getClientIpAddr(request));
        wuo.setTotal_fee(1);
        wuo.setTrade_type("JSAPI");
        list.add("appid=" + wuo.getAppId());//微信公众号身份的唯一标识。
        list.add("attach=" + wuo.getAttach());//附加数据，原样返回
        list.add("body=" + wuo.getBody());//商品描述
        list.add("device_info=" + wuo.getDevice_info());//微信支付分配的终端设备号
        list.add("mch_id=" + wuo.getMch_id());//微信支付  分配的商户号
        list.add("notify_url=" + wuo.getNotify_url());//接收微信支付成功通知
        list.add("nonce_str=" + wuo.getNonce_str());//随机字符串，不长于	32位
        list.add("openid=" + wuo.getOpenid());//opid
        list.add("out_trade_no=" + wuo.getOut_trade_no());//商户系统内部的订单号,32个字符内、可包含字母,确保在商户系统唯一
        //    	list.add("product_id="+wuo.getProduct_id());//商品id
        list.add("spbill_create_ip=" + wuo.getSpbill_create_ip());//订单生成的机器	IP
        list.add("total_fee=" + wuo.getTotal_fee());//订单总金额，单位为分，不能带小数点
        list.add("trade_type=" + wuo.getTrade_type());//交易类型
        Collections.sort(list);//参数名ASCII码从小到大排序（字典序）
        String content = list.toString().substring(1, list.toString().length() - 1).replace(", ", "&");
        String key = PropertiesConfig.readValue("payPartnerKey"); //商户支付密钥
        String paySign = content + "&key=" + key;//支付签名
        logger.info("=====加密明文:" + paySign);
        paySign = MD5Util.MD5(paySign).toUpperCase();
        logger.info("=====微信签名:" + paySign);
        wuo.setSign(paySign);
        StringBuilder param = new StringBuilder();
        param.append("<xml>");
        param.append("<appid><![CDATA[" + wuo.getAppId() + "]]></appid>");
        param.append("<attach><![CDATA[" + wuo.getAttach() + "]]></attach>");
        param.append("<body><![CDATA[" + wuo.getBody() + "]]></body>");
        param.append("<device_info><![CDATA[" + wuo.getDevice_info() + "]]></device_info>");
        param.append("<mch_id><![CDATA[" + wuo.getMch_id() + "]]></mch_id>");
        param.append("<nonce_str><![CDATA[" + wuo.getNonce_str() + "]]></nonce_str>");
        param.append("<notify_url><![CDATA[" + wuo.getNotify_url() + "]]></notify_url>");
        param.append("<out_trade_no><![CDATA[" + wuo.getOut_trade_no() + "]]></out_trade_no>");
        param.append("<spbill_create_ip><![CDATA[" + wuo.getSpbill_create_ip() + "]]></spbill_create_ip>");
        param.append("<total_fee><![CDATA[" + wuo.getTotal_fee() + "]]></total_fee>");
        param.append("<trade_type><![CDATA[" + wuo.getTrade_type() + "]]></trade_type>");
        param.append("<openid><![CDATA[" + wuo.getOpenid() + "]]></openid>");
        //					"<product_id><![CDATA["+wuo.getProduct_id()+"]]></product_id>"+
        param.append("<sign><![CDATA[" + paySign + "]]></sign>");
        param.append("</xml>");
        logger.info("===post请求参数===" + XmlFormatter.format(param.toString()));
        String str = HttpUtil.sendPost(url, param.toString());
        logger.info("===响应===" + XmlFormatter.format(str));
        if (str.contains("prepay_id")) {
            String temp = str.substring(str.indexOf("prepay_id>") + 19);
            return temp.substring(0, temp.indexOf("]]></prepay_id>"));
        }
        return null;
    }

    /**
     * 通过多客服接口给用户发送一个消息
     * @param openid
     * @param param
     */
    public void sendDKFMessage(String openid, String param) {
        String access_token = weiXinPublicAccountService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + access_token;
        //   String param = "{" + "\"touser\":\"" + openid + "\"," + "\"msgtype\":\"text\"," + "\"text\":" + "{" + "\"content\":\"亲，快来邀请好友加入企业联盟吧，好友扫码就成为您的联盟商。联盟商下单完成交易便能享有9%-30%的佣金收入，分享越多，收益越多！\"" + "}"+ "}";
        logger.info("===========多客服发送参数：" + JSONObject.fromObject(param));
        String result = HttpUtil.sendPost(url, param);
        logger.info("===========多客服响应：" + JSONObject.fromObject(result));
    }

    /**
     * 处理微信支付的通知
     * @param request
     * @return
     */
    public String processNotice(HttpServletRequest request) {
        String respMessage = "SUCCESS";
        // 封装订单数据和用户数据
        WeiXinNotifyOrderDTO order = this.setOrderInfo(request);
        if (null == order || !order.getReturnCode().equals("SUCCESS") || !order.getResultCode().equals("SUCCESS")) {
            logger.error("====处理微信支付通知异常===订单信息：" + JSONObject.fromObject(order));
            return "FAIL";
        }
        //调用支付完成接口，订单数据入库处理
        //todo
        return respMessage;
    }

    /**
     * 设置订单信息
     * @param request
     * @return
     */
    private WeiXinNotifyOrderDTO setOrderInfo(HttpServletRequest request) {
        // 处理用户信息
        Map<String, String> orderInfoMap = MessageUtil.parseXml(request);
        if (orderInfoMap != null) {
            WeiXinNotifyOrderDTO orderInfo = new WeiXinNotifyOrderDTO();
            logger.info("========交易通知信息" + JSONObject.fromObject(orderInfoMap));
            orderInfo.setReturnCode(orderInfoMap.get("return_code"));
            orderInfo.setReturnMsg(orderInfoMap.get("return_msg"));
            orderInfo.setAppId(orderInfoMap.get("appid"));
            orderInfo.setMchId(orderInfoMap.get("mch_id"));//微信支付分配的商户号
            orderInfo.setDeviceInfo(orderInfoMap.get("device_info"));//微信支付分配的终端设备号
            orderInfo.setNonceStr(orderInfoMap.get("nonce_str"));//随机字符串
            orderInfo.setResultCode(orderInfoMap.get("result_code"));//业务结果 SUCCESS/FAIL
            orderInfo.setSign(orderInfoMap.get("sign"));//签名
            orderInfo.setErrCode(orderInfoMap.get("err_code"));//错误代码
            orderInfo.setErrCodeDes(orderInfoMap.get("err_code_des"));//错误代码描述
            orderInfo.setOpenId(orderInfoMap.get("openid"));//用户在商户appid下的唯一标识
            orderInfo.setIsSubscribe(orderInfoMap.get("is_subscribe"));//用户是否关注公众账号，Y-  关注， N   -未关注，仅在公众   账号类型支付有效
            orderInfo.setTradeType(orderInfoMap.get("trade_type"));//交易类型JSAPI   NATIVE    MICROPAY  APP
            orderInfo.setBankType(orderInfoMap.get("bank_type"));//银行类型，采用字符串类型的银行标识
            orderInfo.setCash_fee_type(orderInfoMap.get("cash_fee_type"));//货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见
            if (orderInfoMap.get("total_fee") != null) {
                orderInfo.setTotalFee(Integer.parseInt(orderInfoMap.get("total_fee")));//总金额 单位元
            }
            if (orderInfoMap.get("cash_fee") != null) {
                orderInfo.setCash_fee(Integer.parseInt(orderInfoMap.get("cash_fee")));//现金支付金额订单现金支付金额，
            }
            if (orderInfoMap.get("coupon_fee") != null) {
                orderInfo.setCouponFee(Integer.parseInt(orderInfoMap.get("coupon_fee")));//现金券支付金额<=订单总金额，订单总金额 -现金券金额为现金支付金额
            }
            if (orderInfoMap.get("coupon_count") != null) {
                orderInfo.setCoupon_count(Integer.parseInt(orderInfoMap.get("coupon_count")));//代金券或立减优惠使用数量
            }
            if (orderInfoMap.get("coupon_fee_$n") != null) {
                orderInfo.setCoupon_fee_$n(Integer.parseInt(orderInfoMap.get("coupon_fee_$n")));//代金券或立减优惠使用数量
            }
            orderInfo.setCoupon_id_$n(orderInfoMap.get("coupon_id_$n"));//代金券或立减优惠ID,$n为下标，从0开始编号
            orderInfo.setFeeType(orderInfoMap.get("fee_type"));//货币类型，符合ISO 4217标准的三位字母代码，默认人   民币：CNY
            orderInfo.setTransactionId(orderInfoMap.get("transaction_id"));//微信支付订单号
            orderInfo.setOutTradeNo(orderInfoMap.get("out_trade_no"));//商户系统的订单号,与请求一致
            orderInfo.setAttach(orderInfoMap.get("attach"));//商家数据包，原样返回
            orderInfo.setTimeEnd(orderInfoMap.get("time_end"));//支付完成时间, 格 式 为yyyyMMddhhmmss。时区为    GMT+8 beijing   。该时间取自  微信支付服务器
            return orderInfo;
        }
        return null;
    }

}

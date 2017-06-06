package com.infosky.wechat.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.infosky.wechat.common.MessageUtil;
import com.infosky.wechat.entity.po.WeiXinNotifyOrder;

/**
 * @author n004881
 *
 */
@Service
public class PaymentService {

    protected final static Log logger = LogFactory.getLog(PaymentService.class);

    /**
     * 处理微信支付的通知
     * @param request
     * @return
     */
    public String processNotice(HttpServletRequest request) {

        String respMessage = "SUCCESS";
        // 封装订单数据和用户数据
        WeiXinNotifyOrder order = this.setOrderInfo(request);
        if (null == order || !order.getReturnCode().equals("SUCCESS") || !order.getResultCode().equals("SUCCESS")) {
            logger.error("====处理微信支付通知异常===订单信息：" + JSONObject.fromObject(order));
            return "FAIL";
        }
        //调用支付完成接口，订单数据入库处理
        try {
            WeiXinNotifyOrder temp = new WeiXinNotifyOrder();
            temp.setTransactionId(order.getTransactionId());
            //因为微信支付成功通知会发多次，所以需要判断一下是否已经入库

        } catch (Exception e) {
            logger.error("支付订单入库失败:" + e.getMessage());
        }
        return respMessage;
    }

    /**
     * 设置订单信息
     * @param request
     * @return
     */
    private WeiXinNotifyOrder setOrderInfo(HttpServletRequest request) {
        WeiXinNotifyOrder orderInfo = null;
        try {
            orderInfo = new WeiXinNotifyOrder();
            // 处理用户信息
            Map<String, String> orderInfoMap = MessageUtil.parseXml(request);
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
            orderInfo.setIsSubscribe(orderInfoMap.get("is_subscribe"));//用户是否关注公众账号，Y-	关注，	N	-未关注，仅在公众	账号类型支付有效
            orderInfo.setTradeType(orderInfoMap.get("trade_type"));//交易类型JSAPI   NATIVE    MICROPAY  APP
            orderInfo.setBankType(orderInfoMap.get("bank_type"));//银行类型，采用字符串类型的银行标识
            orderInfo.setTotalFee(Integer.parseInt(orderInfoMap.get("total_fee")));//总金额 单位元
            orderInfo.setCash_fee(Integer.parseInt(orderInfoMap.get("cash_fee")));//现金支付金额订单现金支付金额，
            orderInfo.setCash_fee_type(orderInfoMap.get("cash_fee_type"));
            orderInfo.setCouponFee(Integer.parseInt(orderInfoMap.get("coupon_fee")));//现金券支付金额<=订单总金额，订单总金额	-现金券金额为现金支付金额
            orderInfo.setCoupon_count(Integer.parseInt(orderInfoMap.get("coupon_count")));//代金券或立减优惠使用数量
            orderInfo.setCoupon_fee_$n(Integer.parseInt(orderInfoMap.get("coupon_fee_$n")));//代金券或立减优惠使用数量
            orderInfo.setCoupon_id_$n(orderInfoMap.get("coupon_id_$n"));//代金券或立减优惠ID,$n为下标，从0开始编号
            orderInfo.setFeeType(orderInfoMap.get("fee_type"));//货币类型，符合ISO 4217标准的三位字母代码，默认人	民币：CNY
            orderInfo.setTransactionId(orderInfoMap.get("transaction_id"));//微信支付订单号
            orderInfo.setOutTradeNo(orderInfoMap.get("out_trade_no"));//商户系统的订单号,与请求一致
            orderInfo.setAttach(orderInfoMap.get("attach"));//商家数据包，原样返回
            orderInfo.setTimeEnd(orderInfoMap.get("time_end"));//支付完成时间, 格 式 为yyyyMMddhhmmss。时区为	GMT+8 beijing	。该时间取自	微信支付服务器
        } catch (Exception e) {
            logger.error("设置订单信息 setOrderInfo:" + e.getMessage());
            e.printStackTrace();
        }
        return orderInfo;
    }

}

package com.infosky.wechat.entity.dto;

import com.infosky.framework.entity.dto.DTO;

/**
 * 微信订单通知返回接口
 * 
 * 
 */
public class WeiXinNotifyOrderDTO extends DTO<Integer> {

    /**
     * 
     */
    private static final long serialVersionUID = 8737681063762718399L;

    // 微信支付订单号
    private String transactionId;

    // 签名
    private String sign;

    // 总金额
    private Integer totalFee;

    // 现金券支付金额 <=订单总金额，订单总金额 -现金券金额为现金支付金额
    private Integer couponFee;

    // 代金券或立减优惠使用数量
    private Integer coupon_count;

    // 代金券或立减优惠ID,$n为下标，从0开始编号
    private String coupon_id_$n;

    // 单个代金券或立减优惠支付金额,$n为下标，从0开始编号
    private Integer coupon_fee_$n;

    // 现金支付金额订单现金支付金额
    private Integer cash_fee;

    // 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
    private String cash_fee_type;

    // 货币类型，符合ISO 4217标准的三位字母代码，默认人 民币：CNY
    private String feeType;

    // 商户订单号
    private String outTradeNo;

    // 商家数据包
    private String attach;

    // 支付完成时间
    private String timeEnd;

    // 支付该笔订单的用户ID，商户可通过公众号其他接口为付款用户服务。
    private String openId;

    // 公众号id
    private String appId;

    // 用户是否关注了公众号。1 为关注，0 为未关注。
    private String isSubscribe;

    // 随机字符串
    private String nonceStr;

    // SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查 看result_code来判断
    private String returnCode;

    // S业务结果 SUCCESS/FAIL
    private String resultCode;

    // 返回信息，如非空，为错误原因 : 签名失败 ,参数格式校验错误等
    private String returnMsg;

    // 微信支付分配的商户号
    private String mchId;

    // 微信支付分配的终端设备号
    private String deviceInfo;

    // 错误码
    private String errCode;

    // 错误码描述
    private String errCodeDes;

    // 交易类型JSAPI NATIVE MICROPAY APP
    private String tradeType;

    // 银行类型，采用字符串类型的银行标识
    private String bankType;

    public Integer getCoupon_count() {
        return coupon_count;
    }

    public void setCoupon_count(Integer coupon_count) {
        this.coupon_count = coupon_count;
    }

    public String getCoupon_id_$n() {
        return coupon_id_$n;
    }

    public void setCoupon_id_$n(String coupon_id_$n) {
        this.coupon_id_$n = coupon_id_$n;
    }

    public Integer getCoupon_fee_$n() {
        return coupon_fee_$n;
    }

    public void setCoupon_fee_$n(Integer coupon_fee_$n) {
        this.coupon_fee_$n = coupon_fee_$n;
    }

    public Integer getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(Integer cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getCash_fee_type() {
        return cash_fee_type;
    }

    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(Integer couponFee) {
        this.couponFee = couponFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

}

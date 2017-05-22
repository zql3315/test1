package com.infosky.wechat.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.CommonUtil;
import com.infosky.common.util.HttpUtil;
import com.infosky.common.util.PropertiesConfig;
import com.infosky.common.util.XmlFormatter;
import com.infosky.common.util.date.DateUtils;
import com.infosky.common.util.encrypt.MD5Util;
import com.infosky.wechat.entity.dto.WeiXinKeywordDTO;
import com.infosky.wechat.entity.dto.WeiXinKeywordMaterialDTO;
import com.infosky.wechat.entity.dto.WeiXinMaterialMessageDTO;
import com.infosky.wechat.entity.dto.WeiXinPublicAccountDTO;
import com.infosky.wechat.entity.po.WeiXinAutoReplyMessage;
import com.infosky.wechat.entity.po.WeixinUniFiedOrder;
import com.infosky.wechat.service.impl.WeiXinKeywordMaterialService;
import com.infosky.wechat.service.impl.WeiXinKeywordService;
import com.infosky.wechat.service.impl.WeiXinMaterialMessageService;
import com.infosky.wechat.service.impl.WeiXinPublicAccountService;

@Service
public class WeiXinUtilService {

    protected final static Logger logger = LoggerFactory.getLogger(WeiXinUtilService.class);

    /*@Autowired
    CacheManager cache;*/

    @Resource
    private WeiXinKeywordService weiXinKeywordService;

    @Autowired
    private WeiXinPublicAccountService weiXinPublicAccountService;

    @Autowired
    private WeiXinMaterialMessageService weiXinMaterialMessageService;

    @Autowired
    private WeiXinKeywordMaterialService weiXinKeywordMaterialService;

    /**
     * 获取access_token
     */
    public String getAccessToken() {
        WeiXinPublicAccountDTO info = weiXinPublicAccountService.find("1");
        String accessToken = null;

        // 判断数据库中的accessToken是否过期
        int timestamp = 0;
        // 若数据库没有accessToken 认为时间已过期
        if (info.getAccesstoken() == null || "".equals(info.getAccesstoken())) {
            timestamp = 7300;
        } else {
            try {
                timestamp = DateUtils.getTimeStamp(DateUtils.str2Date(info.getAccesstokencreatetime(), "yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // accessToken有效
        if (timestamp < 7200 && timestamp > 0) {
            accessToken = info.getAccesstoken();
        } else {
            // 获取新的accessToken
            URL postUrl = null;
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                postUrl = new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + info.getAppid() + "&secret=" + info.getAppsecret());
                connection = (HttpURLConnection) postUrl.openConnection();
                connection.connect();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                StringBuffer str = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
                accessToken = JSONObject.fromObject(str.toString()).getString("access_token");
                // 跟新数据库中的accessToken
                info.setAccesstoken(accessToken);
                info.setAccesstokencreatetime(DateUtils.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
                weiXinPublicAccountService.save(info);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (connection != null) connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return accessToken;
    }

    /**
     * /** 微信统一支付接口调用
     * 
     * @param request
     * @return 返回预支付id
     * @throws UnknownHostException
     * @throws UnsupportedEncodingException
     */
    public String unifiedorder(HttpServletRequest request, ModelMap map) throws UnknownHostException, UnsupportedEncodingException {

        // 把支付价格的单位从元变成分

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
        wuo.setMch_id(PropertiesConfig.readValue("mchid"));
        wuo.setNotify_url(PropertiesConfig.readValue("notifyurl"));
        wuo.setNonce_str(CommonUtil.CreateNoncestr(32));
        wuo.setOpenid(openid);
        wuo.setOut_trade_no(CommonUtil.CreateNoncestr(32));
        wuo.setSpbill_create_ip(InetAddress.getLocalHost().getHostAddress());
        wuo.setTotal_fee(1);
        wuo.setTrade_type("JSAPI");
        list.add("appid=" + wuo.getAppId());// 微信公众号身份的唯一标识。
        list.add("attach=" + wuo.getAttach());// 附加数据，原样返回
        list.add("body=" + wuo.getBody());// 商品描述
        list.add("device_info=" + wuo.getDevice_info());// 微信支付分配的终端设备号
        list.add("mch_id=" + wuo.getMch_id());// 微信支付 分配的商户号
        list.add("notify_url=" + wuo.getNotify_url());// 接收微信支付成功通知
        list.add("nonce_str=" + wuo.getNonce_str());// 随机字符串，不长于 32位
        list.add("openid=" + wuo.getOpenid());// opid
        list.add("out_trade_no=" + wuo.getOut_trade_no());// 商户系统内部的订单号,32个字符内、可包含字母,确保在商户系统唯一
        // list.add("product_id="+wuo.getProduct_id());//商品id
        list.add("spbill_create_ip=" + wuo.getSpbill_create_ip());// 订单生成的机器 IP
        list.add("total_fee=" + wuo.getTotal_fee());// 订单总金额，单位为分，不能带小数点
        list.add("trade_type=" + wuo.getTrade_type());// 交易类型
        Collections.sort(list);// 参数名ASCII码从小到大排序（字典序）
        String content = list.toString().substring(1, list.toString().length() - 1).replace(", ", "&");
        String key = PropertiesConfig.readValue("partnerKey"); // 商户支付密钥
        String paySign = content + "&key=" + key;// 支付签名
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
        // "<product_id><![CDATA["+wuo.getProduct_id()+"]]></product_id>"+
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
     */
    public void sendDKFMessage(String openid) {
        String access_token = getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + access_token;
        String param = "{" + "\"touser\":\"" + openid + "\"," + "\"msgtype\":\"text\"," + "\"text\":" + "{" + "\"content\":\"亲，好友扫描就成您的客户，客户下单就有奖金\"" + "}" + "}";
        logger.info("===========多客服发送参数：" + JSONObject.fromObject(param));
        String result = HttpUtil.sendPost(url, param);
        logger.info("===========多客服响应：" + JSONObject.fromObject(result));
    }

    /**
     * 首次关注回复
     * 
     * 主要是因为开启了开发者模式，从而使得开始设置的自动回复消息失效，但是没有做相应的微信后台管理， 所以只能通过调用微信API接口来获取原先设置的自动回复设置，然后取得里面的”首次关注“关键字配置的图文消息 在条用API接口返回给用户
     * 
     * @return
     */
    public List<WeiXinAutoReplyMessage> getNewsInfo() {
        logger.info("===========获取首次关注开始===============");
        //ValueWrapper v = cache.getCache("keyword_autoreply_info").get("keyword_autoreply_info");
        String access_token = getAccessToken();
        /*if (v != null && v.get() != null && StringUtils.isNotBlank(v.get().toString())) {
        	logger.info("=====缓存中取得的== 关注回复 =====" + JSONArray.fromObject(v.get()));
        	return (List<WeiXinAutoReplyMessage>) v.get();
        }*/
        List<WeiXinAutoReplyMessage> messageList = null;
        try {
            String autoreply_info = null;
            if (StringUtils.isNotBlank(access_token)) {
                String url = "https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token=" + access_token;
                autoreply_info = HttpUtil.sendGet(url);
                logger.info("==========获取配置的自动回复：" + autoreply_info);
            } else {
                logger.error(access_token);
            }
            if (StringUtils.isNotBlank(autoreply_info)) {
                JSONObject json = JSONObject.fromObject(autoreply_info);
                if (json.get("keyword_autoreply_info") == null) return null;
                JSONObject keyword_autoreply_info = json.getJSONObject("keyword_autoreply_info");
                if (keyword_autoreply_info.get("list") == null) return null;
                JSONArray keyword_autoreply_info_list = keyword_autoreply_info.getJSONArray("list");
                JSONArray news_info_list = null;
                if (keyword_autoreply_info_list.size() <= 0) return null;
                for (int i = 0; i < keyword_autoreply_info_list.size(); i++) {
                    JSONObject obj = keyword_autoreply_info_list.getJSONObject(i);
                    if (obj.getString("rule_name").equals("关注回复") && obj.getJSONArray("reply_list_info").size() > 0) {
                        JSONArray reply_list_info = obj.getJSONArray("reply_list_info");
                        for (int y = 0; y < reply_list_info.size(); y++) {
                            JSONObject news_info = reply_list_info.getJSONObject(i);
                            if (news_info.get("type") != null && news_info.getString("type").equals("news")) {
                                news_info_list = news_info.getJSONObject("news_info").getJSONArray("list");
                                break;
                            }

                        }
                    }
                }
                if (news_info_list != null && news_info_list.size() > 0) {
                    messageList = new ArrayList<WeiXinAutoReplyMessage>();
                    for (int i = 0; i < news_info_list.size(); i++) {
                        JSONObject news_info = news_info_list.getJSONObject(i);
                        WeiXinAutoReplyMessage weiXinMessage = new WeiXinAutoReplyMessage();
                        weiXinMessage.setDescription(news_info.getString("digest"));
                        weiXinMessage.setLinkurl(news_info.getString("content_url"));
                        weiXinMessage.setTitle(news_info.getString("title"));
                        weiXinMessage.setPicurl(news_info.getString("cover_url"));
                        messageList.add(weiXinMessage);
                    }
                    //cache.getCache("keyword_autoreply_info").put("keyword_autoreply_info", messageList);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("===========获取首次关注结束===============");
        return messageList;

    }

    /**
     * 通过关键字重置自定义菜单
     * 
     * 主要是因为开启了开发者模式，从而使得开始设置的自定义菜单失效，但是没有做相应的微信后台管理， 所以只能通过关键字回复来调用微信API接口创建自定义菜单，菜单配置从公众平台官网通过网站功能发布的菜单获取
     * 
     * @return
     */
    public String resetMenu() {

        logger.info("===========重置菜单开始===============");
        try {
            String access_token = getAccessToken();
            String menuStr = null;
            if (StringUtils.isNotBlank(access_token)) {
                String url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=" + access_token;
                menuStr = HttpUtil.sendGet(url);
                logger.info("==========获取的菜单配置：" + menuStr);
            } else {
                logger.error(access_token);
            }
            if (StringUtils.isNotBlank(menuStr)) {
                JSONObject json = JSONObject.fromObject(menuStr);
                String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
                List<Map<String, String>> menuList = new ArrayList<Map<String, String>>();
                json = JSONObject.fromObject(json.getString("selfmenu_info"));
                JSONArray array = json.getJSONArray("button");
                for (int i = 0; i < array.size(); i++) {
                    JSONObject obj = (JSONObject) array.get(i);
                    Map<String, String> map = new HashMap<String, String>();
                    if (obj.get("sub_button") != null) {
                        map.put("name", obj.getString("name"));
                        map.put("sub_button", obj.getJSONObject("sub_button").getJSONArray("list").toString().replaceAll("\"text\"", "\"click\"").replaceAll("\"value\"", "\"key\""));
                    } else {
                        map.put("name", obj.getString("name"));
                        map.put("type", obj.getString("type"));
                        if (obj.getString("type").equals("view")) {
                            map.put("url", obj.getString("url"));
                        } else if (obj.getString("type").equals("click")) {
                            map.put("key", obj.getString("key"));
                        } else if (obj.getString("type").equals("click")) {
                            map.put("key", obj.getString("key"));
                        } else if (obj.getString("type").equals("text")) {
                            map.put("key", obj.getString("key"));
                            map.put("type", "click");
                        }
                    }
                    menuList.add(map);
                }
                JSONArray button = JSONArray.fromObject(menuList);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("button", button);
                String creatMenuStr = JSONObject.fromObject(map).toString();
                logger.info("==========创建菜单参数：" + creatMenuStr);
                String result = HttpUtil.sendPost(url, creatMenuStr);
                return result;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "系统繁忙";
        }
        logger.info("===========重置菜单结束===============");
        return "重置失败";

    }

    /**
     * 获取微信关键词
     * @param key
     * @return WeiXinKeywordDTO
     */
    public WeiXinKeywordDTO getWeiXinKeyword(String key) {
        Searchable s = new SearchRequest();
        s.addSearchParam("keyword", Operator.EQ, key);
        return weiXinKeywordService.find(s);

    }

    /**
     * 获取微信接口配置信息
     * @return WeiXinKeywordDTO
     */
    public WeiXinPublicAccountDTO getWeiXinPublicAccount() {
        List<WeiXinPublicAccountDTO> list = (List<WeiXinPublicAccountDTO>) weiXinPublicAccountService.findAll();

        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return new WeiXinPublicAccountDTO();

    }

    /**
     * 根据关键词查询图文消息
     * @param keyword
     * @return
     */
    public List<WeiXinMaterialMessageDTO> getWeiXinMaterialMessage(String keyword) {
        List<WeiXinMaterialMessageDTO> weiXinMaterialMessageLst = new ArrayList<WeiXinMaterialMessageDTO>();

        //查询关键词对象
        Searchable s = new SearchRequest();
        s.addSearchParam("keyword", Operator.EQ, keyword);
        WeiXinKeywordDTO weiXinKeywordDTO = weiXinKeywordService.find(s);

        if (weiXinKeywordDTO == null) {
            return weiXinMaterialMessageLst;
        }

        //根据关键词id获取关键词与素材关联信息
        Searchable ss = new SearchRequest();
        ss.addSearchParam("weiXinKeyword.id", Operator.EQ, weiXinKeywordDTO.getId());
        List<WeiXinKeywordMaterialDTO> weiXinKeywordMaterialLst = (List<WeiXinKeywordMaterialDTO>) weiXinKeywordMaterialService.findAll(ss);

        for (WeiXinKeywordMaterialDTO weiXinKeywordMaterialDTO : weiXinKeywordMaterialLst) {
            //查询图文消息
            Searchable sss = new SearchRequest();
            sss.addSearchParam("weiXinMaterial.id", Operator.EQ, weiXinKeywordMaterialDTO.getWeiXinMaterial().getId());
            WeiXinMaterialMessageDTO weiXinMaterialMessageDTO = weiXinMaterialMessageService.find(sss);
            weiXinMaterialMessageLst.add(weiXinMaterialMessageDTO);
        }

        return weiXinMaterialMessageLst;

    }

}

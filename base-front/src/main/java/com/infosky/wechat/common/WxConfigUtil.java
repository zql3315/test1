package com.infosky.wechat.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;

import com.infosky.common.util.HttpUtil;

/**
 * 微信开发
 * 1.获取接口调用凭据
 * 2.获取微信服务器IP地址
 * 3.自定义菜单
 * @author n004883
 */
public class WxConfigUtil {

    /**
     * 获取access token
     * @param grant_type client_credential
     * @param appid 第三方用户唯一凭证
     * @param secret 第三方用户唯一凭证密钥，即appsecret
     * @return access token
     */
    public static String getAccess_token(String appid, String secret) {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        String access_token = HttpUtil.sendGet(url);
        return access_token;
    }

    /**
     * 获取微信服务器IP地址
     * @param access_token 公众号的access_token 
     * @return ip_list
     */
    public static String getCallbackip(String access_token) {
        String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=" + access_token;
        String ip_list = HttpUtil.sendGet(url);
        return ip_list;
    }

    /**
     * 获取自定义菜单配置接口
     * @param access_token
     * @return
     */
    public static String getCurrentSelfMenuInfo(String access_token) {
        String url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=" + access_token;
        return HttpUtil.sendGet(url);
    }

    /**
     * 创建自定义菜单
     * @param access_token 公众号的access_token 
     * @return
     */
    public static String createCustomMenu(String access_token) {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
        String menuStr = getCurrentSelfMenuInfo(access_token);

        if (StringUtils.isNotBlank(menuStr)) {
            List<Map<String, String>> menuList = new ArrayList<Map<String, String>>();

            JSONObject json = JSONObject.fromObject(menuStr);
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

            return HttpUtil.sendPost(url, creatMenuStr);
        }

        return "FAIL";

    }

    /**
     * 自定义菜单查询接口
     * @param access_token 公众号的access_token 
     * @return
     */
    public static String queryCustomMenu(String access_token) {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + access_token;
        return HttpUtil.sendGet(url);
    }

    /**
     * 自定义菜单删除接口
     * @param access_token 公众号的access_token 
     * @return
     */
    public static String deleteCustomMenu(String access_token) {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + access_token;
        return HttpUtil.sendGet(url);
    }

    /**
     * 获取JsTicket
     * @param appId 应用APPID
     * @param appSecret 应用密钥
     * @return
     */
    public static TicketResult getJsTicket(String appId, String appSecret, String access_token) {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";

        //获取jsapi_ticket
        String ticketStr = HttpUtil.sendGet(url);
        TicketResult ticketResult = WxJsonUtil.fromJson(ticketStr, TicketResult.class);

        return ticketResult;

    }

    /**
     * 微信自定义菜单操作
     * @param workType 操作类型  create创建 delete删除 get查看
     * @param accesstoken 公众号的全局唯一票据
     * @param json 需要发布的菜单json字符串
     * @return String 和微信接口对接成功或者失败标识 
     */
    public static String weiXinMenuWork(String workType, String accesstoken, String json) {
        String uri = "https://api.weixin.qq.com/cgi-bin/menu/" + workType + "?access_token=" + accesstoken;
        URL url;

        try {

            url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();
            if (json != null && !json.equals("")) {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));

                bw.write(json);
                bw.flush();
                bw.close();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            lines = sb.toString();
            reader.close();
            // 断开连接
            connection.disconnect();
            if (JSONObject.fromObject(lines).get("errcode") != null) {
                return JSONObject.fromObject(lines).get("errcode").toString();
            } else {
                return lines;
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            return "9999";
        }
        return "10001";

    }

}

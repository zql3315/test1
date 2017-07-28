package com.infosky.wechat.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.common.mapper.BeanMapper;
import com.infosky.common.util.date.DateUtils;
import com.infosky.common.util.date.DateUtilsExtend;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.wechat.dao.WeiXinPublicAccountDao;
import com.infosky.wechat.entity.dto.WeiXinPublicAccountDTO;
import com.infosky.wechat.entity.po.WeiXinPublicAccount;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author zan
 * @version [版本号, 2015年2月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
@Transactional
public class WeiXinPublicAccountService extends JpaService<WeiXinPublicAccount, WeiXinPublicAccountDTO, PageResult<WeiXinPublicAccountDTO>, String> {

    @Autowired
    private WeiXinPublicAccountDao dao;

    /**
     * 获取token
     * 
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public String getAccessToken() {
        WeiXinPublicAccountDTO info = this.find("1");
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
                dao.save(BeanMapper.map(info, WeiXinPublicAccount.class));

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
     * 获取token
     * 
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public String getJsapi_ticket() {
        WeiXinPublicAccountDTO info = this.find("1");
        String Jsapi_ticket = "";

        // 判断数据库中的accessToken是否过期
        int timestamp = 0;
        // 若数据库没有accessToken 认为时间已过期
        if (StringUtils.isBlank(info.getTicket())) {
            timestamp = 7300;
        } else {
            try {
                timestamp = DateUtilsExtend.getTimeStamp(DateUtilsExtend.str2Date(info.getTicketcreatetime(), "yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // accessToken有效
        if (timestamp < 7200 && timestamp > 0) {
            Jsapi_ticket = info.getTicket();
        } else {
            String accesstoken = getAccessToken();
            // 获取新的Jsapi_ticket
            String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accesstoken + "&type=jsapi";
            URL postUrl = null;
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                postUrl = new URL(url);
                connection = (HttpURLConnection) postUrl.openConnection();
                connection.connect();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                StringBuffer str = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
                Jsapi_ticket = JSONObject.fromObject(str.toString()).getString("ticket");
                // 跟新数据库中的accessToken
                info.setAccesstoken(accesstoken);
                info.setAccesstokencreatetime(DateUtilsExtend.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
                info.setTicket(Jsapi_ticket);
                info.setTicketcreatetime(DateUtilsExtend.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
                dao.save(BeanMapper.map(info, WeiXinPublicAccount.class));
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

        return Jsapi_ticket;
    }

    /** {@inheritDoc} */

    @Override
    protected DAO<WeiXinPublicAccount, String> getDAO() {
        return dao;
    }

}

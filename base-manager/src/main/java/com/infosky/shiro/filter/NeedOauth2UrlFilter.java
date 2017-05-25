package com.infosky.shiro.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosky.common.util.HttpUtil;
import com.infosky.common.util.PropertiesConfig;

/**
 * 微信网页授权拦截器
 * 
 * @author n004881
 */
public class NeedOauth2UrlFilter extends OncePerRequestFilter {

    private Logger log = LoggerFactory.getLogger(NeedOauth2UrlFilter.class);
    
    /**
     * 不弹出授权页面，直接跳转，只能获取用户openid
     */
    private static String BASIC_TYPE = "snsapi_base";

    /**
     * 弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息
     */
    private static String USER_TYPE = "snsapi_userinfo";

    /**
     * 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     */
    private static String STATE = "infosky";

    public NeedOauth2UrlFilter() {

    }

    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException,
            IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String appId = PropertiesConfig.readValue("appId");
        Object openid = req.getSession().getAttribute("openid");
        // ****************************** oauth2 重定向跳转 start ****************************************************//
        String url = req.getRequestURL().append("?" + req.getQueryString()).toString();
        url = url.replaceAll("need_oauth2_url=true", "1=1").replaceAll("\\?null", "");
        log.info("========= NeedOauth2UrlFilter=====" + url+"==openid="+openid);
        // 需要内部重定向进行 微信oauth2_url跳转
        String need_oauth2_url = request.getParameter("need_oauth2_url");
        String flag = request.getParameter("flag");
        boolean wechatBrowser = false;
        String ua = req.getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
            wechatBrowser = true;
        }
        if ((need_oauth2_url != null && "true".equals(need_oauth2_url) && openid==null)
                || (wechatBrowser && !url.contains(STATE) && openid==null)  ) {
            String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri="
                    + url.toString() + "&response_type=code&scope="
                    + (flag != null && flag.equals("userinfo") ? USER_TYPE : BASIC_TYPE) + "&state=" + STATE + "#wechat_redirect";
            httpServletResponse.sendRedirect(redirectUrl);
            return;
        }
        // **********************************oauth2 重定向跳转 end ************************************************//

        // **********************************oauth2 获取opendid start ************************************************//
        String state = request.getParameter("state");
        String code = request.getParameter("code");
        if (StringUtils.isNotBlank(code) && state != null && state.equals(STATE) && openid == null) {// 微信回调请求
            log.info("===NeedOauth2UrlInterceptor==用户同意授权获取的code:" + code);
            log.info("===NeedOauth2UrlInterceptor==用户同意授权获取的state:" + state);
            log.info("===NeedOauth2UrlInterceptor==openid: " + openid);
            String getAccess_tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET"
                    + "&code=" + code + "&grant_type=authorization_code";
            getAccess_tokenUrl = getAccess_tokenUrl.replace("APPID", PropertiesConfig.readValue("appId"));
            getAccess_tokenUrl = getAccess_tokenUrl.replace("APPSECRET", PropertiesConfig.readValue("appSecret"));
            // 通过code换取网页授权access_token的请求
            String message = HttpUtil.sendGet(getAccess_tokenUrl);
            log.info("===NeedOauth2UrlInterceptor==获取微信用户授权的信息：" + message);

            JSONObject access_message = JSONObject.fromObject(message);
            if (access_message != null && access_message.get("openid") != null) {
                req.getSession().setAttribute("access_token", access_message.getString("access_token"));
                req.getSession().setAttribute("refresh_token", access_message.getString("refresh_token"));
                req.getSession().setAttribute("openid", access_message.getString("openid"));
                req.getSession().setAttribute("asseccCode", code);
                openid = access_message.getString("openid");
                log.info("======openid: " + openid);
                if(access_message.get("scope")!=null && access_message.getString("scope").equals("snsapi_userinfo")){
                    getUserInfo(access_message.getString("access_token"), openid.toString());
                }
            }

        }

        // **********************************oauth2 获取opendid end ************************************************//

        chain.doFilter(request, response);
    }
    
    /**
     * 基于网页授权的方式获取用户微信的信息包含头像，昵称等等
     * 
     * @param user 
     * @param access_token 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同 
     * @param openid
     * @return
     */
    private void getUserInfo(String access_token, String openid) {

        try {
            String getAccess_UserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
            getAccess_UserInfo = getAccess_UserInfo.replace("ACCESS_TOKEN", access_token);
            getAccess_UserInfo = getAccess_UserInfo.replace("OPENID", openid);
            String message = HttpUtil.sendGet(getAccess_UserInfo);
            log.info("====获取微信用户基本信息：" + message);
        } catch (Exception e) {
            log.error("====获取微信用户基本信息error：" + e.getMessage());
        }
    }
}

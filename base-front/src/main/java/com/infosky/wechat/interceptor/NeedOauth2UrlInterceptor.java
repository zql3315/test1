package com.infosky.wechat.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.infosky.common.util.HttpUtil;
import com.infosky.common.util.PropertiesConfig;

public class NeedOauth2UrlInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger(NeedOauth2UrlInterceptor.class);

    private static String BASIC_TYPE = "snsapi_base"; //不弹出授权页面，直接跳转，只能获取用户openid

    private static String USER_TYPE = "snsapi_userinfo"; //弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息

    public NeedOauth2UrlInterceptor() {

    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String appId = PropertiesConfig.readValue("appId");
        String appSecret = PropertiesConfig.readValue("appSecret");
        //****************************** oauth2 重定向跳转 start ****************************************************//
        String url = request.getRequestURL().append("?" + request.getQueryString()).toString();
        log.debug("=====NeedOauth2UrlInterceptor 拦截到的请求是=====" + url);
        url = url.toString().replace("need_oauth2_url=true", "1=1");
        log.debug("======appId: " + appId);
        log.debug("===appSecret: " + appSecret);
        //需要内部重定向进行 微信oauth2_url跳转
        String need_oauth2_url = request.getParameter("need_oauth2_url");
        String flag = request.getParameter("flag");
        if (need_oauth2_url != null && "true".equals(need_oauth2_url)) {
            String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=" + url.toString() + "&response_type=code&scope="
                    + (flag != null && flag.equals("userinfo") ? USER_TYPE : BASIC_TYPE) + "&state=TUNIU#wechat_redirect";
            response.sendRedirect(redirectUrl);
            return false;
        }
        //**********************************oauth2 重定向跳转 end  ************************************************//

        //**********************************oauth2 获取opendid start  ************************************************//
        String state = request.getParameter("state");
        String code = request.getParameter("code");
        if (StringUtils.isNotBlank(code) && state != null && state.equals("TUNIU")) {// 微信回调请求
            log.info("===用户同意授权获取的code:" + code);
            log.info("===用户同意授权获取的state:" + state);
            Object openid = request.getSession().getAttribute("openid");
            log.info("======openid: " + openid);
            if (openid == null) {
                String getAccess_tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET" + "&code=" + code + "&grant_type=authorization_code";
                getAccess_tokenUrl = getAccess_tokenUrl.replace("APPID", PropertiesConfig.readValue("appId"));
                getAccess_tokenUrl = getAccess_tokenUrl.replace("APPSECRET", PropertiesConfig.readValue("appSecret"));
                String message = HttpUtil.sendGet(getAccess_tokenUrl);
                log.info("====获取微信用户授权的信息：" + message);

                JSONObject json1 = JSONObject.fromObject(message);
                if (json1.get("openid") != null) {
                    request.getSession().setAttribute("access_token", json1.getString("access_token"));
                    request.getSession().setAttribute("refresh_token", json1.getString("refresh_token"));
                    request.getSession().setAttribute("openid", json1.getString("openid"));
                    request.getSession().setAttribute("asseccCode", code);
                    openid = json1.getString("openid");
                    log.info("======openid: " + openid);
                    getUserInfo(json1);
                }

            }
        }

        //**********************************oauth2 获取opendid end  ************************************************//

        return true;
    }

    /**
     * 获取用户微信的信息包含头像，昵称等等
     * @param json 
     */
    private void getUserInfo(JSONObject json) {

        try {
            if (json.get("scope") != null) {
                String getAccess_UserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
                getAccess_UserInfo = getAccess_UserInfo.replace("ACCESS_TOKEN", json.getString("access_token"));
                getAccess_UserInfo = getAccess_UserInfo.replace("OPENID", json.getString("openid"));
                String message = HttpUtil.sendGet(getAccess_UserInfo);
                log.info("====获取微信用户基本信息：" + message);
            }
        } catch (Exception e) {
            log.error("====获取微信用户基本信息error：" + e.getMessage());
        }

    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 
     * 
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //		log.debug("=====postHandle====="+request.getRequestURI());
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用 
     * 
     *   当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //		log.debug("=====afterCompletion====="+request.getRequestURI());
    }
}

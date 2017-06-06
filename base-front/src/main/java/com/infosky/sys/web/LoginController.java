package com.infosky.sys.web;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infosky.shiro.service.CaptchaException;

/**
 * 登录
 * 
 * @version [版本号, 2014年10月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    CacheManager cache;

    @RequestMapping(method = RequestMethod.GET)
    public String login(Model model) {
        // 如果用户直接到登录页面 先退出一下
        // 原因：isAccessAllowed实现是subject.isAuthenticated()---->即如果用户验证通过 就允许访问
        // 这样会导致登录一直死循环
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            subject.logout();
        }

        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username, Map<String, Object> map, ServletRequest request) {

        String msg = parseException(request);

        map.put("msg", msg);
        map.put("username", username);

        return "login";
    }

    @RequestMapping(method = RequestMethod.POST, headers = "x-requested-with=XMLHttpRequest")
    public @ResponseBody String failDialog(ServletRequest request) {
        return parseException(request);
    }

    private String parseException(ServletRequest request) {
        String errorString = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        Class<?> error = null;
        try {
            if (errorString != null) {
                error = Class.forName(errorString);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String msg = "其他错误！";
        if (error != null) {
            if (error.equals(UnknownAccountException.class))
                msg = "未知帐号错误！";
            else if (error.equals(AccountException.class)) {
                msg = "用户名不能为空！";
            } else if (error.equals(IncorrectCredentialsException.class)) {
                Object retryCount = cache.getCache("passwordRetryCache").get(request.getParameter("username"));
                msg = "密码错误！如果错误超过5次将被锁定10分钟";
                if (retryCount != null) msg = "密码错误" + retryCount + "次！如果错误超过5次将被锁定10分钟";
            } else if (error.equals(AuthenticationException.class)) {
                msg = "认证失败！";
            } else if (error.equals(LockedAccountException.class)) {
                msg = "账号被冻结！";
            } else if (error.equals(DisabledAccountException.class)) {
                msg = "失效的账号！";
            } else if (error.equals(ExcessiveAttemptsException.class)) {
                msg = "密码失败次数连续超过5次，请10分钟后再试！";
            } else if (error.equals(CaptchaException.class)) {
                msg = "验证码错误";
            }
        }
        return "登录失败，" + msg;
    }
}

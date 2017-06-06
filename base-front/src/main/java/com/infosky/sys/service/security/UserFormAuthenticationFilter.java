package com.infosky.sys.service.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosky.common.util.CommonUtil;
import com.infosky.shiro.service.UsernamePasswordCaptchaToken;

/**
 * 为了支持 AJAX 的方式访问的权限验证，重写了 FormAuthenticationFilter 的登录逻
 * 
 * @author n004881
 * 
 */
public class UserFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(UserFormAuthenticationFilter.class);

    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    protected String getCaptcha(ServletRequest request) {

        return WebUtils.getCleanParam(request, DEFAULT_CAPTCHA_PARAM);

    }

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {

        String username = getUsername(request);

        String password = getPassword(request);

        String captcha = getCaptcha(request);

        boolean rememberMe = isRememberMe(request);

        String host = getHost(request);

        return new UsernamePasswordCaptchaToken(username, password.toCharArray(), rememberMe, host, captcha);

    }

    public UserFormAuthenticationFilter() {
        log.debug("New MyFormAuthenticationFilter()");
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     * 
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            }
            return true;
        }
        if (!CommonUtil.useAjax(httpServletRequest)) {
            saveRequestAndRedirectToLogin(request, response);
        } else {
            // 使用 AJAX 登录成功返回的信息
            httpServletResponse.setCharacterEncoding("UTF-8");
            //定义超时头部状态字段和状态码
            httpServletResponse.setHeader("result", "sessionInvalidation");
            PrintWriter out = httpServletResponse.getWriter();
            out.println();
            out.flush();
            out.close();
        }
        return false;
    }

    /**
     * 登陆成功后处理的逻辑
     * */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (!CommonUtil.useAjax(httpServletRequest)) {
            issueSuccessRedirect(request, response);
        } else {
            // 使用 AJAX 登录成功返回的信息
            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = httpServletResponse.getWriter();
            out.println("{success:true, message:'登录成功'}");
            out.flush();
            out.close();
        }

        return false;
    }

    /**
     * 登陆失败后处理的逻辑
     * */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (!CommonUtil.useAjax(httpServletRequest)) {
            // login failed, let request continue back to the login page:
            setFailureAttribute(request, e);
            request.setAttribute("error", e.getMessage());
            return true;
        } else {
            try {
                // 使用 AJAX 登录失败返回的信息
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println("{success:false, message:'登录失败'}");
                out.flush();
                out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return false;
        }
    }

    // 身份认证：登录
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        UsernamePasswordToken token = (UsernamePasswordToken) createToken(request, response);
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

}

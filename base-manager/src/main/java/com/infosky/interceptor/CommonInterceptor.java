package com.infosky.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CommonInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);

    public CommonInterceptor() {

    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("=====preHandle=====" + request.getRequestURI());
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("=====postHandle=====" + request.getRequestURI());
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     * 
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("=====afterCompletion=====" + request.getRequestURI());
    }
}

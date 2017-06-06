package com.infosky.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用注解标注过滤器
 * 
 * @WebFilter 将一个实现了javax.servlet.Filte接口的类定义为过滤器 属性filterName声明过滤器的名称,可选 属性urlPatterns指定要过滤 的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)
 */
@WebFilter(filterName = "Servlet3Filter", urlPatterns = "/*", asyncSupported = true)
public class Servlet3Filter implements Filter {

    private static Logger log = LoggerFactory.getLogger(Servlet3Filter.class);

    @Override
    public void destroy() {
        log.info("过滤器销毁");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //        log.info("执行过滤操作");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        log.info("过滤器初始化");
    }
}

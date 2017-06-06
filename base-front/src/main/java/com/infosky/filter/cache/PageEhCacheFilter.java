package com.infosky.filter.cache;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import net.sf.ehcache.constructs.web.AlreadyCommittedException;
import net.sf.ehcache.constructs.web.AlreadyGzippedException;
import net.sf.ehcache.constructs.web.filter.FilterNonReentrantException;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 页面缓存过滤器
 * 
 * @author n004881
 * 
 * @version 1.0
 */
@WebFilter(filterName = "PageEhCacheFilter", urlPatterns = "/*", asyncSupported = true, initParams = {
        @WebInitParam(name = "patterns", value = "/demo/pageCache"), @WebInitParam(name = "cacheName", value = "simplePageCache")
})
public class PageEhCacheFilter extends SimplePageCachingFilter {

    private static Logger log = LoggerFactory.getLogger(PageEhCacheFilter.class);

    private static String FILTER_URL_PATTERNS = "patterns";

    private static String[] cacheURLs;

    private void init() {

        String patterns = filterConfig.getInitParameter(FILTER_URL_PATTERNS);

        cacheURLs = StringUtils.split(patterns, ",");

    }

    @Override
    protected void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) {
        if (cacheURLs == null) {
            init();
        }
        String url = request.getRequestURI();
        boolean flag = false;
        if (cacheURLs != null && cacheURLs.length > 0) {
            for (String cacheURL : cacheURLs) {
                if (url.contains(cacheURL.trim())) {
                    flag = true;
                    break;
                }
            }
        }
        // 如果包含我们要缓存的url 就缓存该页面，否则执行正常的页面转向
        if (flag) {
            String query = request.getQueryString();
            if (query != null) {
                query = "?" + query;
            } else {
                query = "";
            }
            log.info("当前请求被缓存：" + url + query);
            try {
                super.doFilter(request, response, chain);
            } catch (AlreadyCommittedException e) {
                e.printStackTrace();
            } catch (AlreadyGzippedException e) {
                e.printStackTrace();
            } catch (FilterNonReentrantException e) {
                e.printStackTrace();
            } catch (LockTimeoutException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                chain.doFilter(request, response);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean headerContains(final HttpServletRequest request, final String header, final String value) {

        logRequestHeaders(request);

        final Enumeration<?> accepted = request.getHeaders(header);

        while (accepted.hasMoreElements()) {

            final String headerValue = (String) accepted.nextElement();

            if (headerValue.indexOf(value) != -1) {

                return true;

            }

        }

        return false;

    }

    /**
     * 
     * @see net.sf.ehcache.constructs.web.filter.Filter#acceptsGzipEncoding(javax.servlet.http.HttpServletRequest)
     * 
     *      <b>function:</b> 兼容ie6/7 gzip压缩
     * 
     * @author n004881
     * 
     * @createDate 2016-5-31 上午11:07:11
     */

    @Override
    protected boolean acceptsGzipEncoding(HttpServletRequest request) {

        boolean ie6 = headerContains(request, "User-Agent", "MSIE 6.0");

        boolean ie7 = headerContains(request, "User-Agent", "MSIE 7.0");

        return acceptsEncoding(request, "zip") || ie6 || ie7;

    }

}

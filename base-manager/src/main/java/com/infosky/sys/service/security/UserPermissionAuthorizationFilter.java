package com.infosky.sys.service.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.CommonUtil;
import com.infosky.framework.web.WebUtil;
import com.infosky.sys.entity.dto.ResourceDTO;
import com.infosky.sys.service.impl.ResourceService;

/**
 * @author n004881
 * 重写权限验证过滤器
 *
 */
public class UserPermissionAuthorizationFilter extends PermissionsAuthorizationFilter {

    @Autowired
    CacheManager cache;

    /**
     * 
     * 重写权限验证不成功之后的逻辑，支持ajax
    */
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {
            saveRequestAndRedirectToLogin(request, response);
        } else {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            String unauthorizedUrl = getUnauthorizedUrl();
            if (CommonUtil.useAjax(httpServletRequest)) {
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setHeader("result", "nopermission");
                PrintWriter out = httpServletResponse.getWriter();
                out.println();
                out.flush();
                out.close();
            } else if (StringUtils.hasText(unauthorizedUrl)) {
                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {
                WebUtils.toHttp(response).sendError(401);
            }
        }
        return false;
    }

    /**
     * 请求URL禁止带占位符，否则权限无法判断是参数还是url
     **/
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        Subject subject = getSubject(request, response);
        String perms[] = (String[]) mappedValue;
        boolean isPermitted = super.isAccessAllowed(request, response, mappedValue);
        if (isPermitted) {// 当父类权限拦截成功后 再进行一下URL的鉴权
            if (subject.getPrincipal().toString().equals("admin")) {
                return true;
            }
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String URI = httpServletRequest.getRequestURI().replace(httpServletRequest.getContextPath(), "");
            if (URI.contains("/static/") || URI.equals("/") || URI.equals("/index") || URI.equals("/checkUserSession")) return true;
            if (URI.indexOf("/toEdit/") > 0) {
                URI = URI.substring(0, URI.indexOf("/toEdit") + 7);
            }
            if (URI.indexOf("/view/") > 0) {
                URI = URI.substring(0, URI.indexOf("/view") + 5);
            }
            Object urlPerm = cache.getCache("urlPermCache").get(URI);

            perms = new String[1];
            if (urlPerm != null && StringUtils.hasText(urlPerm.toString())) {
                perms[0] = urlPerm.toString();
            } else {
                ResourceService service = (ResourceService) WebUtil.getContext().getBean("resourceService");
                Searchable s = new SearchRequest();
                s.addSearchParam("url", Operator.EQ, URI);
                List<ResourceDTO> resourceDTOList = (List<ResourceDTO>) service.findAll(s);
                if (resourceDTOList != null && resourceDTOList.size() > 0) {
                    perms = new String[resourceDTOList.size()];
                    for (int i = 0; i < resourceDTOList.size(); i++) {
                        ResourceDTO resourceDTO = resourceDTOList.get(i);
                        List<String> list = Lists.newArrayList();
                        list = getPermPattern(resourceDTO, list);
                        Collections.reverse(list);
                        list.add("view");
                        perms[i] = org.apache.commons.lang3.StringUtils.join(list, ":").toString();
                        cache.getCache("urlPermCache").put(URI, perms[i]);
                    }
                    if (perms != null && perms.length > 0) {
                        for (String perm : perms) {
                            if (perm != null && subject.isPermitted(perm)) {
                                isPermitted = true;
                                break;
                            } else {
                                isPermitted = false;
                            }
                        }
                    }
                }
            }
        }
        return isPermitted;
        //      return super.isAccessAllowed(request, response, mappedValue);
    }

    private List<String> getPermPattern(ResourceDTO resource, List<String> list) {
        list.add(resource.getSn());

        ResourceDTO parent = resource.getParent();
        if (parent != null) {
            list = getPermPattern(parent, list);
        }

        return list;
    }

}

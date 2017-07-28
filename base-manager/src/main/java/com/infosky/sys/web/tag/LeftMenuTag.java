package com.infosky.sys.web.tag;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Sort;

import com.google.common.collect.Lists;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.framework.web.WebUtil;
import com.infosky.sys.entity.dto.ResourceDTO;
import com.infosky.sys.service.impl.ResourceService;

/**
 * 左侧菜单
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LeftMenuTag extends SimpleTagSupport {

    /** {@inheritDoc} */

    @Override
    public void doTag() throws JspException, IOException {
        ResourceService service = (ResourceService) WebUtil.getContext().getBean("resourceService");

        Searchable searchable = new SearchRequest();
        searchable.addSearchParam("parent.id", Operator.EQ, "0");
        searchable.addSearchParam("type", Operator.IN, new Object[] {
                "1", "2"
        });
        Sort sort = new Sort(Sort.Direction.ASC, "priority");
        Collection<ResourceDTO> resources = service.findAllBySort(searchable, sort);

        StringBuilder builder = new StringBuilder();
        builder.append("<ul class=\"nav nav-list\" style=\"top: 0px;\">");
        builder.append('\n');

        Subject subject = SecurityUtils.getSubject();

        for (ResourceDTO dto : resources) {
            List<String> list = Lists.newArrayList();
            //查看菜单默认需要有view权限
            list.add("view");
            this.getPermPattern(dto, list);
            Collections.reverse(list);
            if (!subject.isPermitted(StringUtils.join(list, ":"))) {
                continue;
            }
            builder.append(next(dto, subject));
        }

        builder.append('\n');
        builder.append("</ul>");
        getJspContext().getOut().write(builder.toString());
    }

    private String next(ResourceDTO dto, Subject subject) {
        StringBuilder builder = new StringBuilder();
        String url = "#".equals(dto.getUrl()) ? "#" : getJspContext().getAttribute("ctx") + dto.getUrl();

        if (!dto.getChildren().isEmpty() && !checkResourceType(dto.getChildren())) {
            builder.append("<li class=\"hsub\">");
            builder.append('\n');
            builder.append("<a class=\"dropdown-toggle\" href=\"javascript:void(0)\" id=\"" + dto.getId() + "\" data-action=\"" + url + "\">");
            builder.append('\n');
            builder.append("<i class=\"menu-icon fa " + dto.getIcons() + "\"></i>");
            builder.append('\n');
            builder.append("<span class=\"menu-text\"> " + dto.getName() + " </span>");
            builder.append('\n');
            builder.append("<b class=\"arrow fa fa-angle-down\"></b>");
            builder.append("</a>");
            builder.append("<b class=\"arrow\"></b>");

            builder.append("<ul class=\"submenu\">");

            List<ResourceDTO> subList = dto.getChildren();
            for (ResourceDTO sub : subList) {
                List<String> list = Lists.newArrayList();

                //查看菜单默认需要有view权限
                list.add("view");
                this.getPermPattern(sub, list);
                Collections.reverse(list);
                if (!subject.isPermitted(StringUtils.join(list, ":"))) {
                    continue;
                }
                builder.append(next(sub, subject));
            }

            builder.append("</ul>");
        }
        //如果不为元素节点
        else if (!"3".equals(dto.getType())) {

            builder.append("<li>");
            builder.append('\n');
            builder.append("<a href=\"javascript:void(0)\" id=\"" + dto.getId() + "\" data-action=\"" + url + "\">");
            builder.append('\n');
            builder.append("<i class=\"menu-icon fa " + dto.getIcons() + "\"></i>");
            builder.append('\n');
            builder.append("<span class=\"menu-text\"> " + dto.getName() + " </span>");
            builder.append("</a>");
            builder.append("<b class=\"arrow\"></b>");
        }

        builder.append("</li>");

        return builder.toString();
    }

    /**
     * 检查子节点是否都为元素节点,如果是返回true
     * @param children
     * @return
     * @see [类、类#方法、类#成员]
     */
    private boolean checkResourceType(List<ResourceDTO> children) {
        for (ResourceDTO resourceDTO : children) {
            if ("3".equals(resourceDTO.getType())) {
                return true;
            }
        }

        return false;
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

package com.infosky.sys.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.infosky.framework.web.WebUtil;
import com.infosky.sys.entity.dto.RoleDTO;
import com.infosky.sys.service.impl.RoleService;

/**
 * @author Kevin
 *
 */
public class RoleTag extends SimpleTagSupport {

    /**
     * 样式
     */
    private String _class;

    private String id;

    private String name;

    private String value;

    private String placeholder;

    private Boolean firstIsBlank;

    @Override
    public void doTag() throws JspException, IOException {
        RoleService service = (RoleService) WebUtil.getContext().getBean("roleService");

        List<RoleDTO> roles = (List<RoleDTO>) service.findAll();

        StringBuffer buff = new StringBuffer();

        /*buff.append("<select class=\""+_class+"\" id=\""+id+"\"");

        if (name != null) {
        	buff.append(" name=\"" + name + "\"");
        }
        if (placeholder != null) {
        	buff.append(" data-placeholder=\"" + placeholder + "\"");
        }

        buff.append(" >");

        if (firstIsBlank != null && firstIsBlank) {

        	buff.append("<option value=\"\"> </option>");
        }*/
        Subject subject = SecurityUtils.getSubject();
        for (RoleDTO role : roles) {
            if (subject.hasRole(role.getName()) || subject.getPrincipal().toString().equals("admin") || subject.getPrincipal().toString().equals(role.getCreator())) {//是否有该角色权限,管理员用户管理所有角色

                /*buff.append("<option value=\"" + role.getId() + "\"");

                // value为空时判断是否有默认值
                if (StringUtils.isNotEmpty(value) && value.equals(role.getId())) {
                buff.append("selected=\"selected\"");
                }

                buff.append(">" + role.getName() + "</option>");*/

                buff.append("<label class=\"position-relative\">");
                buff.append("<input name=\"" + name + "\" class=\"ace\"");
                buff.append(" roleClass");
                buff.append(" type=\"checkbox\"");
                if (!value.equals(null)) {
                    String[] valueStr = value.split(",");
                    for (String val : valueStr) {
                        if (val.equals(role.getId())) buff.append(" checked=\"checked\"");
                    }
                }
                buff.append(" value=\"" + role.getId() + "\"");
                buff.append(" id=\"" + name + "\"");
                buff.append(" >");
                buff.append("<span class=\"lbl\"></span>");
                buff.append(role.getName());
                buff.append("</label>");
                buff.append("&nbsp;&nbsp;&nbsp;");
            }

        }

        //buff.append("</select>");
        buff.append("<input type=\"hidden\" id=\"roleArr\" name=\"roleArr\">");

        getJspContext().getOut().write(buff.toString());
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public Boolean getFirstIsBlank() {
        return firstIsBlank;
    }

    public void setFirstIsBlank(Boolean firstIsBlank) {
        this.firstIsBlank = firstIsBlank;
    }

}

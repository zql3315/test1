package com.infosky.sys.web.tag;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.infosky.framework.web.WebUtil;
import com.infosky.sys.entity.dto.OperationDTO;
import com.infosky.sys.service.impl.OperationService;

/**
 * 获取服务项目列表
 * @author n005843
 *
 */
public class OperationCheckBox extends SimpleTagSupport {

    /**
     * 样式
     */
    private String _class;

    private String id;

    private String name;

    private String value;

    private String placeholder;

    private Boolean firstIsBlank;

    /**
     * 数据字典标识
     */
    private String datakey;

    @Override
    public void doTag() throws JspException, IOException {
        OperationService service = (OperationService) WebUtil.getContext().getBean("operationService");

        Collection<OperationDTO> items = service.findAll();

        StringBuffer buff = new StringBuffer();

        for (OperationDTO operationDTO : items) {
            buff.append("<label>");
            buff.append("<input name=\"" + name + "\"");
            buff.append(" type=\"checkbox\"");
            if (!value.equals(null)) {
                if (value.contains(operationDTO.getId())) buff.append(" checked=\"checked\"");
            }
            buff.append(" value=\"" + operationDTO.getId() + "\"");
            buff.append(" id=\"" + name + "\"");
            buff.append(" >");
            buff.append(operationDTO.getName());
            buff.append("&nbsp;");
            buff.append("&nbsp;");
            buff.append("&nbsp;");
            buff.append("</label>");

        }

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

    public String getDatakey() {
        return datakey;
    }

    public void setDatakey(String datakey) {
        this.datakey = datakey;
    }
}

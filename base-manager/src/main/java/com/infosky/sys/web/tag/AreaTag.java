package com.infosky.sys.web.tag;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.framework.web.WebUtil;
import com.infosky.sys.entity.dto.AreaDTO;
import com.infosky.sys.service.impl.AreaService;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author n004881
 * @version [版本号, 2015年3月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AreaTag extends SimpleTagSupport {

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
     * 区域层级
     */
    private String level;

    /**
     * 是否热门
     */
    private int isHot = -1;

    /** {@inheritDoc} */

    @Override
    public void doTag() throws JspException, IOException {
        AreaService service = (AreaService) WebUtil.getContext().getBean("areaService");

        Searchable searchable = new SearchRequest();
        searchable.addSearchParam("level", Operator.EQ, level);
        if (isHot > -1) {
            searchable.addSearchParam("isHot", Operator.EQ, isHot);
        }
        Collection<AreaDTO> dics = service.findAll(searchable);

        StringBuffer buff = new StringBuffer();

        buff.append("<select class=\"" + _class + "\" id=\"" + id + "\"");

        if (name != null) {
            buff.append(" name=\"" + name + "\"");
        }
        if (placeholder != null) {
            buff.append(" data-placeholder=\"" + placeholder + "\"");
        }

        buff.append(" >");

        if (firstIsBlank != null && firstIsBlank) {

            buff.append("<option value=\"\"> </option>");
        }

        for (AreaDTO areaDTO : dics) {
            buff.append("<option value=\"" + areaDTO.getId() + "\"");

            // value为空时判断是否有默认值
            if (StringUtils.isNotEmpty(value) && value.equals(areaDTO.getName())) {
                buff.append("selected");
            }

            buff.append(">" + areaDTO.getName() + "</option>");
        }

        buff.append("</select>");

        getJspContext().getOut().write(buff.toString());
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    /**
     * @return 返回 _class
     */
    public String get_class() {
        return _class;
    }

    /**
     * @param 对_class进行赋值
     */
    public void set_class(String _class) {
        this._class = _class;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }

    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 返回 value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param 对value进行赋值
     */
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

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
import com.infosky.sys.entity.dto.DictionaryDTO;
import com.infosky.sys.service.impl.DictionaryService;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年3月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SelectItemTag extends SimpleTagSupport {

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

    /** {@inheritDoc} */

    @Override
    public void doTag() throws JspException, IOException {
        DictionaryService service = (DictionaryService) WebUtil.getContext().getBean("dictionaryService");

        Searchable searchable = new SearchRequest();
        searchable.addSearchParam("datakey", Operator.EQ, datakey);
        //searchable.addSearchParam("type", Operator.IN, new Object[] {"1", "2"});
        Collection<DictionaryDTO> dics = service.findAll(searchable);

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

        for (DictionaryDTO dictionaryDTO : dics) {
            buff.append("<option value=\"" + dictionaryDTO.getItemcode() + "\"");

            //value为空时判断是否有默认值
            if (StringUtils.isNotEmpty(value) && value.equals(dictionaryDTO.getItemcode())) {
                buff.append("selected");
            }

            buff.append(">" + dictionaryDTO.getItemvalue() + "</option>");
        }

        buff.append("</select>");

        getJspContext().getOut().write(buff.toString());
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

    /**
     * @return 返回 datakey
     */
    public String getDatakey() {
        return datakey;
    }

    /**
     * @param 对datakey进行赋值
     */
    public void setDatakey(String datakey) {
        this.datakey = datakey;
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

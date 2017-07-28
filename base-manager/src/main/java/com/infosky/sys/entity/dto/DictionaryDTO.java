package com.infosky.sys.entity.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.infosky.framework.entity.dto.DTO;
import com.infosky.sys.entity.po.Dictionary;

/**
 * 工程项目
 * 
 * @author n004881
 * @version [版本号, 2015年2月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DictionaryDTO extends DTO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * KEY
     */
    private String datakey;

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
     * 编码
     */
    private String itemcode;

    /**
     * @return 返回 itemcode
     */
    public String getItemcode() {
        return itemcode;
    }

    /**
     * @param 对itemcode进行赋值
     */
    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    /**
     * 值
     */
    private String itemvalue;

    /**
     * @return 返回 itemvalue
     */
    public String getItemvalue() {
        return itemvalue;
    }

    /**
     * @param 对itemvalue进行赋值
     */
    public void setItemvalue(String itemvalue) {
        this.itemvalue = itemvalue;
    }

    /**
     * 父级
     */
    private DictionaryDTO parent;

    @JsonIgnore
    private List<Dictionary> children = Lists.newArrayList();

    public List<Dictionary> getChildren() {
        return children;
    }

    public void setChildren(List<Dictionary> children) {
        this.children = children;
    }

    /**
     * @return 返回 parent
     */
    public DictionaryDTO getParent() {
        return parent;
    }

    /**
     * @param 对parent进行赋值
     */
    public void setParent(DictionaryDTO parent) {
        this.parent = parent;
    }

}

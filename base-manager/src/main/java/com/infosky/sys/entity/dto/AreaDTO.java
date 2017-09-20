package com.infosky.sys.entity.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.infosky.framework.entity.dto.DTO;

/**
 * 区域对象
 * 
 * @author n004881
 */
public class AreaDTO extends DTO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private int code;

    /**
     * 等级
     */
    private int level;

    /**
     * 权重
     */
    private int weight;

    /**
     * 热门类型0默认,1热门城市 其他待扩展
     */
    private int isHot;

    /**
     * 父节点
     */
    private AreaDTO parent;

    /**
     * 权限表外键
     */
    @JsonIgnore
    private List<AreaDTO> children = Lists.newArrayList();

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public AreaDTO getParent() {
        return parent;
    }

    public void setParent(AreaDTO parent) {
        this.parent = parent;
    }

    public List<AreaDTO> getChildren() {
        return children;
    }

    public void setChildren(List<AreaDTO> children) {
        this.children = children;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}

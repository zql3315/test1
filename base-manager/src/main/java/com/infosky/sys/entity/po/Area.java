package com.infosky.sys.entity.po;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;
import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

@Entity
@Table(name = "sys_area")
@Comment("区域表")
public class Area extends PO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * 名称
     */
    @Comment("名称")
    private String name;

    /**
     * 编码
     */
    @Comment("编码")
    private int code;

    /**
     * 等级
     */
    @Comment("等级")
    private int level;

    /**
     * 热门类型
     */
    @Comment("0默认,1热门城市 其他待扩展")
    private int isHot;

    /**
     * 权重
     */
    @Comment("权重")
    private int weight;

    /**
     * 父节点
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private Area parent;

    /**
     * 权限表外键
     */
    @OneToMany(mappedBy = "parent", cascade = {
            CascadeType.REMOVE, CascadeType.PERSIST
    }, orphanRemoval = true)
    private List<Area> children = Lists.newArrayList();

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

    public Area getParent() {
        return parent;
    }

    public void setParent(Area parent) {
        this.parent = parent;
    }

    public List<Area> getChildren() {
        return children;
    }

    public void setChildren(List<Area> children) {
        this.children = children;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}

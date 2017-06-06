package com.infosky.sys.entity.po;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.google.common.collect.Lists;
import com.infosky.framework.entity.po.PO;

@Entity
@Table(name = "sys_dictionary")
public class Dictionary extends PO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * KEY
     */
    @Column(name = "DATAKEY")
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
    @Column(name = "ITEMCODE")
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
    @Column(name = "ITEMVALUE")
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
    @ManyToOne
    @JoinColumn(name = "pid")
    private Dictionary parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
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
    public Dictionary getParent() {
        return parent;
    }

    /**
     * @param 对parent进行赋值
     */
    public void setParent(Dictionary parent) {
        this.parent = parent;
    }

}

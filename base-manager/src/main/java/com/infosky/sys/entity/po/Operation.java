package com.infosky.sys.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

@Entity
@Table(name = "sys_operation")
@Comment("操作表")
public class Operation extends PO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    /**
     * 名称
     */
    @Column(name = "NAME")
    private String name;

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
     * 简称
     */
    @Column(name = "SN")
    private String sn;

    /**
     * @return 返回 sn
     */
    public String getSn() {
        return sn;
    }

    /**
     * @param 对sn进行赋值
     */
    public void setSn(String sn) {
        this.sn = sn;
    }
}

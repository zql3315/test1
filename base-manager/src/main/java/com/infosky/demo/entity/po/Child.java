/*
 * 文 件 名:  Demo.java
 * 版    权:  XXXX Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  xx
 * 修改时间:  xx年xx月xx日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.infosky.demo.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.infosky.framework.entity.po.PO;

/**
 * 
 * 
 * @author xx
 * @version [版本号, xx年xx月xx日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Entity
@Table(name = "t_child")
public class Child extends PO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = 5821577028213006758L;

    /**
     * 
     */
    @Column(name = "name")
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

    @ManyToOne
    @JoinColumn(name = "did")
    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * 
     */
    @Column(name = "age")
    private int age;

    /**
     * @return 返回 age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param 对age进行赋值
     */
    public void setAge(int age) {
        this.age = age;
    }
}

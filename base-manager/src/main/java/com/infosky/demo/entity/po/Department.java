package com.infosky.demo.entity.po;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Sets;
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
@Table(name = "t_department")
public class Department extends PO<String> {

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
     * 
     */
    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "department", cascade = {
        CascadeType.REMOVE
    }, orphanRemoval = true)
    private Set<Child> child = Sets.newHashSet();

    public Set<Child> getChild() {
        return child;
    }

    public void setChild(Set<Child> child) {
        this.child = child;
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

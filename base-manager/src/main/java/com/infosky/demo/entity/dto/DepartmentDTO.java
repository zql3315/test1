package com.infosky.demo.entity.dto;

import java.util.Set;

import com.google.common.collect.Sets;
import com.infosky.framework.entity.dto.DTO;

/**
 * 
 * 
 * @author xx
 * @version [版本号, xx年xx月xx日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DepartmentDTO extends DTO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = 5821577028213006758L;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private int age;

    private Set<ChildDTO> child = Sets.newHashSet();

    public Set<ChildDTO> getChild() {
        return child;
    }

    public void setChild(Set<ChildDTO> child) {
        this.child = child;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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

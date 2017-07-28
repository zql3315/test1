package com.infosky.demo.entity.dto;

import com.infosky.framework.entity.dto.DTO;

public class ChildDTO extends DTO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = 5821577028213006758L;

    /**
     * 
     */
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

    private DepartmentDTO department;

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    /**
     * 
     */
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

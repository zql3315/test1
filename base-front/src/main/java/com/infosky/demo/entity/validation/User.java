package com.infosky.demo.entity.validation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class User {

    private String username;

    private String password;

    private int age;
    
    
    @Pattern(regexp = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$", message = "身份证不合法")
    private String idCard;

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "[a-zA-Z0-9_]{5,10}", message = "只能输入5到10位的字母或数字")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "{password.empty}")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Min(value = 10, message = "年龄的最小值为10")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}

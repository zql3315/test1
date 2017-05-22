package com.infosky.build.entity.dto;

import com.infosky.framework.entity.dto.DTO;

/**
 * 工程项目
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ProjectDTO extends DTO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    private String name;

    private String mName;

    private String location;

    private String rPackage;

    private String staticDir;

    private String ftlDir;

    /**
     * 工程结构 0-normal 1-maven
     */
    private String struct;

    /**
     * 工程类型  0-normal 1-web
     */
    private String type;

    private String viewDir;

    private String url;

    private String user;

    private String password;

    private String driver;

    private String createConfig;

    private String createJava;

    private String createView;

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
     * @return 返回 location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param 对location进行赋值
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return 返回 rPackage
     */
    public String getrPackage() {
        return rPackage;
    }

    /**
     * @param 对rPackage进行赋值
     */
    public void setrPackage(String rPackage) {
        this.rPackage = rPackage;
    }

    /**
     * @return 返回 staticDir
     */
    public String getStaticDir() {
        return staticDir;
    }

    /**
     * @param 对staticDir进行赋值
     */
    public void setStaticDir(String staticDir) {
        this.staticDir = staticDir;
    }

    /**
     * @return 返回 struct
     */
    public String getStruct() {
        return struct;
    }

    /**
     * @param 对struct进行赋值
     */
    public void setStruct(String struct) {
        this.struct = struct;
    }

    /**
     * @return 返回 type
     */
    public String getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return 返回 viewDir
     */
    public String getViewDir() {
        return viewDir;
    }

    /**
     * @param 对viewDir进行赋值
     */
    public void setViewDir(String viewDir) {
        this.viewDir = viewDir;
    }

    /**
     * @return 返回 url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param 对url进行赋值
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return 返回 user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param 对user进行赋值
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return 返回 password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param 对password进行赋值
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return 返回 driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param 对driver进行赋值
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return 返回 mName
     */
    public String getmName() {
        return mName;
    }

    /**
     * @param 对mName进行赋值
     */
    public void setmName(String mName) {
        this.mName = mName;
    }

    /**
     * @return 返回 ftlDir
     */
    public String getFtlDir() {
        return ftlDir;
    }

    /**
     * @param 对ftlDir进行赋值
     */
    public void setFtlDir(String ftlDir) {
        this.ftlDir = ftlDir;
    }

    /**
     * @return 返回 createConfig
     */
    public String getCreateConfig() {
        return createConfig;
    }

    /**
     * @param 对createConfig进行赋值
     */
    public void setCreateConfig(String createConfig) {
        this.createConfig = createConfig;
    }

    /**
     * @return 返回 createJava
     */
    public String getCreateJava() {
        return createJava;
    }

    /**
     * @param 对createJava进行赋值
     */
    public void setCreateJava(String createJava) {
        this.createJava = createJava;
    }

    /**
     * @return 返回 createView
     */
    public String getCreateView() {
        return createView;
    }

    /**
     * @param 对createView进行赋值
     */
    public void setCreateView(String createView) {
        this.createView = createView;
    }

}

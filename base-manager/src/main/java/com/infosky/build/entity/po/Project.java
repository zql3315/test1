package com.infosky.build.entity.po;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;
import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

/**
 * 工程项目
 * 
 */
/**
 * @author n004881
 *
 */
@Entity
@Table(name = "bs_project")
public class Project extends PO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1684133362669153672L;

    @Column(name = "project_name")
    @Comment("名称")
    private String name;

    @Comment("模块名称")
    private String mName;

    @Comment("工程位置")
    @Column(name = "project_location")
    private String location;

    @Comment("顶级包名")
    private String rPackage;

    @Comment("静态资源目录")
    private String staticDir;

    @Comment("FTL模板目录")
    private String ftlDir;

    //级联删除但不级联新建
    @OneToMany(mappedBy = "project", cascade = {
        CascadeType.REMOVE
    }, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProjectTable> tables = Lists.newArrayList();

    /**
     * 工程结构 0-normal 1-maven
     */
    @Comment("工程结构 0-normal 1-maven")
    private String struct;

    /**
     * 工程类型  0-normal 1-web
     */
    @Comment("工程类型  0-normal 1-web")
    private String type;

    @Comment("视图文件目录")
    private String viewDir;

    @Comment("数据库URL")
    private String url;

    @Column(name = "project_user")
    @Comment("数据库用户名")
    private String user;

    @Column(name = "project_password")
    @Comment("数据库密码")
    private String password;

    @Comment("驱动")
    private String driver;

    @Comment("配置文件生成")
    private String createConfig;

    @Comment("JAVA文件生成")
    private String createJava;

    @Comment("生成视图文件")
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
     * @return 返回 tables
     */
    public List<ProjectTable> getTables() {
        return tables;
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
     * @param 对tables进行赋值
     */
    public void setTables(List<ProjectTable> tables) {
        this.tables = tables;
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

package com.infosky.adv.entity.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

/**
 * 
 * 广告位管理
 * @author  n003588
 */
@Entity
@Table(name = "adver_manager")
public class AdverManager extends PO<String> {

    private static final long serialVersionUID = 1376551965461739545L;

    @Comment(value = "广告位名称")
    private String advname;

    @Comment(value = "广告位位置")
    private String advposition;

    @Comment(value = "图片存储路径")
    private String imgpath;

    @Comment(value = "广告位url")
    private String advurl;

    @Column(name = "createtime", updatable = false)
    @Comment(value = "创建时间")
    private Date createtime;

    @Column(name = "modifytime")
    @Comment(value = "修改时间 ")
    private Date modifytime;

    @Column(name = "description")
    @Comment(value = "描述内容")
    private String description;

    @Column(name = "advno")
    @Comment(value = "广告位编号，1,2,3,4,5等")
    private Integer advno;

    @Column(name = "isdisplay")
    @Comment(value = "是否显示 1显示 0隐藏 ")
    private int isdisplay;

    @Column(name = "advspecification")
    @Comment(value = " 广告位规格，可以用字典表维护 ，比如：100*100,300*200等 ")
    private String advspecification;

    @Column(name = "advtype")
    @Comment(value = "广告位类型，可以用字典表维护，比如是首页广告位，详情页广告位，列表页广告位等 ")
    private Integer advtype;

    @Column(name = "starttime")
    @Comment(value = "开始时间 ")
    private Date starttime;

    @Column(name = "endtime")
    @Comment(value = "结束时间 ")
    private Date endtime;

    @Column(name = "terminaltype")
    @Comment(value = "终端类型  比如手机，PC等，可以用字典表维护 ")
    private Integer terminaltype;

    @Column(name = "citycode")
    @Comment(value = " 城市编号，可以设置为城市专属广告位 ")
    private String citycode;

    public String getAdvname() {
        return advname;
    }

    public void setAdvname(String advname) {
        this.advname = advname;
    }

    public String getAdvposition() {
        return advposition;
    }

    public void setAdvposition(String advposition) {
        this.advposition = advposition;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getAdvurl() {
        return advurl;
    }

    public void setAdvurl(String advurl) {
        this.advurl = advurl;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getAdvno() {
        return advno;
    }

    public void setAdvno(Integer advno) {
        this.advno = advno;
    }

    public int getIsdisplay() {
        return isdisplay;
    }

    public void setIsdisplay(int isdisplay) {
        this.isdisplay = isdisplay;
    }

    public String getAdvspecification() {
        return advspecification;
    }

    public void setAdvspecification(String advspecification) {
        this.advspecification = advspecification;
    }

    public Integer getAdvtype() {
        return advtype;
    }

    public void setAdvtype(Integer advtype) {
        this.advtype = advtype;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getTerminaltype() {
        return terminaltype;
    }

    public void setTerminaltype(Integer terminaltype) {
        this.terminaltype = terminaltype;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

}

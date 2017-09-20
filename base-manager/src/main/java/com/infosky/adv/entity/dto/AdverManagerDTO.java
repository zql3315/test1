package com.infosky.adv.entity.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.infosky.framework.entity.dto.DTO;
import com.infosky.framework.entity.utils.CustomJsonDateDeserialize;
import com.infosky.framework.entity.utils.CustomJsonDateSerializer;

/**
 * 
 * 广告位管理
 * @author  n003588
 */
public class AdverManagerDTO extends DTO<String> {

    private static final long serialVersionUID = 209843842885810427L;

    /**
     * 广告位名称
     */
    private String advname;

    /**
     * 广告位位置，可以用字典表维护
     */
    private String advposition;

    /**
     * 广告位位置中文名称
     */
    private String advpositionname;

    /**
     * 广告位图片地址
     */
    private String imgpath;

    /**
     * 广告位连接
     */
    private String advurl;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomJsonDateSerializer.class)
    @JsonDeserialize(using = CustomJsonDateDeserialize.class)
    private Date createtime = new Date();

    /**
     * 修改时间 
     */
    @JsonSerialize(using = CustomJsonDateSerializer.class)
    @JsonDeserialize(using = CustomJsonDateDeserialize.class)
    private Date modifytime;

    /**
     * 广告位描述
     */
    private String description;

    /**
     * 广告位编号，1,2,3,4,5等
     */
    private int advno;

    /**
     * 是否显示  1：显示，2：不显示
     */
    private int isdisplay;

    /**
     * 广告位规格，可以用字典表维护 ，比如：100*100,300*200等
     */
    private String advspecification;

    /**
     * 广告位类型，可以用字典表维护，比如是首页广告位，详情页广告位，列表页广告位等
     */
    private Integer advtype;

    /**
     * 广告位类型中文名称
     */
    private String advtypename;

    /**
     * 广告位开始时间
     */
    @JsonSerialize(using = CustomJsonDateSerializer.class)
    @JsonDeserialize(using = CustomJsonDateDeserialize.class)
    private Date starttime;

    /**
     * 广告位结束时间
     */
    @JsonSerialize(using = CustomJsonDateSerializer.class)
    @JsonDeserialize(using = CustomJsonDateDeserialize.class)
    private Date endtime;

    /**
     * 终端类型  比如手机，PC等，可以用字典表维护
     */
    private Integer terminaltype;

    /**
     * 城市编号，可以设置为城市专属广告位
     */
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

    public String getAdvtypename() {
        return advtypename;
    }

    public void setAdvtypename(String advtypename) {
        this.advtypename = advtypename;
    }

    public String getAdvpositionname() {
        return advpositionname;
    }

    public void setAdvpositionname(String advpositionname) {
        this.advpositionname = advpositionname;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

}

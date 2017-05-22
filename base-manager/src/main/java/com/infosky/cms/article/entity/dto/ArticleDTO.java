package com.infosky.cms.article.entity.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.infosky.framework.entity.dto.DTO;
import com.infosky.framework.entity.utils.CustomJsonDateDeserialize;

/**
 * 工程项目
 * 
 * @author xx
 * @version [版本号, xx年xx月xx日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ArticleDTO extends DTO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = 7563115113022595025L;

    private String title;

    private Date createTime = new Date();

    private String content;

    private String contentId;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    @JsonDeserialize(using = CustomJsonDateDeserialize.class)
    private Date updatetime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}

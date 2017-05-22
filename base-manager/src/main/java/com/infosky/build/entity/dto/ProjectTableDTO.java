package com.infosky.build.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infosky.framework.entity.dto.DTO;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ProjectTableDTO extends DTO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 6969784808225150815L;

    private ProjectDTO project;

    @JsonIgnore
    private TableDTO table;

    /**
     * @return 返回 project
     */
    public ProjectDTO getProject() {
        return project;
    }

    /**
     * @param 对project进行赋值
     */
    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    /**
     * @return 返回 table
     */
    public TableDTO getTable() {
        return table;
    }

    /**
     * @param 对table进行赋值
     */
    public void setTable(TableDTO table) {
        this.table = table;
    }

}

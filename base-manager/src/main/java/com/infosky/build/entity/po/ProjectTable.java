package com.infosky.build.entity.po;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.infosky.framework.entity.po.PO;

/**
 * @author n004881
 *
 */
@Entity
@javax.persistence.Table(name = "bs_project_table")
public class ProjectTable extends PO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 4332682115163898284L;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "tid")
    private Table table;

    /**
     * @return 返回 project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param 对project进行赋值
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * @return 返回 table
     */
    public Table getTable() {
        return table;
    }

    /**
     * @param 对table进行赋值
     */
    public void setTable(Table table) {
        this.table = table;
    }
}

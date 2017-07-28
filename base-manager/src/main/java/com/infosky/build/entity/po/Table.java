package com.infosky.build.entity.po;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.google.common.collect.Lists;
import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

/**
 * 表
 * @author n004881
 *
 */
@Entity
@javax.persistence.Table(name = "bs_table")
public class Table extends PO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 239310406090142492L;

    @Comment("数据表名")
    private String name;

    @Comment("表对应的实体对象名")
    private String eName;

    @Comment("简称")
    private String sn;

    @Comment("描述")
    private String description;

    @OneToMany(mappedBy = "table", cascade = {
            CascadeType.PERSIST, CascadeType.REMOVE
    }, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProjectTable> projects = Lists.newArrayList();

    @Comment("是否生成成持久层对象")
    private String createPO;

    @Comment("持久层对象模板")
    private String poTemplate;

    @Comment("数据传输层对象")
    private String createDTO;

    @Comment("数据传输层对象模板")
    private String dtoTemplate;

    @Comment("是否生成数据访问层")
    private String createDAO;

    @Comment("数据访问层模板")
    private String daoTemplate;

    @Comment("是否生成服务层")
    private String createService;

    @Comment("服务层模板")
    private String serviceTemplate;

    @Comment("视图控制层")
    private String createController;

    @Comment("视图控制层模板")
    private String controllerTemplate;

    @Comment("新增视图")
    private String createAddView;

    @Comment("新增视图模板")
    private String addViewTemplate;

    @Comment("修改视图")
    private String createModView;

    @Comment("修改视图模板")
    private String modViewTemplate;

    @Comment("列表查询视图")
    private String createListView;

    @Comment("列表查询视图模板")
    private String listViewTemplate;

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
     * @return 返回 eName
     */
    public String geteName() {
        return eName;
    }

    /**
     * @param 对eName进行赋值
     */
    public void seteName(String eName) {
        this.eName = eName;
    }

    /**
     * @return 返回 projects
     */
    public List<ProjectTable> getProjects() {
        return projects;
    }

    /**
     * @param 对projects进行赋值
     */
    public void setProjects(List<ProjectTable> projects) {
        this.projects = projects;
    }

    /**
     * @return 返回 sn
     */
    public String getSn() {
        return sn;
    }

    /**
     * @param 对sn进行赋值
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * @return 返回 desciption
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param 对desciption进行赋值
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 返回 createPO
     */
    public String getCreatePO() {
        return createPO;
    }

    /**
     * @param 对createPO进行赋值
     */
    public void setCreatePO(String createPO) {
        this.createPO = createPO;
    }

    /**
     * @return 返回 poTemplate
     */
    public String getPoTemplate() {
        return poTemplate;
    }

    /**
     * @param 对poTemplate进行赋值
     */
    public void setPoTemplate(String poTemplate) {
        this.poTemplate = poTemplate;
    }

    /**
     * @return 返回 createDTO
     */
    public String getCreateDTO() {
        return createDTO;
    }

    /**
     * @param 对createDTO进行赋值
     */
    public void setCreateDTO(String createDTO) {
        this.createDTO = createDTO;
    }

    /**
     * @return 返回 dtoTemplate
     */
    public String getDtoTemplate() {
        return dtoTemplate;
    }

    /**
     * @param 对dtoTemplate进行赋值
     */
    public void setDtoTemplate(String dtoTemplate) {
        this.dtoTemplate = dtoTemplate;
    }

    /**
     * @return 返回 createDAO
     */
    public String getCreateDAO() {
        return createDAO;
    }

    /**
     * @param 对createDAO进行赋值
     */
    public void setCreateDAO(String createDAO) {
        this.createDAO = createDAO;
    }

    /**
     * @return 返回 daoTemplate
     */
    public String getDaoTemplate() {
        return daoTemplate;
    }

    /**
     * @param 对daoTemplate进行赋值
     */
    public void setDaoTemplate(String daoTemplate) {
        this.daoTemplate = daoTemplate;
    }

    /**
     * @return 返回 createService
     */
    public String getCreateService() {
        return createService;
    }

    /**
     * @param 对createService进行赋值
     */
    public void setCreateService(String createService) {
        this.createService = createService;
    }

    /**
     * @return 返回 serviceTemplate
     */
    public String getServiceTemplate() {
        return serviceTemplate;
    }

    /**
     * @param 对serviceTemplate进行赋值
     */
    public void setServiceTemplate(String serviceTemplate) {
        this.serviceTemplate = serviceTemplate;
    }

    /**
     * @return 返回 createController
     */
    public String getCreateController() {
        return createController;
    }

    /**
     * @param 对createController进行赋值
     */
    public void setCreateController(String createController) {
        this.createController = createController;
    }

    /**
     * @return 返回 controllerTemplate
     */
    public String getControllerTemplate() {
        return controllerTemplate;
    }

    /**
     * @param 对controllerTemplate进行赋值
     */
    public void setControllerTemplate(String controllerTemplate) {
        this.controllerTemplate = controllerTemplate;
    }

    /**
     * @return 返回 createAddView
     */
    public String getCreateAddView() {
        return createAddView;
    }

    /**
     * @param 对createAddView进行赋值
     */
    public void setCreateAddView(String createAddView) {
        this.createAddView = createAddView;
    }

    /**
     * @return 返回 addViewTemplate
     */
    public String getAddViewTemplate() {
        return addViewTemplate;
    }

    /**
     * @param 对addViewTemplate进行赋值
     */
    public void setAddViewTemplate(String addViewTemplate) {
        this.addViewTemplate = addViewTemplate;
    }

    /**
     * @return 返回 createModView
     */
    public String getCreateModView() {
        return createModView;
    }

    /**
     * @param 对createModView进行赋值
     */
    public void setCreateModView(String createModView) {
        this.createModView = createModView;
    }

    /**
     * @return 返回 modViewTemplate
     */
    public String getModViewTemplate() {
        return modViewTemplate;
    }

    /**
     * @param 对modViewTemplate进行赋值
     */
    public void setModViewTemplate(String modViewTemplate) {
        this.modViewTemplate = modViewTemplate;
    }

    /**
     * @return 返回 createListView
     */
    public String getCreateListView() {
        return createListView;
    }

    /**
     * @param 对createListView进行赋值
     */
    public void setCreateListView(String createListView) {
        this.createListView = createListView;
    }

    /**
     * @return 返回 listViewTemplate
     */
    public String getListViewTemplate() {
        return listViewTemplate;
    }

    /**
     * @param 对listViewTemplate进行赋值
     */
    public void setListViewTemplate(String listViewTemplate) {
        this.listViewTemplate = listViewTemplate;
    }

}

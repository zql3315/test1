package com.infosky.build.entity.dto;

import java.util.List;

import com.google.common.collect.Lists;
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
public class TableDTO extends DTO<String> {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 4715935708143233890L;

    private String name;

    private String eName;

    private String sn;

    private String description;

    private List<ProjectTableDTO> projects = Lists.newArrayList();;

    private String createPO;

    private String poTemplate;

    private String createDTO;

    private String dtoTemplate;

    private String createDAO;

    private String daoTemplate;

    private String createService;

    private String serviceTemplate;

    private String createController;

    private String controllerTemplate;

    private String createAddView;

    private String addViewTemplate;

    private String createModView;

    private String modViewTemplate;

    private String createListView;

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
    public List<ProjectTableDTO> getProjects() {
        return projects;
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
     * @param 对projects进行赋值
     */
    public void setProjects(List<ProjectTableDTO> projects) {
        this.projects = projects;
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
    public void setDesciption(String description) {
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

    /**
     * @param 对description进行赋值
     */
    public void setDescription(String description) {
        this.description = description;
    }
}

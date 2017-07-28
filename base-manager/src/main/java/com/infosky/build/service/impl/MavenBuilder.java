package com.infosky.build.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Maps;
import com.infosky.build.entity.dto.Column;
import com.infosky.build.entity.po.Project;
import com.infosky.build.entity.po.ProjectTable;
import com.infosky.build.entity.po.Table;
import com.infosky.build.helper.EngineFactory;
import com.infosky.build.helper.Query;
import com.infosky.build.service.ProjectBuilder;
import com.infosky.common.util.FreeMarkers;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年3月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MavenBuilder implements ProjectBuilder {

    /** {@inheritDoc} */

    public String getWebAppPath() {
        return "src/main/webapp";
    }

    /** {@inheritDoc} */

    public String getSrcPath() {
        return "src/main/java";
    }

    /** {@inheritDoc} */

    public String getResourcePath() {
        return "src/main/resources";
    }

    /** {@inheritDoc} 
     * @throws IOException */

    public void createXMLFile(com.infosky.build.entity.po.Project p) throws IOException {
        //工程路径
        String projectRoot = p.getLocation() + File.separator + p.getName();

        //POM.xml、web。xml
        Map<String, Object> model = Maps.newHashMap();
        model.put("project", p);

        //classpath:/ftl/
        Configuration cfg = FreeMarkers.buildConfiguration(p.getFtlDir());
        cfg.setDefaultEncoding("UTF-8");
        Template template = cfg.getTemplate("web_xml.ftl");
        String web_xml = FreeMarkers.renderTemplate(template, model);
        FileUtils.writeStringToFile(new File(projectRoot + File.separator + this.getWebAppPath() + "/WEB-INF/web.xml"), web_xml, "utf-8");

        template = cfg.getTemplate("pom_xml.ftl");
        String pom_xml = FreeMarkers.renderTemplate(template, model);
        FileUtils.writeStringToFile(new File(projectRoot + File.separator + "pom.xml"), pom_xml, "utf-8");

        //配置文件 application、springmvc
        template = cfg.getTemplate("jdbc_properties.ftl");
        String jdbc_properties = FreeMarkers.renderTemplate(template, model);
        FileUtils.writeStringToFile(new File(projectRoot + File.separator + this.getResourcePath() + File.separator + "jdbc.properties"), jdbc_properties, "utf-8");

        template = cfg.getTemplate("application_xml.ftl");
        String application_xml = FreeMarkers.renderTemplate(template, model);
        FileUtils.writeStringToFile(new File(projectRoot + File.separator + this.getResourcePath() + File.separator + "applicationContext.xml"), application_xml, "utf-8");

        template = cfg.getTemplate("springmvc_xml.ftl");
        String springmvc_xml = FreeMarkers.renderTemplate(template, model);
        FileUtils.writeStringToFile(new File(projectRoot + File.separator + this.getWebAppPath() + "/WEB-INF" + File.separator + "spring-mvc.xml"), springmvc_xml, "utf-8");
    }

    public void createJavaFile(List<ProjectTable> ptList) throws IOException {
        for (ProjectTable projectTable : ptList) {
            Project project = projectTable.getProject();
            //工程路径
            String projectRoot = project.getLocation() + File.separator + project.getName();

            Map<String, Object> model = Maps.newHashMap();
            model.put("project", project);

            try {
                //classpath:/ftl/
                Configuration cfg = FreeMarkers.buildConfiguration(project.getFtlDir());
                cfg.setDefaultEncoding("UTF-8");

                //查询驱动
                Query engine = EngineFactory.buildEQuery(project.getDriver(), project.getUrl(), project.getUser(), project.getPassword());
                Table table = projectTable.getTable();

                List<Column> columns = engine.getColumns(table.getName());

                model.put("columns", columns);
                model.put("table", table);

                Template template = null;

                if ("1".equals(table.getCreatePO())) {
                    //创建实体类
                    template = cfg.getTemplate(table.getPoTemplate());
                    String poContext = FreeMarkers.renderTemplate(template, model);
                    FileUtils.writeStringToFile(new File(projectRoot + File.separator + this.getSrcPath() + File.separator + project.getrPackage().replace(".", File.separator) + File.separator
                            + project.getmName() + File.separator + "entity/po/" + table.geteName() + ".java"), poContext, "utf-8");
                }

                if ("1".equals(table.getCreateDTO())) {
                    template = cfg.getTemplate(table.getDtoTemplate());
                    String dtoContext = FreeMarkers.renderTemplate(template, model);
                    FileUtils.writeStringToFile(new File(projectRoot + File.separator + this.getSrcPath() + File.separator + project.getrPackage().replace(".", File.separator) + File.separator
                            + project.getmName() + File.separator + "entity/dto/" + table.geteName() + "DTO.java"), dtoContext, "utf-8");
                }

                if ("1".equals(table.getCreateDAO())) {
                    template = cfg.getTemplate(table.getDaoTemplate());
                    String daoContext = FreeMarkers.renderTemplate(template, model);
                    FileUtils.writeStringToFile(new File(projectRoot + File.separator + this.getSrcPath() + File.separator + project.getrPackage().replace(".", File.separator) + File.separator
                            + project.getmName() + File.separator + "dao/" + table.geteName() + "DAO.java"), daoContext, "utf-8");
                }

                if ("1".equals(table.getCreateService())) {
                    template = cfg.getTemplate(table.getServiceTemplate());
                    String serviceContext = FreeMarkers.renderTemplate(template, model);
                    FileUtils.writeStringToFile(new File(projectRoot + File.separator + this.getSrcPath() + File.separator + project.getrPackage().replace(".", File.separator) + File.separator
                            + project.getmName() + File.separator + "service/impl/" + table.geteName() + "Service.java"), serviceContext, "utf-8");
                }

                if ("1".equals(table.getCreateController())) {
                    template = cfg.getTemplate(table.getControllerTemplate());
                    String webContext = FreeMarkers.renderTemplate(template, model);
                    FileUtils.writeStringToFile(new File(projectRoot + File.separator + this.getSrcPath() + File.separator + project.getrPackage().replace(".", File.separator) + File.separator
                            + project.getmName() + File.separator + "web/" + table.geteName() + "Controller.java"), webContext, "utf-8");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createViewFile(List<ProjectTable> ptList) throws IOException {
        //表级别  粒度生成
        for (ProjectTable projectTable : ptList) {
            Project project = projectTable.getProject();
            //工程路径
            String projectRoot = project.getLocation() + File.separator + project.getName();

            Map<String, Object> model = Maps.newHashMap();
            model.put("project", project);

            try {
                //classpath:/ftl/
                Configuration cfg = FreeMarkers.buildConfiguration(project.getFtlDir());
                cfg.setDefaultEncoding("UTF-8");

                //查询驱动
                Query engine = EngineFactory.buildEQuery(project.getDriver(), project.getUrl(), project.getUser(), project.getPassword());
                Table table = projectTable.getTable();

                List<Column> columns = engine.getColumns(table.getName());

                model.put("columns", columns);
                model.put("table", table);

                Template template = null;

                if ("1".equals(table.getCreateListView())) {
                    template = cfg.getTemplate(table.getListViewTemplate());
                    String list_viewContext = FreeMarkers.renderTemplate(template, model);
                    FileUtils.writeStringToFile(new File(projectRoot + File.separator + this.getWebAppPath() + "/WEB-INF" + File.separator + project.getViewDir() + File.separator + table.getSn()
                            + File.separator + "list.jsp"), list_viewContext, "utf-8");

                }

                if ("1".equals(table.getCreateAddView())) {
                    template = cfg.getTemplate(table.getAddViewTemplate());
                    String add_viewContext = FreeMarkers.renderTemplate(template, model);
                    FileUtils.writeStringToFile(new File(projectRoot + File.separator + this.getWebAppPath() + "/WEB-INF" + File.separator + project.getViewDir() + File.separator + table.getSn()
                            + File.separator + "add.jsp"), add_viewContext, "utf-8");

                }

                if ("1".equals(table.getCreateModView())) {
                    template = cfg.getTemplate(table.getModViewTemplate());
                    String edit_viewContext = FreeMarkers.renderTemplate(template, model);
                    FileUtils.writeStringToFile(new File(projectRoot + File.separator + this.getWebAppPath() + "/WEB-INF" + File.separator + project.getViewDir() + File.separator + table.getSn()
                            + File.separator + "edit.jsp"), edit_viewContext, "utf-8");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

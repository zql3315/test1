package com.infosky.build.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosky.build.dao.ProjectDAO;
import com.infosky.build.entity.dto.ProjectDTO;
import com.infosky.build.entity.po.Project;
import com.infosky.build.service.ProjectBuilder;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
public class ProjectService extends JpaService<Project, ProjectDTO, PageResult<ProjectDTO>, String> {

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    protected DAO<Project, String> getDAO() {
        return projectDAO;
    }

    public void build(List<ProjectDTO> projects) {
        for (ProjectDTO projectDTO : projects) {
            Project project = projectDAO.findOne(projectDTO.getId());

            try {
                //工程路径
                String projectRoot = project.getLocation() + File.separator + project.getName();
                //创建工程目录 如：demo 
                FileUtils.forceMkdir(new File(projectRoot));
                //静态资源目录
                //FileUtils.forceMkdir(new File(projectRoot + File.separator + "src/main/webapp/static"));

                ProjectBuilder builder = null;
                if ("1".equals(project.getType()) && "1".equals(project.getStruct())) {
                    builder = new MavenBuilder();
                }

                builder.createXMLFile(project);
                builder.createJavaFile(project.getTables());
                builder.createViewFile(project.getTables());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}

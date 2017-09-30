package com.infosky.build.service;

import java.io.IOException;
import java.util.List;

import com.infosky.build.entity.po.ProjectTable;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年3月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ProjectBuilder {

    String getWebAppPath();

    String getSrcPath();

    String getResourcePath();

    void createJavaFile(List<ProjectTable> ptList) throws IOException;

    void createXMLFile(com.infosky.build.entity.po.Project p) throws IOException;

    void createViewFile(List<ProjectTable> ptList) throws IOException;

}

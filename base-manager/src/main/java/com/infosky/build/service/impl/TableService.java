package com.infosky.build.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.infosky.build.dao.TableDAO;
import com.infosky.build.entity.dto.TableDTO;
import com.infosky.build.entity.po.Project;
import com.infosky.build.entity.po.ProjectTable;
import com.infosky.build.entity.po.Table;
import com.infosky.build.service.ProjectBuilder;
import com.infosky.common.mapper.BeanMapper;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
@Transactional
public class TableService extends JpaService<Table, TableDTO, PageResult<TableDTO>, String> {

    @Autowired
    private TableDAO tableDAO;

    /** {@inheritDoc} */

    @Override
    public TableDTO save(TableDTO t) {
        Table po = BeanMapper.map(t, Table.class);

        List<ProjectTable> projects = po.getProjects();

        for (ProjectTable projectTable : projects) {
            projectTable.setTable(po);
        }

        po.setProjects(projects);

        po = this.getDAO().save(po);

        TableDTO dto = BeanMapper.map(po, TableDTO.class);

        return dto;
    }

    public void build(List<TableDTO> tables) {

        for (TableDTO tableDTO : tables) {
            Table table = tableDAO.findOne(tableDTO.getId());
            List<ProjectTable> ptList = table.getProjects();

            for (ProjectTable projectTable : ptList) {
                Project project = projectTable.getProject();

                ProjectBuilder builder = null;
                if ("1".equals(project.getType()) && "1".equals(project.getStruct())) {
                    builder = new MavenBuilder();
                }

                try {
                    List<ProjectTable> tList = Lists.newArrayList();
                    tList.add(projectTable);

                    builder.createJavaFile(tList);
                    builder.createViewFile(tList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /** {@inheritDoc} */

    @Override
    protected DAO<Table, String> getDAO() {
        return tableDAO;
    }

}

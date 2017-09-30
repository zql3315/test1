package com.infosky.build.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.build.entity.dto.ProjectTableDTO;
import com.infosky.build.entity.dto.TableDTO;
import com.infosky.build.service.impl.ProjectService;
import com.infosky.build.service.impl.TableService;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;

/**
 * 表操作
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/table")
@Description("项目表管理")
public class TableController extends CrudController<String, PageResult<TableDTO>, TableDTO> {

    private static final Logger logger = LoggerFactory.getLogger(TableController.class);

    @Autowired
    private TableService tableService;

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/toAdd")
    public String toAdd(Model model) {
        model.addAttribute("projects", projectService.findAll(null));

        return getView("add");
    }

    /**
     * 编辑页面
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "toEdit/{id}")
    @Description(value = "打开编辑页面")
    public String toEdit(@PathVariable String id, Model model) {
        TableDTO d = getService().find(id);

        List<ProjectTableDTO> projects = d.getProjects();

        if (projects != null && projects.size() > 0) {
            ProjectTableDTO projectTableDTO = projects.get(0);
            model.addAttribute("project", projectTableDTO.getProject());
        } else {
            model.addAttribute("project", null);
        }

        model.addAttribute("model", d);

        return getView("edit");
    }

    @RequestMapping(value = "edit")
    @ResponseBody
    @Description(value = "更新数据")
    public Map<String, Object> edit(@RequestBody TableDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        TableDTO oldTableDTO = getService().find(b.getId());
        List<ProjectTableDTO> projects = oldTableDTO.getProjects();
        b.setProjects(projects);

        try {
            getService().update(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping("/build")
    @ResponseBody
    public Map<String, Object> build(@RequestBody List<TableDTO> tables) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            tableService.build(tables);
            result.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping("/deleteTable")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody List<TableDTO> tables) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            for (TableDTO tableDTO : tables) {
                super.delete(tableDTO);
            }

            result.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /** {@inheritDoc} */
    public PagingService<TableDTO, PageResult<TableDTO>, String> getService() {
        return tableService;
    }
}

package com.infosky.build.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.build.entity.dto.ProjectDTO;
import com.infosky.build.service.impl.ProjectService;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/project")
@Description("项目管理")
public class ProjectController extends CrudController<String, PageResult<ProjectDTO>, ProjectDTO> {

    @Autowired
    private ProjectService projectService;

    @RequestMapping("/build")
    @ResponseBody
    public Map<String, Object> build(@RequestBody List<ProjectDTO> projects) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            projectService.build(projects);
            result.put("result", true);
        } catch (Exception e) {
        }

        return result;
    }

    /** {@inheritDoc} */

    public PagingService<ProjectDTO, PageResult<ProjectDTO>, String> getService() {
        return projectService;
    }
}

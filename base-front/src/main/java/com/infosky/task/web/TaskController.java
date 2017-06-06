package com.infosky.task.web;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infosky.common.excel.ExportExcel;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.task.entity.dto.TaskDTO;
import com.infosky.task.service.impl.TaskService;

/**
 * 表操作
 * 
 * @author  xx
 * @version  [版本号, xx年xx月xx日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/task")
public class TaskController extends CrudController<String, PageResult<TaskDTO>, TaskDTO> {

    @Autowired
    private TaskService service;

    @RequestMapping("/exportData")
    @ResponseBody
    @Description(value = "导出数据")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        try {
            Searchable s = DynamicSearchUtils.toSearchable(request);
            s.addSearchParam("name", Operator.LIKE, request.getParameter("name"));

            /*Sort sort = new Sort(Sort.Direction.ASC, "name");
            List<TaskDTO> taskDTOLst = (List<TaskDTO>) service.findAllBySort(s, sort);*/
            List<TaskDTO> taskDTOLst = (List<TaskDTO>) service.findAll(s);

            List<Map<String, String>> dataLst = new ArrayList<Map<String, String>>();

            for (TaskDTO taskDTO : taskDTOLst) {
                Map<String, String> data = new HashMap<String, String>();

                data.put("名称", taskDTO.getName());
                data.put("类型", taskDTO.getType());
                data.put("状态", taskDTO.getStatus());
                dataLst.add(data);
            }

            //excel的第一行标题
            String titleStrs = "名称,类型,状态";
            String[] columnNames = titleStrs.split(",");

            // 底部
            String[] bottom_row_str = new String[0];

            //设置导出文件信息
            OutputStream outputStream = ExportExcel.setExportFileInfo(response);

            //创建excel
            ExportExcel.createExcel(outputStream, columnNames, dataLst, bottom_row_str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    public PagingService<TaskDTO, PageResult<TaskDTO>, String> getService() {
        return service;
    }

}

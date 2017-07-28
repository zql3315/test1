package com.infosky.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.entity.dto.DictionaryDTO;
import com.infosky.sys.service.impl.DictionaryService;

/**
 * 表操作
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/dictionary")
@Description(value = "数据字典管理")
public class DictionaryController extends CrudController<String, PageResult<DictionaryDTO>, DictionaryDTO> {

    @Autowired
    private DictionaryService service;

    @RequestMapping(value = "ztree")
    @ResponseBody
    public List<Map<String, Object>> ztree(HttpServletRequest request) {
        List<Map<String, Object>> list = null;
        try {
            list = service.buildTree();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** {@inheritDoc} */

    @RequestMapping(value = "deleteAll")
    @ResponseBody
    public Map<String, Object> deleteAll(@RequestBody List<DictionaryDTO> b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            for (DictionaryDTO dictionaryDTO : b) {
                service.delete(dictionaryDTO);
            }

            result.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /** {@inheritDoc} */
    public PagingService<DictionaryDTO, PageResult<DictionaryDTO>, String> getService() {
        return service;
    }

}

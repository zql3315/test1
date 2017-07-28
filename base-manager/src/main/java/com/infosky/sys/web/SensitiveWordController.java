package com.infosky.sys.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.common.util.PropertiesConfig;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.entity.dto.SensitiveWordDTO;
import com.infosky.sys.service.impl.SensitiveWordService;

@Controller
@RequestMapping("/sensitiveWord")
@Description(value = "敏感字管理")
public class SensitiveWordController extends CrudController<String, PageResult<SensitiveWordDTO>, SensitiveWordDTO> {

    @Autowired
    private SensitiveWordService service;

    /** {@inheritDoc} */
    public PagingService<SensitiveWordDTO, PageResult<SensitiveWordDTO>, String> getService() {
        return service;
    }

    @RequestMapping(value = "edit")
    @ResponseBody
    @Description(value = "更新数据")
    public Map<String, Object> edit(@RequestBody SensitiveWordDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            getService().update(b);
            result.put("result", true);
            updateCache();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "add")
    @ResponseBody
    @Description(value = "添加数据")
    public Map<String, Object> add(@RequestBody SensitiveWordDTO b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            getService().save(b);
            result.put("result", true);
            updateCache();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "deleteByIds")
    @ResponseBody
    @Description(value = "根据多个id批量删除数据")
    public Map<String, Object> deleteByIds(@RequestBody List<SensitiveWordDTO> b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        try {
            getService().deleteAll(b);
            result.put("result", true);
            updateCache();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private void updateCache() throws Exception {

        //获取新的accessToken
        URL postUrl = new URL(PropertiesConfig.readValue("host") + "/sensitive/resetCache");
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String line;
        StringBuffer str = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            str.append(line);
        }
        String result = JSONObject.fromObject(str.toString()).getString("code");
        if ("0000".equals(result)) {
            System.out.println("清空缓存成功");
        }
        //跟新数据库中的accessToken
        try {
            if (reader != null) reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

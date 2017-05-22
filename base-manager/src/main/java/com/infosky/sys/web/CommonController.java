package com.infosky.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infosky.framework.annotation.Description;

@Controller
@Description(value = "公共请求")
public class CommonController {

    @RequestMapping(value = "checkUserSession")
    @ResponseBody
    @Description(value = "定时校验是否超时和提出")
    public String checkUserSession() {
        return "SUCCESS";
    }

}
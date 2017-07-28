package com.infosky.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * 主界面
 * 
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    //	SimpleFilterChainDefinitionsService simpleFilterChainDefinitionsService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("resetPermission")
    @ResponseBody
    public String resetPermission(Model model) {
        //simpleFilterChainDefinitionsService.updatePermission();
        return "SUCCESS";
    }
}

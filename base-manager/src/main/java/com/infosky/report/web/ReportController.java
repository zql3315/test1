package com.infosky.report.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 报表统计图
 * 
 * @author n004881
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    /**
     * 
     * @return demo页面
     */
    @RequestMapping(value = "index")
    public String index() {
        return "report/index";
    }

}

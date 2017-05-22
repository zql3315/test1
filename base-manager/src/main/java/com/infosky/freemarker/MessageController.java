package com.infosky.freemarker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * freemarker测试控制类
 * @author n004881
 *
 */
@Controller
@RequestMapping("/freemarker")
public class MessageController {

    /**
     * 测试方法
     * @param request
     * @param response
     * @return 返回到相应的ftl模板页面
     */
    @RequestMapping(value = "msg")
    public String addMessage(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("message", "message is added");
        return "freemarker/message";

    }

}

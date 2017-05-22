package com.infosky.build.web;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * 未认证
 */
@Controller
@RequestMapping("/unauthorized")
public class UnauthorizedController {

    @RequestMapping(method = {
        RequestMethod.GET
    })
    public String unauthorized(Model model) {
        return "unauthorized";
    }

    @RequestMapping(method = {
        RequestMethod.GET
    }, headers = "x-requested-with=XMLHttpRequest")
    @ResponseBody
    public Model unauthorized(Model model, ServletRequest request) {
        model.addAttribute("code", "401");
        model.addAttribute("message", "未授权");
        return model;
    }
}

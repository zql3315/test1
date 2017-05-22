package com.infosky.demo.web;

import javax.validation.Valid;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infosky.demo.entity.validation.User;
import com.infosky.framework.annotation.Description;

/**
 * spring mvc validation 
 * 只要介绍了两张验证方式 局部验证和全局验证，两张验证方法不能共存
 * 
 * @author n004881
 * 
 */
@Controller
@RequestMapping("/demo")
@Description("验证测试")
public class ValidationController {

    private static final Logger logger = LoggerFactory.getLogger(ValidationController.class);

    /**
     * 第二种验证方法，全局通过配置spring配置文件来实现
     * 
     * @param user
     * @param errors
     * @return
     */
    @RequestMapping(value = "/validate/hello", method = RequestMethod.POST)
    @ResponseBody
    public String test(@Valid @RequestBody User user, Errors errors) {
        logger.info(JSONObject.fromObject(user).toString());
        if (errors.hasErrors()) {
            return errors.getFieldErrors().get(0).getDefaultMessage();
        }
        return "success";
    }

    /**
     * 地图控件
     * 
     * @return
     */
    @RequestMapping(value = "/map")
    public String map() {
        return "demo/map";
    }

}

package com.infosky.demo.web.socket;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.google.gson.GsonBuilder;

/** 
 * 
 * http://blog.csdn.net/liuyunshengsir/article/details/52495919#
 * 
 * 通过Controller调用进行websocket的后台推送
 * 
 */
@Controller
public class GarlicPriceController {

    @Resource
    MyWebSocketHandler myWebSocketHandler;

    @RequestMapping(value = "demo/testWebSocket", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String testWebSocket(HttpServletRequest request) throws IOException {
        Object sessionUser = (String) request.getSession().getAttribute("sessionUser");
        if(sessionUser!=null){
            myWebSocketHandler.sendMessageToJsp(new TextMessage(new GsonBuilder().create().toJson("\"number\":\"" + "GarlicPriceController/testWebSocket" + "\"")));
        }
        return "1";
    }

}

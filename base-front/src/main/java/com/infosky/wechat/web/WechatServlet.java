package com.infosky.wechat.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.infosky.wechat.common.SignUtil;
import com.infosky.wechat.service.WechatService;

@Controller
public class WechatServlet {

    protected final static Logger logger = LoggerFactory.getLogger(WechatServlet.class);

    @Autowired
    protected WechatService wechatService;

    /**
     * @param request
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/wechatServlet", method = RequestMethod.GET)
    public void wechatServletGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        logger.info("微信加密签名:" + signature + " ; 时间戳:" + timestamp + ",随机数: " + nonce + "; 随机字符串: " + echostr);
        PrintWriter out = response.getWriter();
        // 校验签名
        if (signature != null && timestamp != null && nonce != null && SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        } else {
            out.print("ERROR");
        }
        out.close();
    }

    /**
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/wechatServlet", method = RequestMethod.POST)
    public void wechatServletPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String respMessage = wechatService.processRequest(request);//
        logger.info("==响应消息：");
        logger.info(respMessage);
        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(respMessage);
        out.close();
    }

}

package com.infosky.demo.web.socket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/** 
 * 
 * 创建websocket握手协议的后台
 * HandShake的实现类
 * 
*/
public class MyHandShake extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 解决苹果手机sef浏览器The extension [x-webkit-deflate-frame] is not supported问题
        if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }

        String jspCode = ((ServletServerHttpRequest) request).getServletRequest().getParameter("jspCode");
        // 标记用户  
        //String userId = (String) session.getAttribute("userId");  
        if (jspCode != null) {
            attributes.put("jspCode", jspCode);
        } else {
            attributes.put("jspCode", "default");
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        super.afterHandshake(request, response, wsHandler, exception);
    }

}

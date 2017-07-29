package com.infosky.demo.web.socket;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/** 
 * WebSocketConfig的实现类
 * @author n004881
 *
 */
@Component
@EnableWebMvc
@EnableWebSocket
public class MyWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Resource
    MyWebSocketHandler handler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //允许连接的域,只能以http或https开头
        String[] allowsOrigins = {
        //            "http://www.xxx.com"
        };
        registry.addHandler(handler, "/demo/wsMy").addInterceptors(new MyHandShake());
        registry.addHandler(handler, "/demo/wsMy/sockjs").setAllowedOrigins(allowsOrigins).addInterceptors(new MyHandShake()).withSockJS();
    }

}
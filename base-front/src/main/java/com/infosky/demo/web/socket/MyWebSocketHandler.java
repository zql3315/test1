package com.infosky.demo.web.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.GsonBuilder;

/** 
 * WebSocketHandler的实现类
*/
@Component
public class MyWebSocketHandler implements WebSocketHandler {

    
    private static final Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);
    
    
    public static final Map<String, WebSocketSession> userSocketSessionMap;

    static {
        userSocketSessionMap = new HashMap<String, WebSocketSession>();
    }

    //连接建立后处理
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Socket会话连接:用户ID:" + session.getId());
        String jspCode = (String) session.getAttributes().get("sessionUser");
        if (jspCode!=null && userSocketSessionMap.get(jspCode) == null) {
            userSocketSessionMap.put(jspCode, session);
        }
        for (int i = 0; i < 10; i++) {
            //broadcast(new TextMessage(new GsonBuilder().create().toJson("\"number\":\""+i+"\"")));  
            session.sendMessage(new TextMessage(new GsonBuilder().create().toJson("\"number\":\"" + i + "\"")));
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        session.sendMessage(message);
    }

    //连接异常处理
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
        // 移除Socket会话  
        while (it.hasNext()) {
            Entry<String, WebSocketSession> entry = it.next();
            if (entry.getValue().getId().equals(session.getId())) {
                userSocketSessionMap.remove(entry.getKey());
                logger.info("Socket会话已经移除:sessionId:" + session.getId() + "==handleTransportError==用户ID：" + entry.getKey());
                break;
            }
        }
    }

    //连接关闭后处理
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.info("Websocket:" + session.getId() + "已经关闭");
        Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
        // 移除Socket会话  
        while (it.hasNext()) {
            Entry<String, WebSocketSession> entry = it.next();
            if (entry.getValue().getId().equals(session.getId())) {
                userSocketSessionMap.remove(entry.getKey());
                logger.info("Socket会话已经移除:sessionId:" + session.getId() + "=afterConnectionClosed===用户ID" + entry.getKey());
                break;
            }
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /** 
     * 群发 
     */
    public void broadcast(final TextMessage message) throws IOException {
        logger.info("服务端主动推送消息到客户端");
        Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
        // 多线程群发  
        while (it.hasNext()) {
            final Entry<String, WebSocketSession> entry = it.next();
            if (entry.getValue().isOpen()) {
                new Thread(new Runnable() {

                    public void run() {
                        try {
                            if (entry.getValue().isOpen()) {
                                entry.getValue().sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }).start();
            }

        }
    }

    /** 
     * 给所有在线用户的实时工程检测页面发送消息 
     *  
     * @param message 
     * @throws IOException 
     */
    public void sendMessageToJsp(final TextMessage message) throws IOException {
        Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
        // 多线程群发  
        while (it.hasNext()) {

            final Entry<String, WebSocketSession> entry = it.next();
            if (entry.getValue().isOpen()) {
                new Thread(new Runnable() {

                    public void run() {
                        try {
                            if (entry.getValue().isOpen()) {
                                logger.info("服务端主动推送消息到客户端,用id:{},用户name:{}",entry.getValue().getId(),entry.getKey());
                                entry.getValue().sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }).start();
            }

        }
    }
}

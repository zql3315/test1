package com.infosky.demo.web.mywebsocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 使用原生的javaee自带的websocket 类似于springmvc方式
 * 需要导入javax.websocket-api.jar
 * 
 * @author n004881
 *
 */
@ServerEndpoint(value = "/demo/mywebsocket")
public class MyWebSocket {
    
    private static final Logger logger = LoggerFactory.getLogger(MyWebSocket.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。  
    private static AtomicInteger onlineCount =  new AtomicInteger(0);

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识  
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据  
    private Session session;

    /** 
     * 连接建立成功调用的方法 
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据 
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中  
        addOnlineCount();           //在线数加1  
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /** 
     * 连接关闭调用的方法 
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除  
        subOnlineCount();           //在线数减1      
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /** 
     * 收到客户端消息后调用的方法 
     * @param message 客户端发送过来的消息 
     * @param session 可选的参数 
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("来自客户端的消息:" + message);

        //群发消息  
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage("来自服务端的消息：" + message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /** 
     * 发生错误时调用 
     * @param session 
     * @param error 
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }

    /** 
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。 
     * @param message 
     * @throws IOException 
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);  
    }

    public static int getOnlineCount() {
        return onlineCount.get();
    }

    public static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    public static void subOnlineCount() {
        onlineCount.getAndDecrement();
    }

}
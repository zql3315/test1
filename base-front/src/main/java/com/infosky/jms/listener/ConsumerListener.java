package com.infosky.jms.listener;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.infosky.jms.entity.Email;

public class ConsumerListener {

    public void handleMessage(TextMessage message) {
        System.out.println("ConsumerListener通过handleMessage接收到一个纯文本消息，消息内容是：" + message);
    }

    public void handleMessage(String message) {
        System.out.println("ConsumerListener通过handleMessage接收到一个纯文本消息，消息内容是：" + message);
    }

    // public void receiveMessage(String message) {
    // System.out.println("0ConsumerListener通过receiveMessage接收到一个纯文本消息，消息内容是：" + message);
    // }
    //
    // public void receiveMessage(TextMessage message) throws JMSException {
    // System.out.println("1ConsumerListener通过receiveMessage接收到一个纯文本消息，消息内容是：" + message.getText());
    // }

    /**
     * 当返回类型是非null时MessageListenerAdapter会自动把返回值封装成一个Message，然后进行回复
     * 
     * @param message
     * @return
     */
    public String receiveMessage(String message) {
        System.out.println("2ConsumerListener通过receiveMessage接收到一个纯文本消息，消息内容是：" + message);
        return "这是ConsumerListener对象的receiveMessage方法的返回值。";
    }

    // public String receiveMessage(TextMessage message) {
    // System.out.println("ConsumerListener通过receiveMessage接收到一个纯文本消息，消息内容是：" + message);
    // return "这是ConsumerListener对象的receiveMessage方法的返回值。";
    // }

    public void receiveMessage(Email email) {
        System.out.println("接收到一个包含Email的ObjectMessage。");
        System.out.println(email);
    }

    public void receiveMessage(ObjectMessage message) throws JMSException {
        System.out.println(message.getObject());
    }

}

package com.infosky.jms.listener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.springframework.jms.support.converter.MessageConverter;

import com.infosky.jms.entity.Email;

/**
 * MessageListener是最原始的消息监听器，它是JMS规范中定义的一个接口。其中定义了一个用于处理接收到的消息的onMessage方法，该方法只接收一个Message参数。
 * 
 * @author n004881
 */
public class ConsumerMessageListener implements MessageListener {

    private MessageConverter messageConverter;

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            // 这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
            TextMessage textMsg = (TextMessage) message;
            System.out.println("接收到一个纯文本消息。");
            try {
                System.out.println("消息内容是：" + textMsg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else if (message instanceof MapMessage) {
            MapMessage mapMessage = (MapMessage) message;
            System.out.println("消息内容是：" + mapMessage);
        } else if (message instanceof ObjectMessage) {
            ObjectMessage objMessage = (ObjectMessage) message;
            try {
                // Object obj = objMessage.getObject();
                // Email email = (Email) obj;
                Email email = (Email) messageConverter.fromMessage(objMessage);
                System.out.println("接收到一个ObjectMessage，包含Email对象。");
                System.out.println(email);
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }

    public MessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

}

package com.infosky.jms.service;

import javax.jms.JMSException;

/**
 * 消费者实现类
 * 
 * @author n004881
 */
public interface ConsumerService {

    /**
     * 接收文本消息
     * 
     * @param message
     * @throws JMSException
     */
    public void receiveMessage(String message) throws JMSException;

}

package com.infosky.sys.service.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.collect.Maps;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  n004881
 * @version  [版本号, 2015年3月26日]
 */
@Service
public class PushService<K extends Serializable> {

    private volatile Map<K, Queue<DeferredResult<Object>>> userIdToDeferredResultMap = Maps.newConcurrentMap();

    private static final Logger logger = LoggerFactory.getLogger(PushService.class);

    public boolean isOnline(final K userId) {
        return userIdToDeferredResultMap.containsKey(userId);
    }

    /**
     * 上线后 创建一个空队列，防止多次判断
     * @param userId
     */
    public void online(final K userId) {
        Queue<DeferredResult<Object>> queue = userIdToDeferredResultMap.get(userId);
        if (queue == null) {

            //            queue = new LinkedBlockingDeque<DeferredResult<Object>>(); //jdk 1.6
            queue = new ConcurrentLinkedQueue<DeferredResult<Object>>(); //jdk 1.7
            userIdToDeferredResultMap.put(userId, queue);
        }
    }

    public void offline(final K userId) {

        Queue<DeferredResult<Object>> queue = userIdToDeferredResultMap.remove(userId);
        if (queue != null) {
            for (DeferredResult<Object> result : queue) {
                try {
                    result.setResult("");
                } catch (Exception e) {
                    //ignore
                }
            }
        }
    }

    public DeferredResult<Object> newDeferredResult(final K userId) {
        final DeferredResult<Object> deferredResult = new DeferredResult<Object>();
        deferredResult.onCompletion(new Runnable() {

            @Override
            public void run() {
                Queue<DeferredResult<Object>> queue = userIdToDeferredResultMap.get(userId);
                if (queue != null) {
                    queue.remove(deferredResult);
                    deferredResult.setResult("");
                }
            }
        });
        deferredResult.onTimeout(new Runnable() {

            @Override
            public void run() {
                deferredResult.setErrorResult("");
            }
        });
        Queue<DeferredResult<Object>> queue = userIdToDeferredResultMap.get(userId);
        if (queue == null) {
            //          queue = new LinkedBlockingDeque<DeferredResult<Object>>(); //jdk 1.6
            queue = new ConcurrentLinkedQueue<DeferredResult<Object>>(); //jdk 1.7
            userIdToDeferredResultMap.put(userId, queue);
        }
        queue.add(deferredResult);

        return deferredResult;
    }

    public void push(final K userId, final Object data) {
        Queue<DeferredResult<Object>> queue = userIdToDeferredResultMap.get(userId);
        if (queue == null) {
            return;
        }
        for (DeferredResult<Object> deferredResult : queue) {
            if (!deferredResult.isSetOrExpired()) {
                try {
                    deferredResult.setResult(data);
                } catch (Exception e) {
                    queue.remove(deferredResult);
                }
            }
        }
    }

    /**
     * 定期清空队列 防止中间推送消息时中断造成消息丢失
     */
    //    @Scheduled(fixedRate = 5L * 60 * 1000)
    public void sync() {
        logger.debug("PushService---------------sync");
        Map<K, Queue<DeferredResult<Object>>> oldMap = userIdToDeferredResultMap;
        userIdToDeferredResultMap = new ConcurrentHashMap<K, Queue<DeferredResult<Object>>>();
        for (Queue<DeferredResult<Object>> queue : oldMap.values()) {
            if (queue == null) {
                continue;
            }

            for (DeferredResult<Object> deferredResult : queue) {
                try {
                    deferredResult.setResult("");
                } catch (Exception e) {
                    queue.remove(deferredResult);
                }
            }

        }
    }
}

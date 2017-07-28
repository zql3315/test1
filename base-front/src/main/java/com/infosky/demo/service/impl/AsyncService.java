package com.infosky.demo.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用方法 不能有事物管理，否则无效
 * 
 * @author n004881
 */
@Service
public class AsyncService {

    @Async
    public void asyncMethod() {
        try {
            //让程序暂停100秒，相当于执行一个很耗时的任务
            Thread.sleep(5000);
            System.out.println("=================");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

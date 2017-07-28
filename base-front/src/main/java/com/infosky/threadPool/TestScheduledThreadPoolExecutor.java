package com.infosky.threadPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 创建一个大小无限的线程池。
 * 
 * 此线程池支持定时以及周期性执行任务的需求。
 * 
 * @author n004881
 * 
 */
public class TestScheduledThreadPoolExecutor {

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        // 每隔一段时间就触发异常
        exec.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                // throw new RuntimeException();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                System.out.println(df.format(new Date()) + "================");
            }
        }, 1000, 5000, TimeUnit.MILLISECONDS);

        // 每隔一段时间打印系统时间，证明两者是互不影响的
        exec.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                System.out.println(df.format(new Date()) + "==" + System.nanoTime());
            }
        }, 1000, 2000, TimeUnit.MILLISECONDS);
    }
}
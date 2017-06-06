package com.infosky.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建一个单线程的线程池。
 * 
 * 这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。
 * 
 * 如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。 此线程池保证所有任务的执行顺序按照任务的提交顺序执行。
 * 
 * @author n004881
 * 
 */
public class TestSingleThreadExecutor {

    public static void main(String[] args) {
        // 创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();
        // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        Thread t3 = new MyThread();
        Thread t4 = new MyThread();
        Thread t5 = new MyThread();
        // 将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);
        // 关闭线程池
        pool.shutdown();
    }
}

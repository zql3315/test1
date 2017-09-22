package com.infosky.redis.cluster;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * 分布式锁管理器，支持对单个资源加锁解锁，或给一批资源的批量加锁及解锁
 * 
 * @ClassName: DistributedLockManger
 * @Description:
 * @author 何志雄 001
 * @company 南京高嘉软件技术有限公司
 * @date 2015年6月3日 下午5:44:06
 */
public class DistributedLockManger {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLockManger.class);

    private static final int DEFAULT_SINGLE_EXPIRE_TIME = 3;

    // private static final int DEFAULT_BATCH_EXPIRE_TIME = 6;

    // static的变量无法注解
    @Autowired
    JedisCluster jc;

    private static DistributedLockManger lockManger;

    public DistributedLockManger() {
    }

    @PostConstruct
    public void init() {
        lockManger = this;
        lockManger.jc = this.jc;
    }

    /**
     * 获取锁 如果锁可用 立即返回true， 否则立即返回false，作为非阻塞式锁使用
     * 
     * @param key
     * @param value
     * @return
     */
    public static boolean tryLock(String key, String value) {
        try {
            return tryLock(key, value, 0L, null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false，作为阻塞式锁使用
     * 
     * @param key
     *            锁键
     * @param value
     *            被谁锁定
     * @param timeout
     *            尝试获取锁时长，建议传递500,结合实践单位，则可表示500毫秒
     * @param unit
     *            ，建议传递TimeUnit.MILLISECONDS
     * @return
     * @throws InterruptedException
     */
    public static boolean tryLock(String key, String value, long timeout, TimeUnit unit) throws InterruptedException {
        // 纳秒
        long begin = System.nanoTime();
        do {
            // LOGGER.debug("{}尝试获得{}的锁.", value, key);
            Long i = lockManger.jc.setnx(key, value);
            if (i == 1) {
                lockManger.jc.expire(key, DEFAULT_SINGLE_EXPIRE_TIME);
                LOGGER.debug("{}成功获取{}的锁,设置锁过期时间为{}秒 ", value, key, DEFAULT_SINGLE_EXPIRE_TIME);
                return true;
            } else {
                // 存在锁 ，但可能获取不到，原因是获取的一刹那间
                // String desc = lockManger.jc.get(key);
                // LOGGER.error("{}正被{}锁定.", key, desc);
            }
            if (timeout == 0) {
                break;
            }
            // 在其睡眠的期间，锁可能被解，也可能又被他人占用，但会尝试继续获取锁直到指定的时间
            Thread.sleep(100);
        } while ((System.nanoTime() - begin) < unit.toNanos(timeout));
        // 因超时没有获得锁
        return false;
    }

    /**
     * 释放单个锁
     * 
     * @param key
     *            锁键
     * @param value
     *            被谁释放
     */
    public static void unLock(String key, String value) {
        try {
            lockManger.jc.del(key);
            LOGGER.debug("{}锁被{}释放 .", key, value);
        } catch (JedisConnectionException je) {

        } catch (Exception e) {

        }
    }

    public void test() throws InterruptedException {
        String productId = "18061249844";
        String userId;
        for (int i = 1; i <= 50; i++) {
            // 随机产生一个用户
            userId = UUID.randomUUID().toString();
            // 该用户试图锁定(如果被锁，尝试等待300毫秒)，在处理一些事情后，再释放锁
            testLock(productId, userId);
            Thread.sleep(20);
        }
    }

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(150, 150, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new BasicThreadFactory.Builder().daemon(true)
            .namingPattern("mongo-oper-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy());

    private static void testLock(final String key, final String value) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    // 获取锁，如果不能立即获取，尝试等待1000毫秒
                    boolean isLock = DistributedLockManger.tryLock(key, value, 500, TimeUnit.MILLISECONDS);
                    if (isLock) {
                        // doSomething(占用锁20毫秒到4秒，模拟处理事务)
                        long doSomeThingTime = RandomUtils.nextLong(new Random());
                        LOGGER.debug("{}将持有锁{}时长{}毫秒.", value, key, doSomeThingTime);
                        Thread.sleep(doSomeThingTime);
                        // 然后释放锁
                        DistributedLockManger.unLock(key, value);
                    }
                } catch (Throwable th) {
                }
            }
        });
    }

    public JedisCluster getJc() {
        return jc;
    }

    public void setJc(JedisCluster jc) {
        this.jc = jc;
    }
}
